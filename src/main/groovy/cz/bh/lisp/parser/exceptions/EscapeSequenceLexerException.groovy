package cz.bh.lisp.parser.exceptions

class EscapeSequenceLexerException extends LexerException {
    EscapeSequenceLexerException(int line) {
        super("Wrong escape sequence on line " + line + ".", line)
    }
}
