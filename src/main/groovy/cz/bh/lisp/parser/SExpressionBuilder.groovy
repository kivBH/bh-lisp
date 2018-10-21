package cz.bh.lisp.parser

import cz.bh.lisp.LispException
import cz.bh.lisp.parser.exceptions.ParserException

import cz.bh.lisp.parser.lexer.Lexer
import cz.bh.lisp.parser.lexer.Token
import cz.bh.lisp.parser.lexer.TokenType
import cz.bh.lisp.parser.sexp.ListNode
import cz.bh.lisp.parser.sexp.Node

class SExpressionBuilder {
    Lexer lexer
    int counter

    SExpressionBuilder(Reader reader) {
        this.lexer = new Lexer(reader)
        this.counter = 0
    }

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
                throw new ParserException("Wrong bracket counter", t.linePosition)

            case TokenType.START_LIST:
                counter++
                ListNode root = new ListNode(t.linePosition)
                buildOver(root, counter - 1)
                if (counter != 0) {
                    throw new ParserException("Wrong bracket counter", t.linePosition)
                }
                return root

            default:
                return t.node
        }
    }

    /**
     * Stavi S-exp nad node
     * @param node Node nad kterym se stavi
     * @param retCounter Po dosazeni stavu citace se vratit
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
