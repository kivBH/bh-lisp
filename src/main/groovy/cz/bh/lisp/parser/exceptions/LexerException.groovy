package cz.bh.lisp.parser.exceptions

class LexerException extends Exception {
    int line

    LexerException(String message, int line) {
        super(message)
        this.line = line
    }

    int getLine() {
        return line
    }
}
