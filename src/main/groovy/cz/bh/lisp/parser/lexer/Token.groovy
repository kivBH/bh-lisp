package cz.bh.lisp.parser.lexer

class Token {
    final TokenType type
    final String val
    final int linePosition

    Token(TokenType type, String val, int linePosition) {
        this.type = type
        this.val = val
        this.linePosition = linePosition
    }
}
