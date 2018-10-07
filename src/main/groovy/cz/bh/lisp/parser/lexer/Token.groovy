package cz.bh.lisp.parser.lexer

class Token {
    TokenType type
    String val
    int linePosition

    Token(TokenType type, String val, int linePosition) {
        this.type = type
        this.val = val
        this.linePosition = linePosition
    }

    TokenType getType() {
        return type
    }

    String getVal() {
        return val
    }

    int getLinePosition() {
        return linePosition
    }
}
