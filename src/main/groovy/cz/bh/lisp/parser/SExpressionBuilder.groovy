package cz.bh.lisp.parser

import cz.bh.lisp.LispException
import cz.bh.lisp.parser.exceptions.ParserException
import cz.bh.lisp.parser.lexer.Lexer
import cz.bh.lisp.parser.lexer.Token
import cz.bh.lisp.parser.lexer.TokenType
import cz.bh.lisp.parser.sexp.LNode
import cz.bh.lisp.parser.sexp.ListLiteralNode
import cz.bh.lisp.parser.sexp.ListNode
import cz.bh.lisp.parser.sexp.Node

/**
 * Class for creating S-exp from reader
 *
 * @author Josef Baloun
 */
class SExpressionBuilder {
    Lexer lexer
    int counter

    SExpressionBuilder(Reader reader) {
        this.lexer = new Lexer(reader)
        this.counter = 0
    }

    /**
     * Builds next S-exp
     * @return Returns S-exp as Node or null if end of input reached
     */
    Node build() {
        Node n
        try {
            n = pomBuild()
        }
        catch (LispException e) {
            counter = 0
            lexer.recover()
            throw e
        }
        return n
    }

    private LNode getLNode(TokenType t, int line) {
        if (t == TokenType.START_LIST_LITERAL) {
            return new ListLiteralNode(line)
        }
        return new ListNode(line)
    }

    private Node pomBuild() {
        Token t = lexer.nextToken()
        if (t == null) {
            return null
        }

        switch (t.type) {
            case TokenType.END_LIST:
            case TokenType.END_LIST_LITERAL:
                counter--
                throw new ParserException("Wrong bracket counter", t.linePosition)  // should not occur

            case TokenType.START_LIST:
            case TokenType.START_LIST_LITERAL:
                counter++
                LNode root = getLNode(t.type, t.linePosition)
                buildOver(root, counter - 1)
                if (counter != 0) {
                    throw new ParserException("Wrong bracket counter", t.linePosition) // should not occur
                }
                return root

            default:
                return t.node
        }
    }

    /**
     * Builds S-exp over node
     * @param node Node to build over
     * @param retCounter Return if counter has this value
     * */
    private void buildOver(LNode node, int retCounter) {
        Token t
        while ((t = lexer.nextToken()) != null) {
            switch (t.type) {
                case TokenType.START_LIST:
                case TokenType.START_LIST_LITERAL:
                    counter++
                    LNode ln = getLNode(t.type, t.linePosition)
                    node.list.add(ln)
                    buildOver(ln, counter - 1)
                    break

                case TokenType.END_LIST:
                case TokenType.END_LIST_LITERAL:
                    counter--
                    if (counter == retCounter) {
                        return
                    }
                    else {
                        throw new Exception("Error in buildOver() - " + this.getClass().getName() + " input line: " + t.linePosition)
                    }
                    break

                default:
                    node.list.add(t.node)
            }
        }
    }
}
