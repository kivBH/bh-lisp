package cz.bh.lisp.lib.constants

import cz.bh.lisp.lib.Definition

/**
 * Defines the {@code false} constant.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class False implements Definition {

    @Override
    String getSymbol() {
        return "false"
    }

    @Override
    Object getValue() {
        return Boolean.FALSE
    }
}
