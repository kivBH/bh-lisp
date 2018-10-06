package cz.bh.lisp.lib.constants

import cz.bh.lisp.interpret.Context
import cz.bh.lisp.lib.Definition

/**
 * Defines the {@code nil} constant (aka null).
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Nil implements Definition {

    @Override
    String getSymbol() {
        return "nil"
    }

    @Override
    Object getValue() {
        return Context.NIL
    }
}
