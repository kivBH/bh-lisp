package cz.bh.lisp.parser.exceptions

class InputEndsWithinStringLexerException extends LexerException {
    InputEndsWithinStringLexerException(int line) {
        super("Input ends within a string, line: " + line, line)
    }
}
