package cz.bh.lisp.lib

/**
 *
 * @version 2018-10-12
 * @author Patrik Harag
 */
final class Nil {

    static final Nil VALUE = new Nil()

    static def map(object) {
        return (object == null) ? VALUE : object
    }


    private Nil() {
        // hidden constructor
    }

    @Override
    String toString() {
        return "nil"
    }
}
