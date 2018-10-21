package cz.bh.lisp.parser.lexer

import cz.bh.lisp.parser.sexp.DoubleNode
import cz.bh.lisp.parser.sexp.Node
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * @author Josef Baloun
 */
@RunWith(Parameterized.class)
class NodeHandlerParamTest {

    @Parameterized.Parameters(name = "{0}")
    static Collection<Object[]> data() {
        def vals = [
                ["1,", false],
                ["1.", false],
                [",1", false],
                [".1", false],

                ["1,b", false],
                ["-1.", false],
                ["-,1", false],
                ["+.1", false],

                ["1,e5", false],

                ["1e1", true],
                ["+1e1", true],
                ["-1e1", true],
                ["1e-1", true],
                ["1e+1", true],

                ["1.1E6", true],
                ["+1.2E7", true],
                ["-1.3E8", true],
                ["1.4E-9", true],
                ["1.5E+1", true],

                ["+1.1", true],
                ["-1.1", true],
                ["1.1", true]
        ]*.toArray()
    }

    private final String val
    private final boolean isDoubleNode

    NodeHandlerParamTest(String val, boolean isDoubleNode) {
        this.val = val
        this.isDoubleNode = isDoubleNode
    }

    NodeHandler nh

    @Before
    void setUp() {
        nh = new NodeHandler()
    }

    @Test
    void compare() {
        Node n = nh.handle(val, 0)
        assert isDoubleNode == (n instanceof DoubleNode)
    }
}
