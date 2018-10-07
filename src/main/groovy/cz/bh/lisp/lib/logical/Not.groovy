package cz.bh.lisp.lib.logical

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code not} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Not extends NativeFunction {

    @Override
    String getSymbol() {
        return "not"
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 1)
        return !Preconditions.requireType(parameters.first(), Boolean)
    }
}
