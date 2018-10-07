package cz.bh.lisp

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
class LispException extends RuntimeException {

    LispException(String message) {
        super(message)
    }

    LispException(String message, int line) {
        super(message + format(line))
    }

    LispException(String message, int line, Throwable cause) {
        super(message + format(line), cause)
    }

    LispException(Throwable cause) {
        super(cause)
    }

    private static String format(int line) {
        (line != 1 ? ", on line: $line" : "")
    }

    @Override
    String toString() {
        return super.toString() + (cause ? ":\n    " + cause : "")
    }
}
