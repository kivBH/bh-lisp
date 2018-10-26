package cz.bh.lisp.parser


import cz.bh.lisp.parser.exceptions.ParserException
import cz.bh.lisp.parser.sexp.*
import org.junit.Test

/**
 * @author Josef Baloun
 */
class SExpressionBuilderTest {
    String s
    Reader r
    SExpressionBuilder b
    Node n

    void prep(String str) {
        s = str
        r = new StringReader(s)
        b = new SExpressionBuilder(r)
        n = null
    }

    void build(String str) {
        prep(str)
        n = b.build()
    }

    @Test
    void tokenizingTest() {
        build(' ( +   1  (+ 2 3)"4")')

        assert n.toString() == '(+ 1 (+ 2 3) "4")'
        assert n instanceof ListNode
        assert n.list.size() == 4
        n = n.list[2]
        assert n.toString() == "(+ 2 3)"
        assert n instanceof ListNode
        assert n.list.size() == 3
    }

    @Test
    void nodesTest() {
        build('(+ 1 4.3 "str")')

        assert n instanceof ListNode
        assert n.list[0] instanceof SymbolNode
        assert n.list[1] instanceof IntegerNode
        assert n.list[2] instanceof DoubleNode
        assert n.list[3] instanceof StringNode
    }

    @Test
    void stringNodeEscapingTest() {
        build('"s\\"t\\r \\\\"')

        assert n instanceof StringNode
        assert n.val == 's"tr \\'
    }

    @Test
    void commentTest() {
        build('(text ; comment \n)')
        assert n instanceof ListNode
        assert n.val.size() == 1
        n = n.val.get(0)
        assert n.val == "text"
    }

    @Test
    void listTest() {
        build('[1 2]')
        assert n instanceof ListNode
        assert n.val.size() == 3
        n = n.val.get(0)
        assert n.val == "list"
    }

    @Test(expected = ParserException.class)
    void wrongEscapeSequenceTest() {
        build("\\")
    }

    @Test(expected = ParserException.class)
    void inputEndInTextTest() {
        build('"')
    }

    @Test(expected = ParserException.class)
    void wrongBracketCounterTest1() {
        build(")")
    }

    @Test(expected = ParserException.class)
    void wrongBracketCounterTest2() {
        build("(")
    }

    @Test(expected = ParserException.class)
    void wrongBracketCounterTest3() {
        build("[")
    }

    @Test(expected = ParserException.class)
    void wrongBracketCounterTest4() {
        build("]")
    }

    @Test
    void classTest() {
        build('@java.lang.String')
        assert n instanceof ClassNode
    }

    @Test(expected = ParserException.class)
    void classWrongTest() {
        build('@(123)')
    }
}
