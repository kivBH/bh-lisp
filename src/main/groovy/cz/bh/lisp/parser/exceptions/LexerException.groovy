package cz.bh.lisp.parser.exceptions

import cz.bh.lisp.LispException

class LexerException extends LispException {
    final int line

    LexerException(String message, int line) {
        super(message)
        this.line = line
    }
}
