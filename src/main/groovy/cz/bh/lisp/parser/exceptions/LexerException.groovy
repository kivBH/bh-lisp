package cz.bh.lisp.parser.exceptions

import cz.bh.lisp.LispException

class LexerException extends LispException {

    LexerException(String message, int line) {
        super(message, line)
    }
}
