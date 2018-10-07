package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code -} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Minus extends NativeFunction {

    @Override
    String getSymbol() {
        return "-"
    }

    @Override
    String getDoc() {
        return "If no ys are supplied, returns the negation of x, else subtracts" +
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
            parameters.subList(1, parameters.size()).each {
                acc -= Preconditions.requireType(it, Number)
            }
            return acc
        }
    }
}
