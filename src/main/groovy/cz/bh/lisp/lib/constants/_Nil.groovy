package cz.bh.lisp.lib.constants

import cz.bh.lisp.lib.Definition
import cz.bh.lisp.lib.Nil

/**
 * Defines the {@code nil} constant (aka null).
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class _Nil implements Definition {

    @Override
    String getSymbol() {
        return "nil"
    }

    @Override
    Object getValue() {
        return Nil.VALUE
    }
}
