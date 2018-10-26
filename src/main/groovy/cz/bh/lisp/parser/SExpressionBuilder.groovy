package cz.bh.lisp.parser

import cz.bh.lisp.LispException
import cz.bh.lisp.parser.exceptions.ParserException
import cz.bh.lisp.parser.lexer.Lexer
import cz.bh.lisp.parser.lexer.Token
import cz.bh.lisp.parser.lexer.TokenType
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

    private Node pomBuild() {
        Token t = lexer.nextToken()
        if (t == null) {
            return null
        }

        switch (t.type) {
            case TokenType.END_LIST:
                counter--
                throw new ParserException("Wrong bracket counter", t.linePosition)  // should not occur

            case TokenType.START_LIST:
                counter++
                ListNode root = new ListNode(t.linePosition)
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
    private void buildOver(ListNode node, int retCounter) {
        Token t
        while ((t = lexer.nextToken()) != null) {
            switch (t.type) {
                case TokenType.START_LIST:
                    counter++
                    ListNode ln = new ListNode(t.linePosition)
                    node.list.add(ln)
                    buildOver(ln, counter - 1)
                    break

                case TokenType.END_LIST:
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
