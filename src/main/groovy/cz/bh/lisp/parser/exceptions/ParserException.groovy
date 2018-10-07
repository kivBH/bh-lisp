package cz.bh.lisp.parser.exceptions

class ParserException extends Exception {
    int line

    ParserException(String message, int line) {
        super(message)
        this.line = line
    }

    int getLine() {
        return line
    }
}
