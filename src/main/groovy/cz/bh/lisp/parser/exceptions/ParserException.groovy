package cz.bh.lisp.parser.exceptions

import cz.bh.lisp.LispException

/**
 * @author Josef Baloun
 */
class ParserException extends LispException {

    ParserException(String message, int line) {
        super(message, line)
    }
}
