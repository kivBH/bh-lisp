package cz.bh.lisp.parser.lexer

import cz.bh.lisp.parser.exceptions.EscapeSequenceLexerException
import cz.bh.lisp.parser.exceptions.InputEndsWithinStringLexerException

class Lexer {
    Reader reader
    int line
    StringBuilder stringBuilder
    Queue<Token> tokenBuff

    Lexer(Reader reader) {
        this.reader = reader
        this.line = 1
        this.stringBuilder = new StringBuilder()
        this.tokenBuff = new LinkedList<>()
        this.tokenBuff.clear()
    }

    Token nextToken() {
        Token t = createNextToken()
        while (t != null && t.type == TokenType.EMPTY) {
            t = createNextToken()
        }
        return t
    }

    private Token createToken(String val) {
        if (val.length() <= 0) {
            return new Token(TokenType.EMPTY, val, line)
        }

        return new Token(TokenType.UNKNOWN, val, line)
    }

    private Token createEndOfStreamToken(String val) {
        if (val.length() > 0) {
            return createToken(val)
        }

        return null
    }

    private Token createNextToken() {
        if (!tokenBuff.isEmpty()) {
            Token t = tokenBuff.poll()
            return t
        }

        stringBuilder.setLength(0)  // reset
        char c
        while ((c = reader.read()) >= 0) {    // -1 end of the stream

            switch (c) {
            // samostatny token
                case '(':
                    tokenBuff.add(new Token(TokenType.START_LIST, ""+c, line)) // samostatny token schovam na pozdeji
                    return createToken(stringBuilder.toString())    // vratim token predchazejici
                case ')':
                    tokenBuff.add(new Token(TokenType.END_LIST, ""+c, line))   // samostatny token schovam na pozdeji
                    return createToken(stringBuilder.toString())    // vratim token predchazejici

            // retezec
                case '"':
                    tokenBuff.add(createStringToken()) // do bufferu dalsi string token, pokud dojde k vyjimce nevrati soucasny token, ale to odpovida chovani napr. pro (+ (+ 1 2 "v") "jh)
                    return createToken(stringBuilder.toString())    // vratim token predchazejici

            // ukonceni tokenu
                case ' ':
                case '\t':
                    return createToken(stringBuilder.toString())

            // ignore
                case '\r':
                    break

            // comment
                case ';':
                    eatComment() // skip commentu
                    return createToken(stringBuilder.toString())    // vratim token predchazejici

            // new line
                case '\n':
                    Token t = createToken(stringBuilder.toString())
                    line++
                    return t

            // escape sekvence
                case '\\':
                    stringBuilder.append(getNextForEscapeSequence())
                    break

            // znaky tokenu
                default:
                    stringBuilder.append(c)
            }
        }
        return createEndOfStreamToken(stringBuilder.toString())
    }

    def eatComment() {
        char c
        while ((c = reader.read()) >= 0) {
            switch (c) {
            // new line
                case '\n':
                    line++
                    return  // po konci radky se vratim
            }
        }
    }

    private Token createStringToken() {
        StringBuilder sb = new StringBuilder()
        char c
        while ((c = reader.read()) >= 0) {
            switch (c) {
            // konec retezce
                case '"':
                    return new Token(TokenType.STRING, sb.toString(), line)

            // escape sekvence
                case '\\':
                    sb.append(getNextForEscapeSequence())
                    break

            // new line
                case '\n':
                    line++
                    sb.append(c)
                    break

            // znaky
                default:
                    sb.append(c)
            }
        }
        throw new InputEndsWithinStringLexerException(line)
    }

    private char getNextForEscapeSequence() {
        char c
        if ((c = reader.read()) >= 0) {
            return c
        } else {
            throw new EscapeSequenceLexerException(line)
        }
    }
}
