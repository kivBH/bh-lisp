package cz.bh.lisp.parser.lexer

import cz.bh.lisp.parser.sexp.Node

class Token {
    final TokenType type
    final Node node
    final int linePosition

    Token(TokenType type, Node node, int linePosition) {
        this.type = type
        this.node = node
        this.linePosition = linePosition
    }
}
