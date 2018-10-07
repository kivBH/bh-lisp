package cz.bh.lisp.parser.sexp

import cz.bh.lisp.parser.lexer.Token
import cz.bh.lisp.parser.lexer.TokenType
import org.junit.Before
import org.junit.Test

class NodeHandlerTest {
    NodeHandler nodeHandler

    @Before
    void setUp() throws Exception {
        nodeHandler = new NodeHandler()
    }

    @Test
    void SymbolTest() {
        String val = "vdsaf"
        Token t = new Token(TokenType.UNKNOWN, val, 0)
        Node n = nodeHandler.handleToken(t)
        assert n instanceof SymbolNode
        assert n.val == val
    }

    @Test
    void StringTest() {
        String val = "vdsaf"
        Token t = new Token(TokenType.STRING, val, 0)
        Node n = nodeHandler.handleToken(t)
        assert n instanceof StringNode
        assert n.val == val
    }

    @Test
    void IntegerTest() {
        String val = "12"
        Token t = new Token(TokenType.UNKNOWN, val, 0)
        Node n = nodeHandler.handleToken(t)
        assert n instanceof IntegerNode
        assert n.val == 12
    }

    @Test
    void DoubleTest() {
        String val = "12.5"
        Token t = new Token(TokenType.UNKNOWN, val, 0)
        Node n = nodeHandler.handleToken(t)
        assert n instanceof DoubleNode
        assert n.val == 12.5
    }
}
