package cz.bh.lisp.parser.lexer


import cz.bh.lisp.parser.exceptions.ParserException
import org.junit.Test

/**
 * @author Josef Baloun
 */
class LexerTest {

    @Test(expected = ParserException.class)
    void wrongEscapeSequenceTest() {
        String s = "\\"
        Reader r = new StringReader(s)
        Lexer l = new Lexer(r)
        l.nextToken()
    }

    @Test
    void test() {
        String s = " ( +   1  2)"
        Reader r = new StringReader(s)
        Lexer l = new Lexer(r)

        Token t = l.nextToken()
        assert t.node == null
        assert t.linePosition == 1
        assert t.type == TokenType.START_LIST

        t = l.nextToken()
        assert t.node.val == "+"
        assert t.linePosition == 1
        assert t.type == TokenType.NODE

        t = l.nextToken()
        assert t.node.val == new BigInteger("1")
        assert t.linePosition == 1
        assert t.type == TokenType.NODE

        t = l.nextToken()
        assert t.node.val == new BigInteger("2")
        assert t.linePosition == 1
        assert t.type == TokenType.NODE

        t = l.nextToken()
        assert t.node == null
        assert t.linePosition == 1
        assert t.type == TokenType.END_LIST

        t = l.nextToken()
        assert t == null

        t = l.nextToken()
        assert t == null
    }
}
