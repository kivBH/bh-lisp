package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code *} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Multiply extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "*"
    }

    @Override
    String getDoc() {
        return "[n*] Returns the product of nums. (*) returns 1."
    }

    @Override
    def run(List parameters) {
        Number acc = BigInteger.ONE
        parameters.each {
            acc *= Preconditions.requireType(it, Number)
        }
        return acc
    }
}
