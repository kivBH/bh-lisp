package cz.bh.lisp.lib

/**
 *
 * @version 2018-10-14
 * @author Patrik Harag
 */
final class Nil {

    static final Nil VALUE = new Nil()

    static def wrap(object) {
        return (object == null) ? VALUE : object
    }

    static def unwrap(object) {
        return (object instanceof Nil) ? null : object
    }


    private Nil() {
        // hidden constructor
    }

    @Override
    String toString() {
        return "nil"
    }
}
