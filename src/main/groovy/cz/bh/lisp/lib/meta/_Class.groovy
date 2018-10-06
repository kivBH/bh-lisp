package cz.bh.lisp.lib.meta

import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code class} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class _Class extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "class"
    }

    @Override
    String getDoc() {
        return "[x] Returns the Class of x."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 1)
        return parameters.first().class
    }
}
