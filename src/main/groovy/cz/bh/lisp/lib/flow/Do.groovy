package cz.bh.lisp.lib.flow

import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Nil

/**
 * Defines the {@code do} function.
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
class Do extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "do"
    }

    @Override
    String getDoc() {
        return "[expr*] Evaluates the expressions in order and returns the value of the last." +
                " If no expressions are supplied, returns nil."
    }

    @Override
    def run(List parameters) {
        return parameters.isEmpty() ? Nil.VALUE : parameters.last()
    }
}
