package parsers

import com.github.h0tk3y.betterParse.combinators.and
import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.oneOrMore
import com.github.h0tk3y.betterParse.combinators.optional
import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.combinators.separatedTerms
import com.github.h0tk3y.betterParse.combinators.times
import com.github.h0tk3y.betterParse.combinators.unaryMinus
import com.github.h0tk3y.betterParse.combinators.use
import com.github.h0tk3y.betterParse.combinators.zeroOrMore
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.grammar.tryParseToEnd
import com.github.h0tk3y.betterParse.lexer.Token
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.ParseResult
import com.github.h0tk3y.betterParse.parser.Parser
import commands.parsed.ParsedCallCommand
import commands.parsed.ParsedCommand
import commands.parsed.ParsedCommandPipe
import commands.parsed.ParsedCommandSequence
import commands.parsed.ParsedSetVariableCommand

/**
 * Class provides parsing [ParsedCommand] from string.
 */
object CommandParser {

    /**
     * Parse [ParsedCommand] from [inputString].
     *
     * Brief rules:
     * - \? - explicitly puts specified char
     * - "*" - weak quoting
     * - '*' - strong quoting
     * - { q; } - command sequence
     * - a | b - command pipe
     * - cmd [[args*]] - call command cmd with args
     * - w=n, w="s", w='s' - equation
     */
    fun parse(inputString: String): ParseResult<ParsedCommand> {
        val grammar = CommandGrammar()
        return grammar.tryParseToEnd(inputString)
    }

    private class CommandGrammar : Grammar<ParsedCommand>() {

        // Special characters
        private val specialChars = """
            "'\=|;{}
        """.trimIndent()
        private val spRegex = specialChars.replace("\\", "\\\\")

        // Tokens
        private val inplaceStrongQuote by regexToken("\\\\'") // \'
        private val inplaceWeakQuote by regexToken("\\\\\"") // \"
        private val inplaceSlash by regexToken("\\\\\\\\") // \\
        private val inplaceChar by regexToken("\\\\.")
        private val weakQuote by literalToken("\"")
        private val strongQuote by literalToken("'")
        private val eq by literalToken("=")
        private val pipe by literalToken("|")
        private val seq by literalToken(";")
        private val beginSeq by literalToken("{")
        private val endSeq by literalToken("}")
        private val separator by regexToken("\\s+")
        private val nonSpec by regexToken("[^\\s$spRegex]+")
        @Suppress("UNUSED") private val any by regexToken(".*")

        // Terms
        private val weakQuotingTerm by
            (-weakQuote * parser(::weakQuotingParser) * -weakQuote)

        private val strongQuotingTerm by
            (-strongQuote * parser(::strongQuotingParser)) and
            (
                (strongQuote use { "" }) or
                (inplaceStrongQuote use { text.dropLast(1) })
            ) map { (body, end) -> body + end }

        private val inplaceTerm by
            (inplaceStrongQuote or inplaceWeakQuote or inplaceSlash or inplaceChar) use {
                text.drop(1)
            }

        private val wordTerm by
            oneOrMore(
                weakQuotingTerm or strongQuotingTerm or inplaceTerm or (nonSpec use { text })
            ) use { joinToString("") }

        // Parsing
        private val callCommandParser by
            wordTerm and optional(
                -separator * separatedTerms(wordTerm, separator)
            ) map { (cmd, args) ->
                ParsedCallCommand(cmd, args ?: emptyList())
            }

        private val equalityParser by
            nonSpec * -eq * wordTerm map { (name, value) ->
                ParsedSetVariableCommand(name.text, value)
            }

        private val nodeCommandParser by
            equalityParser or callCommandParser

        private val sequenceCommandParser by
            parser(::sequenceParser) map { commands ->
                ParsedCommandSequence(commands)
            }

        private val surroundedSequenceParser by
            (-beginSeq * -separator * parser {
                sequenceParser(checkLastSeq = false)
            } * -seq * -separator * -endSeq) map { commands ->
                ParsedCommandSequence(commands)
            }

        private val pipeCommandParser by
            parser(::pipeParser) map { commands ->
                ParsedCommandPipe(commands)
            }

        override val rootParser by
            -optional(separator) * (
                sequenceCommandParser or pipeCommandParser or surroundedSequenceParser or nodeCommandParser
            ) * -optional(separator)

        private fun allTokensExcept(vararg exceptTokens: Token): Parser<String> =
            tokens
                .filterNot { it in exceptTokens }
                .map { token -> token use { text } }
                .reduce { acc, parser -> acc or parser }

        private fun weakQuotingParser(): Parser<String> {
            val defaultTokens = allTokensExcept(weakQuote, inplaceWeakQuote, inplaceSlash)
            return zeroOrMore(
                defaultTokens or
                        ((inplaceWeakQuote or inplaceSlash) use { text.drop(1) })
            ) use { joinToString("") }
        }

        private fun strongQuotingParser(): Parser<String> {
            val defaultTokens = allTokensExcept(strongQuote, inplaceStrongQuote)
            return zeroOrMore(defaultTokens) use { joinToString("") }
        }

        private fun sequenceParser(checkLastSeq: Boolean = true): Parser<List<ParsedCommand>> {
            val cmd = pipeCommandParser or nodeCommandParser
            val sep = seq * optional(separator)
            val main = cmd * -sep * separatedTerms(cmd, sep, acceptZero = true) map { (first, other) ->
                listOf(first) + other
            }
            return if (checkLastSeq) {
                main * -optional(seq)
            } else {
                main
            }
        }

        private fun pipeParser(): Parser<List<ParsedCommand>> {
            val cmd = surroundedSequenceParser or nodeCommandParser
            val sep = optional(separator) * pipe * optional(separator)
            return cmd * -sep * separatedTerms(cmd, sep) map { (first, other) ->
                listOf(first) + other
            }
        }

    }

}