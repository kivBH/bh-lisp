package cz.bh.lisp.parser.sexp

import cz.bh.lisp.parser.SExpressionBuilder
import org.junit.Test

/**
 * @author Josef Baloun
 */
class NodeTest {
    @Test
    void getValTest() {
        String s = "+"
        Reader r = new StringReader(s)
        SExpressionBuilder b = new SExpressionBuilder(r)
        Node n = b.build()
        assert n instanceof SymbolNode
        assert n.getVal() == "+"
    }
}
