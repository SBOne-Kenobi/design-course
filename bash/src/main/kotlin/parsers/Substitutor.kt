package parsers

import SessionContext
import com.github.h0tk3y.betterParse.combinators.and
import com.github.h0tk3y.betterParse.combinators.map
import com.github.h0tk3y.betterParse.combinators.or
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

object Substitutor {

    fun parse(context: SessionContext, inputString: String): ParseResult<String> {
        val grammar = SubstitutionGrammar(context)
        return grammar.tryParseToEnd(inputString)
    }

    private class SubstitutionGrammar(private val context: SessionContext) : Grammar<String>() {

        // Special characters
        private val specialChars = """
            $"'\{}
        """.trimIndent()
        private val spRegex = specialChars
            .replace("\\", "\\\\")
            .replace("$", "\\$")

        // Tokens
        private val inplaceStrongQuote by regexToken("\\\\'") // \'
        private val inplaceWeakQuote by regexToken("\\\\\"") // \"
        private val inplaceSlash by regexToken("\\\\\\\\") // \\
        private val inplaceSubstitutor by regexToken("\\\\\\$") // \$
        private val inplaceChar by regexToken("\\\\.")
        private val weakQuote by literalToken("\"")
        private val strongQuote by literalToken("'")
        private val substitutor by literalToken("$")
        private val beginFig by literalToken("{")
        private val endFig by literalToken("}")
        @Suppress("UNUSED") private val separator by regexToken("\\s+")
        private val nonSpec by regexToken("[^\\s$spRegex]+")
        @Suppress("UNUSED") private val any by regexToken(".*")

        // Terms
        private val strongQuotingTerm by
        (strongQuote * parser(::strongQuotingParser)) and
        (
            strongQuote or inplaceStrongQuote
        ) map { (a, b, c) -> a.text + b + c.text }

        private val varTerm by nonSpec use { context.variables[text] }

        private val substitutorTerm by
        -substitutor * (
            varTerm or
            (-beginFig * varTerm * -endFig)
        ) map { it ?: "" }

        private val weakQuotingTerm by
        (weakQuote * parser(::weakQuotingParser) * weakQuote) map { (a, b, c) -> a.text + b + c.text }

        private val inplaceTerm by
        (inplaceStrongQuote or inplaceWeakQuote or inplaceSlash or inplaceSubstitutor or inplaceChar) use { text }

        override val rootParser: Parser<String> by zeroOrMore(
            strongQuotingTerm or weakQuotingTerm or inplaceTerm or substitutorTerm or allTokensExcept()
        ) use { joinToString("") }

        private fun allTokensExcept(vararg exceptTokens: Token): Parser<String> =
            tokens
                .filterNot { it in exceptTokens }
                .map { token -> token use { text } }
                .reduce { acc, parser -> acc or parser }

        private fun weakQuotingParser(): Parser<String> {
            val defaultTokens = allTokensExcept(weakQuote)
            return zeroOrMore(
                substitutorTerm or defaultTokens
            ) use { joinToString("") }
        }

        private fun strongQuotingParser(): Parser<String> {
            val defaultTokens = allTokensExcept(strongQuote, inplaceStrongQuote)
            return zeroOrMore(defaultTokens) use { joinToString("") }
        }

    }
}