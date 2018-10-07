package cz.bh.lisp.lib.logical

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code or} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Or extends NativeFunction {

    @Override
    String getSymbol() {
        return "or"
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)

        parameters.each {
            if (Preconditions.requireType(it, Boolean))
                return true
        }
        return false
    }
}
