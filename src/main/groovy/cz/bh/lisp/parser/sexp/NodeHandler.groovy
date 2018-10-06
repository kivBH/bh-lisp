package cz.bh.lisp.parser.sexp

import cz.bh.lisp.parser.lexer.Token

class NodeHandler {
    static Node handleToken(Token token) {
        return new SymbolNode(token.val, token.linePosition)
    }
}
