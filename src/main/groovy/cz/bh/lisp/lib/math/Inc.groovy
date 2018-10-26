package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code inc} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Inc extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "inc"
    }

    @Override
    String getDoc() {
        return "[x] Returns a number one greater than x."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 1)
        return Preconditions.requireType(parameters.first(), Number) + 1
    }
}
