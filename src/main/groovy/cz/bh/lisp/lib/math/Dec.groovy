package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code dec} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Dec extends NativeFunction {

    @Override
    String getSymbol() {
        return "dec"
    }

    @Override
    String getDoc() {
        return "[x] Returns a number one less than x."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 1)
        return Preconditions.requireType(parameters.first(), Number) - 1
    }
}
