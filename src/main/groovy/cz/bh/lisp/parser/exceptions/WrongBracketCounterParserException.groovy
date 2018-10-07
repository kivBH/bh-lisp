package cz.bh.lisp.parser.exceptions

class WrongBracketCounterParserException extends Exception {
    int counter

    WrongBracketCounterParserException(int counter) {
        super("Wrong bracket counter")
    }

    int getCounter() {
        return counter
    }
}
