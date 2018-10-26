package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code -} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Minus extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "-"
    }

    @Override
    String getDoc() {
        return "[x ys*] If no ys are supplied, returns the negation of x, else subtracts" +
                " the ys from x and returns the result."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)

        Number first = Preconditions.requireType(parameters.first(), Number)
        if (parameters.size() == 1) {
            return - first
        } else {
            Number acc = first
            parameters.drop(1).each {
                acc -= Preconditions.requireType(it, Number)
            }
            return acc
        }
    }
}
