package cz.bh.lisp.parser

import cz.bh.lisp.parser.sexp.*
import org.junit.Test

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

    @Test(expected = NoSuchElementException.class)
    void iterableTest() {
        prep('1 2 3 4 5')

        Iterator<Node> it = b.iterator()
        for (int i = 0; i < 6; i++) {
            it.hasNext()
        }
        assert it.hasNext()

        for (int i = 1; i < 5; i++) {
            n = it.next()
            assert n instanceof IntegerNode
            assert n.val == i
        }

        assert it.hasNext()
        it.next()
        assert !it.hasNext()
        assert !it.hasNext()
        it.next()
    }

    @Test
    void iterableTest2() {
        prep('1 2 3 4 5')
        int c = 0
        int sum = 0
        for(Node node: b) {
            c++
        }
        assert c == 5
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
}
