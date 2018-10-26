package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code min} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Min extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "min"
    }

    @Override
    String getDoc() {
        return "[n+] Returns the least of the nums."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)
        Number temp = Preconditions.requireType(parameters.first(), Number)
        parameters.drop(1).each {
            Number n = Preconditions.requireType(it, Number)
            if (n < temp) {
                temp = n
            }
        }
        return temp
    }
}
