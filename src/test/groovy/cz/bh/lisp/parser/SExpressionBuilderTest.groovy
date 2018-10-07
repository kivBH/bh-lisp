package cz.bh.lisp.parser

import cz.bh.lisp.parser.sexp.ListNode
import org.junit.Test

class SExpressionBuilderTest {
    @Test
    void test() {
        String s = " ( +   1  (+ 2 3) 4)"
        Reader r = new StringReader(s)
        SExpressionBuilder b = new SExpressionBuilder(r)
        ListNode ln = b.build()

        String sexp = ln.toString()
        assert sexp == "(+ 1 (+ 2 3) 4)"
        assert ln.list.size() == 4

        ln = ln.list[2]
        sexp = ln.toString()
        assert sexp == "(+ 2 3)"
        assert ln.list.size() == 3
    }
}
