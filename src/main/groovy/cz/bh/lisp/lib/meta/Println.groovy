package cz.bh.lisp.lib.meta

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeHighOrderFunction
import cz.bh.lisp.lib.Nil

/**
 * Defines the {@code println} function.
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
class Println extends NativeHighOrderFunction {

    @Override
    String getSymbol() {
        return "println"
    }

    @Override
    String getDoc() {
        return "[o*] Same as print followed by (newline)"
    }

    @Override
    def run(Interpreter interpreter, Context context, List parameters) {
        parameters.each {
            interpreter.stdOut.print(it)
        }
        interpreter.stdOut.println()
        return Nil.VALUE
    }
}
