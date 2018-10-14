package cz.bh.lisp.parser.exceptions

class WrongBracketCounterParserException extends ParserException {
    int counter

    WrongBracketCounterParserException(int counter, int line) {
        super("Wrong bracket counter", line)
        this.counter = counter
    }

    int getCounter() {
        return counter
    }
}
