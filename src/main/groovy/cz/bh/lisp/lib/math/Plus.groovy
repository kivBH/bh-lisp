package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code +} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Plus extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "+"
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
