package cz.bh.lisp.parser.exceptions

import cz.bh.lisp.LispException

class ParserException extends LispException {
    int line

    ParserException(String message, int line) {
        super(message)
        this.line = line
    }

    int getLine() {
        return line
    }
}
