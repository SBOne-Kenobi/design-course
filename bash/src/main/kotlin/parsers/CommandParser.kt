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

/**
 * Class provides parsing [ParsedCommand] from string.
 */
object CommandParser {

    /**
     * Parse [ParsedCommand] from [inputString].
     *
     * Brief rules:
     * - \? - explicitly puts specified char.
     * - "*" - weak quoting.
     * - '*' - strong quoting.
     * - cmd [[args*]] - call command cmd with args
     */
    fun parse(inputString: String): ParseResult<ParsedCommand> {
        val grammar = CommandGrammar()
        return grammar.tryParseToEnd(inputString)
    }

    private class CommandGrammar : Grammar<ParsedCommand>() {

        // Special characters
        private val specialChars = """
            "'\
        """.trimIndent()
        private val spRegex = specialChars.replace("\\", "\\\\")

        // Tokens
        private val inplaceStrongQuote by regexToken("\\\\'") // \'
        private val inplaceWeakQuote by regexToken("\\\\\"") // \"
        private val inplaceSlash by regexToken("\\\\\\\\") // \\
        private val inplaceChar by regexToken("\\\\.")
        private val weakQuote by literalToken("\"")
        private val strongQuote by literalToken("'")
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

        override val rootParser by
            -optional(separator) * callCommandParser * -optional(separator)

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

    }

}