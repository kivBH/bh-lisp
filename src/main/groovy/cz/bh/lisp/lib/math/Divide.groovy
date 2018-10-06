package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code /} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Divide extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "/"
    }

    @Override
    String getDoc() {
        return "If no denominators are supplied, returns 1/numerator,\n" +
                "else returns numerator divided by all of the denominators."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)

        Number first = Preconditions.requireType(parameters.first(), Number)
        if (parameters.size() == 1) {
            return BigInteger.ONE / first
        } else {
            Number acc = first
            parameters.subList(1, parameters.size()).each {
                acc /= Preconditions.requireType(it, Number)
            }
            return acc
        }
    }
}
