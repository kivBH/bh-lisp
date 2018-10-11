package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code max} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Max extends NativeFunction {

    @Override
    String getSymbol() {
        return "max"
    }

    @Override
    String getDoc() {
        return "[n+] Returns the greatest of the nums."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)
        Number temp = Preconditions.requireType(parameters.first(), Number)
        parameters.subList(1, parameters.size()).each {
            Number n = Preconditions.requireType(it, Number)
            if (n > temp) {
                temp = n
            }
        }
        return temp
    }
}
