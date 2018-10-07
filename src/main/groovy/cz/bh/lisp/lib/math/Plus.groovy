package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code +} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Plus extends NativeFunction {

    @Override
    String getSymbol() {
        return "+"
    }

    @Override
    String getDoc() {
        return "Returns the sum of nums. (+) returns 0."
    }

    @Override
    def run(List parameters) {
        Number acc = BigInteger.ZERO
        parameters.each {
            acc += Preconditions.requireType(it, Number)
        }
        return acc
    }
}
