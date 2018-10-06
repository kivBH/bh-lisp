package cz.bh.lisp.lib

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class CloningUtils {

    static <T> T clone(T object) {
        // TODO: handle various exceptions here...
        return object.clone() as T
    }

}
