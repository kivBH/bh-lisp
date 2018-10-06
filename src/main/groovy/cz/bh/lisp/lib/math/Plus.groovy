package cz.bh.lisp.lib.math

import cz.bh.lisp.lib.Definition

/**
 * Defines the {@code +} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Plus implements Definition {

    @Override
    String getSymbol() {
        return "+"
    }

    @Override
    Object getValue() {
        // TODO: function definition
        return 42
    }
}
