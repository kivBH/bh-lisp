package cz.bh.lisp.lib.logical

import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code not} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Not extends NativeFunctionDefinition {

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
