package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code /} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Divide extends NativeFunction {

    @Override
    String getSymbol() {
        return "/"
    }

    @Override
    String getDoc() {
        return "[n d*] If no denominators are supplied, returns 1/numerator," +
                " else returns numerator divided by all of the denominators."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)

        Number first = Preconditions.requireType(parameters.first(), Number)
        if (parameters.size() == 1) {
            return BigInteger.ONE / first
        } else {
            Number acc = first
            parameters.drop(1).each {
                acc /= Preconditions.requireType(it, Number)
            }
            return acc
        }
    }
}
