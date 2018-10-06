package cz.bh.lisp.parser

import cz.bh.lisp.parser.exceptions.WrongBracketCounterParserException
import cz.bh.lisp.parser.lexer.Lexer
import cz.bh.lisp.parser.lexer.Token
import cz.bh.lisp.parser.lexer.TokenType
import cz.bh.lisp.parser.sexp.ListNode
import cz.bh.lisp.parser.sexp.NodeHandler

class SExpressionBuilder {
    Lexer lexer
    ListNode root
    int counter

    SExpressionBuilder(Reader reader) {
        this.lexer = new Lexer(reader)
        root = new ListNode(0)
        int counter = 0
    }

    ListNode build() {
        buildOver(root, counter)

        if (counter != 0) {
            throw new WrongBracketCounterParserException(counter)
        }
        return root.list[0]
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
                    node.list.add(NodeHandler.handleToken(t))
            }
        }
    }
}
