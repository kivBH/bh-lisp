package cz.bh.lisp.parser.lexer


import cz.bh.lisp.parser.exceptions.LexerException
import cz.bh.lisp.parser.sexp.ClassNode
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.StringNode
import cz.bh.lisp.parser.sexp.SymbolNode

/**
 * Class for reading input and parsing into tokens
 *
 * @author Josef Baloun
 */
class Lexer {
    Reader reader
    int line
    StringBuilder stringBuilder
    Queue<Token> tokenBuff
    NodeHandler nodeHandler
    int bracketCounter

    Lexer(Reader reader) {
        this.reader = reader
        this.line = 1
        this.stringBuilder = new StringBuilder()
        this.tokenBuff = new LinkedList<>()
        this.tokenBuff.clear()
        this.nodeHandler = new NodeHandler()
        this.bracketCounter = 0
    }

    /**
     * Returns next not empty token or null if the stream ends.
     * @return next not empty token or null if the stream ends
     * */
    Token nextToken() {
        return nextNotEmptyToken(tokenBuff, stringBuilder)
    }

    /**
     * Returns next not empty token or null if the stream ends.
     *
     * @param tokenBuff buff to use
     * @param stringBuilder string builder to use
     * @return next not empty token or null if the stream ends
     * */
    private Token nextNotEmptyToken(Queue<Token> tokenBuff, StringBuilder stringBuilder) {
        if (tokenBuff.isEmpty()) {
            buffNextToken(tokenBuff, stringBuilder)
        }

        while (!tokenBuff.isEmpty()) {
            while (!tokenBuff.isEmpty()) {
                if (tokenBuff.peek().type == TokenType.EMPTY) {
                    tokenBuff.poll()
                }
                else {
                    return tokenBuff.poll()
                }
            }
            buffNextToken(tokenBuff, stringBuilder)
        }

        return null
    }

    private Token createToken(String val) {
        if (val.length() <= 0) {
            return new Token(TokenType.EMPTY, null, line)
        }
        return new Token(TokenType.NODE, nodeHandler.handle(val, line), line)
    }

    private Token createStringToken() {
        StringBuilder sb = new StringBuilder()
        char c
        while ((c = reader.read()) >= 0) {
            switch (c) {
            // konec retezce
                case '"':
                    return new Token(TokenType.NODE, new StringNode(sb.toString(), line), line)

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
        throw new LexerException("Input ends within a string", line)
    }

    private Token createStartListToken() {
        bracketCounter++
        return new Token(TokenType.START_LIST, null, line)
    }

    private Token createEndListToken() {
        bracketCounter--
        return new Token(TokenType.END_LIST, null, line)
    }

    private void buffNextToken(Queue<Token> tokenBuff, StringBuilder stringBuilder) {
        stringBuilder.setLength(0)  // reset
        char c
        while ((c = reader.read()) >= 0) {    // -1 end of the stream

            switch (c) {
            // samostatny token
                case '(':
                    tokenBuff.add(createToken(stringBuilder.toString())) // token predchazejici
                    tokenBuff.add(createStartListToken())                // samostatny token pozdeji
                    return
                case ')':
                case ']':   // konec listu
                    tokenBuff.add(createToken(stringBuilder.toString()))    // token predchazejici
                    tokenBuff.add(createEndListToken())                     // samostatny token na pozdeji
                    return

            // list
                case '[':
                    tokenBuff.add(createToken(stringBuilder.toString()))    // token predchazejici
                    tokenBuff.add(createStartListToken())                   // [ na (list
                    tokenBuff.add(new Token(TokenType.NODE, new SymbolNode("list", line), line))
                    return

            // retezec
                case '"':
                    tokenBuff.add(createToken(stringBuilder.toString()))
                    tokenBuff.add(createStringToken())
                    return

            // ukonceni tokenu
                case ' ':
                case '\t':
                    tokenBuff.add(createToken(stringBuilder.toString()))
                    return

            // ignore
                case '\r':
                    break

            // comment
                case ';':
                    tokenBuff.add(createToken(stringBuilder.toString()))
                    eat((char) '\n') // skip commentu
                    return

            // new line
                case '\n':
                    tokenBuff.add(createToken(stringBuilder.toString()))
                    line++
                    return

            // escape sekvence
                case '\\':
                    stringBuilder.append(getNextForEscapeSequence())
                    break

            // class
                case '@':
                    tokenBuff.add(createToken(stringBuilder.toString())) // predchazejici
                    addClassTokenToBuff(tokenBuff) // do bufferu dalsi class token
                    return

            // znaky tokenu
                default:
                    stringBuilder.append(c)
            }
        }

        String val = stringBuilder.toString()
        if (val.length() > 0) {
            tokenBuff.add(createToken(val))
        }
    }

    private Token addClassTokenToBuff(Queue<Token> tokenBuff) {
        // stringB reset, fronta
        StringBuilder sb = new StringBuilder()
        Queue<Token> tb = new LinkedList<>()

        Token t = nextNotEmptyToken(tb, sb)
        if (t != null) {
            Node n = t.node
            if (n != null && n instanceof SymbolNode) {
                Token classToken = new Token(TokenType.NODE, nodeHandler.handleClass(n.val, n.line), n.line)
                tokenBuff.add(classToken)
                tokenBuff.addAll(tb)        // pridam vsechny nabufferovane polozky
                return
            }
        }
        throw new LexerException("Wrong @class definition", t.linePosition)
    }

    private void eat(char end) {
        char c
        while ((c = reader.read()) >= 0) {
            if (c == '\n') {
                line++
            }
            if (c == end) {
                return
            }
        }
    }

    private char getNextForEscapeSequence() {
        char c
        if ((c = reader.read()) >= 0) {
            return c
        } else {
            throw new LexerException("Wrong escape sequence", line)
        }
    }


    /**
     * Recover based on bracket counter
     * */
    void recover() {
        tokenBuff.clear()
        stringBuilder.setLength(0)
        if (bracketCounter < 0) {   // zacnu ihned
            bracketCounter = 0
            return
        }

        while (bracketCounter > 0) {
            char c
            if((c = reader.read()) >= 0) {
                switch (c) {
                    case '(':
                    case '[':
                        bracketCounter++
                        break

                    case ')':
                    case ']':
                        bracketCounter--
                        break

                // retezec
                    case '"':
                        eat('"')
                        break

                // comment
                    case ';':
                        eat((char) '\n') // skip commentu
                        break

                // new line
                    case '\n':
                        line++
                        break
                }
            }
            else {  // EOS
                return
            }
        }
    }
}
