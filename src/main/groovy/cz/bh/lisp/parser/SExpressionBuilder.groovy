package cz.bh.lisp.parser

import cz.bh.lisp.parser.exceptions.WrongBracketCounterParserException
import cz.bh.lisp.parser.lexer.Lexer
import cz.bh.lisp.parser.lexer.Token
import cz.bh.lisp.parser.lexer.TokenType
import cz.bh.lisp.parser.sexp.ListNode
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.NodeHandler

class SExpressionBuilder implements Iterable<Node> {
    Lexer lexer
    int counter
    NodeHandler nodeHandler

    SExpressionBuilder(Reader reader) {
        this.lexer = new Lexer(reader)
        this.counter = 0
        this.nodeHandler = new NodeHandler()
    }

    Node build() {
        Token t = lexer.nextToken()
        if (t == null) {
            return null
        }

        switch (t.type) {
            case TokenType.END_LIST:
                counter--
                throw new WrongBracketCounterParserException(counter, t.linePosition)

            case TokenType.START_LIST:
                counter++
                ListNode root = new ListNode(t.linePosition)
                buildOver(root, counter - 1)
                if (counter != 0) {
                    throw new WrongBracketCounterParserException(counter, t.linePosition)
                }
                return root

            default:
                return nodeHandler.handleToken(t)
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
                    node.list.add(nodeHandler.handleToken(t))
            }
        }
    }

    @Override
    Iterator<Node> iterator() {
        return new IteratorImpl()
    }

    private class IteratorImpl implements Iterator<Node> {
        Node buffNext

        IteratorImpl() {
            buffNext = null
        }

        @Override
        boolean hasNext() {
            if (buffNext == null) {
                buffNext = SExpressionBuilder.this.build()
            }
            return buffNext != null
        }

        @Override
        Node next() {
            if(hasNext()) {
                Node ret = buffNext
                buffNext = null
                return ret
            }
            throw new NoSuchElementException()
        }
    }
}
