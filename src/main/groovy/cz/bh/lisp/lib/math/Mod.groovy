package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code mod} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Mod extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "mod"
    }

    @Override
    String getDoc() {
        return "[n d] Returns the modulus of dividing numerator n by denominator d."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 2)
        def n = Preconditions.requireType(parameters[0], Number)
        def d = Preconditions.requireType(parameters[1], Number)
        return n % d
    }
}
