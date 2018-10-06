package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code !=} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class NotEquals extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "!="
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)

        for (int i = 1; i < parameters.size(); i++) {
            if (parameters[i - 1] == parameters[i])
                return false
        }
        return true
    }
}
