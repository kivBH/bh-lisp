package cz.bh.lisp.lib.meta

import cz.bh.lisp.interpret.Context
import cz.bh.lisp.lib.Documented
import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code doc} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Doc extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "doc"
    }

    @Override
    String getDoc() {
        return "Returns documentation as string if available, nil otherwise."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParametersAtLeast(parameters, 1)

        def first = parameters.first()
        if (first instanceof Documented) {
            return first.doc ?: Context.NIL
        } else {
            return Context.NIL
        }
    }
}
