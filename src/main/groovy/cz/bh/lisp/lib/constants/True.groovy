package cz.bh.lisp.lib.constants

import cz.bh.lisp.lib.Definition

/**
 * Defines the {@code true} constant.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class True implements Definition {

    @Override
    String getSymbol() {
        return "true"
    }

    @Override
    Object getValue() {
        return Boolean.TRUE
    }
}
