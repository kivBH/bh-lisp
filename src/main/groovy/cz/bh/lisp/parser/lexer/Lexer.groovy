package cz.bh.lisp.parser.lexer


import cz.bh.lisp.parser.exceptions.ParserException
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.StringNode
import cz.bh.lisp.parser.sexp.SymbolNode

/**
 * Class for reading input and parsing into tokens
 *
 * @author Josef Baloun
 */
class Lexer {

    private enum BracketType {
        LEFT("("), RIGHT(")"), LEFT_LIST("["), RIGHT_LIST("]")

        String val

        BracketType(String val) {
            this.val = val
        }
    }

    ReaderWrapper reader
    int line
    StringBuilder stringBuilder
    Queue<Token> tokenBuff
    NodeHandler nodeHandler
    Stack<BracketType> bracketStack

    Lexer(Reader reader) {
        this.reader = new ReaderWrapper(reader)
        this.line = 1
        this.stringBuilder = new StringBuilder()
        this.tokenBuff = new LinkedList<>()
        this.tokenBuff.clear()
        this.nodeHandler = new NodeHandler()
        this.bracketStack = new Stack<>()
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

        // EOS
        if (!bracketStack.isEmpty()) {
            BracketType last = bracketStack.peek()
            BracketType expected
            if (last == BracketType.LEFT) {
                expected = BracketType.RIGHT
            }
            else {
                expected = BracketType.RIGHT_LIST
            }
            throw new ParserException("Expected '" + expected.val + "'", line)
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
        throw new ParserException("Input ends within a string", line)
    }

    private void checkBracketStack(BracketType actual, BracketType expected) {
        if (bracketStack.isEmpty()) {
            throw new ParserException("Expected '" + expected.val + "', but was '" + actual.val + "'", line)
        }
        BracketType last = bracketStack.peek()
        if (last != expected) {
            throw new ParserException("For '" + actual.val + "' is expected '" + expected.val + "' before, but was '" + last.val + "'", line)
        }
        bracketStack.pop()
    }

    private Token createListToken(BracketType actual) {
        switch (actual) {
            case BracketType.RIGHT:
                checkBracketStack(actual, BracketType.LEFT)
                return new Token(TokenType.END_LIST, null, line)

            case BracketType.RIGHT_LIST:
                checkBracketStack(actual, BracketType.LEFT_LIST)
                return new Token(TokenType.END_LIST_LITERAL, null, line)

            case BracketType.LEFT:
                bracketStack.push(actual)
                return new Token(TokenType.START_LIST, null, line)

            case BracketType.LEFT_LIST:
                bracketStack.push(actual)
                return new Token(TokenType.START_LIST_LITERAL, null, line)
        }
    }

    private void buffNextToken(Queue<Token> tokenBuff, StringBuilder stringBuilder) {
        stringBuilder.setLength(0)  // reset
        char c
        while ((c = reader.read()) >= 0) {    // -1 end of the stream

            switch (c) {
            // samostatny token
                case '(':
                    tokenBuff.add(createToken(stringBuilder.toString())) // token predchazejici
                    tokenBuff.add(createListToken(BracketType.LEFT))                // samostatny token pozdeji
                    return
                case ')':
                    tokenBuff.add(createToken(stringBuilder.toString()))    // token predchazejici
                    tokenBuff.add(createListToken(BracketType.RIGHT))                     // samostatny token na pozdeji
                    return
                case ']':
                    tokenBuff.add(createToken(stringBuilder.toString()))    // token predchazejici
                    tokenBuff.add(createListToken(BracketType.RIGHT_LIST))                     // samostatny token na pozdeji
                    return
                case '[':
                    tokenBuff.add(createToken(stringBuilder.toString()))    // token predchazejici
                    tokenBuff.add(createListToken(BracketType.LEFT_LIST))
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
        throw new ParserException("Wrong @class definition", t.linePosition)
    }

    private void eat(char end) {
        char c
        while ((c = reader.read()) >= 0) {
            if (c == '\n' as char) {
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
            if (c == '\n' as char) {
                line++
            }
            return c
        } else {
            throw new ParserException("Wrong escape sequence", line)
        }
    }


    /**
     * Recover based on bracket stack
     * */
    void recover() {
        tokenBuff.clear()
        stringBuilder.setLength(0)

        while (!bracketStack.isEmpty()) {
            char c
            if((c = reader.read()) >= 0) {
                switch (c) {
                    case '(':
                        bracketStack.push(BracketType.LEFT)
                        break
                    case '[':
                        bracketStack.push(BracketType.LEFT_LIST)
                        break

                    case ')':
                        if (bracketStack.peek() == BracketType.LEFT) {
                            bracketStack.pop()
                        }
                        break
                    case ']':
                        if (bracketStack.peek() == BracketType.LEFT_LIST) {
                            bracketStack.pop()
                        }
                        break

                // escape sekvence
                    case '\\':
                        try {
                            getNextForEscapeSequence()
                        }
                        catch (ParserException e) {}    // EOS - ignore when recovering
                        break

                // retezec
                    case '"':
                        while ((c = reader.read()) >= 0) {
                            if (c == '\n' as char) {
                                line++
                            }
                            if (c == '\\' as char) {
                                try {
                                    getNextForEscapeSequence()
                                }
                                catch (ParserException e) {}    // EOS - ignore when recovering
                            }
                            if (c == '"' as char) {
                                break
                            }
                        }
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
                bracketStack.clear()
                return
            }
        }
    }
}
