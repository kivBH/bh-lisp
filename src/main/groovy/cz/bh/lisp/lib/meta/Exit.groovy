package cz.bh.lisp.lib.meta

import cz.bh.lisp.lib.ExitException
import cz.bh.lisp.lib.NativeSimpleFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code exit} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Exit extends NativeSimpleFunction {

    @Override
    String getSymbol() {
        return "exit"
    }

    @Override
    String getDoc() {
        return "[return-code] Terminates the currently running program."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 1)
        def exitCode = Preconditions.requireType(parameters.first(), Number).toInteger()
        throw new ExitException(exitCode)
    }
}
