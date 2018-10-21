package cz.bh.lisp.parser.lexer

import cz.bh.lisp.parser.lexer.NodeHandler
import cz.bh.lisp.parser.sexp.DoubleNode
import cz.bh.lisp.parser.sexp.IntegerNode
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.SymbolNode
import org.junit.Before
import org.junit.Test

/**
 * @author Josef Baloun
 */
class NodeHandlerTest {
    NodeHandler nodeHandler

    @Before
    void setUp() throws Exception {
        nodeHandler = new NodeHandler()
    }

    @Test
    void SymbolTest() {
        String val = "vdsaf"
        int line = 2
        Node n = nodeHandler.handle(val, line)
        assert n instanceof SymbolNode
        assert n.val == val
        assert n.line == line
    }

    @Test
    void IntegerTest() {
        String val = "12"
        int line = 2
        Node n = nodeHandler.handle(val, line)
        assert n instanceof IntegerNode
        assert n.val == new BigInteger(val)
        assert n.line == line
    }

    @Test
    void DoubleTest() {
        String val = "12.5"
        int line = 2
        Node n = nodeHandler.handle(val, line)
        assert n instanceof DoubleNode
        assert n.val == new BigDecimal(val)
        assert n.line == line
    }
}
