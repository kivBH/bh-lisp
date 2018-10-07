package cz.bh.lisp.lib.collections

import cz.bh.lisp.interpreter.Context
import cz.bh.lisp.lib.NativeFunctionDefinition

/**
 * Defines the {@code str} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Str extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "str"
    }

    @Override
    String getDoc() {
        return "[x*] With no args, returns the empty string. With one arg x, returns x.toString()." +
                " (str nil) returns the empty string. With more than one arg, returns the" +
                " concatenation of the str values of the args."
    }

    @Override
    def run(List parameters) {
        String out = ""
        parameters.each {
            if (it != Context.NIL) {
                out += it.toString()
            }
        }
        return out
    }
}
