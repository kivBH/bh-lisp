package cz.bh.lisp.lib.meta

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.lib.NativeHighOrderFunction
import cz.bh.lisp.lib.Nil

/**
 * Defines the {@code print} function.
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
class Print extends NativeHighOrderFunction {

    @Override
    String getSymbol() {
        return "print"
    }

    @Override
    String getDoc() {
        return "[o*] Prints the object(s) to the standard output stream."
    }

    @Override
    def run(Interpreter interpreter, Context context, List parameters) {
        parameters.each {
            interpreter.stdOut.print(it)
        }
        return Nil.VALUE
    }
}
