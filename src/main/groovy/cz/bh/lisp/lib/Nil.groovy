package cz.bh.lisp.lib

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
final class Nil {

    static final Nil VALUE = new Nil()

    private Nil() {
        // hidden constructor
    }

    @Override
    String toString() {
        return "nil"
    }
}
