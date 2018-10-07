package cz.bh.lisp.parser.lexer

import cz.bh.lisp.parser.exceptions.EscapeSequenceLexerException
import org.junit.Test

class LexerTest {

    @Test(expected = EscapeSequenceLexerException.class)
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
        assert t.val == "("
        assert t.linePosition == 1
        assert t.type == TokenType.START_LIST

        t = l.nextToken()
        assert t.val == "+"
        assert t.linePosition == 1
        assert t.type == TokenType.UNKNOWN

        t = l.nextToken()
        assert t.val == "1"
        assert t.linePosition == 1
        assert t.type == TokenType.UNKNOWN

        t = l.nextToken()
        assert t.val == "2"
        assert t.linePosition == 1
        assert t.type == TokenType.UNKNOWN

        t = l.nextToken()
        assert t.val == ")"
        assert t.linePosition == 1
        assert t.type == TokenType.END_LIST

        t = l.nextToken()
        assert t == null

        t = l.nextToken()
        assert t == null
    }
}
