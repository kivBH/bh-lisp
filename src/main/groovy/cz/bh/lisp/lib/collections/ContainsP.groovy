package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code contains?} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class ContainsP extends NativeFunction {

    @Override
    String getSymbol() {
        return "contains?"
    }

    @Override
    String getDoc() {
        return "[coll k] Returns true if the coll contains the lookup key k, otherwise returns false."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 2)
        def coll = parameters[0]
        def key = parameters[1]

        if (coll instanceof Collection) {
            return coll.contains(key)
        } else {
            throw new IllegalArgumentException("Collection type not supported: " + coll.class)
        }
    }
}
