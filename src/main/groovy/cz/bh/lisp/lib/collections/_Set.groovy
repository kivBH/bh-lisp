package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code set} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class _Set extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "set"
    }

    @Override
    String getDoc() {
        return "[coll] Returns a set of the distinct elements of coll."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 1)
        def coll = Preconditions.requireType(parameters.first(), Collection)
        return new HashSet<>(coll)
    }
}
