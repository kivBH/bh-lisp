package cz.bh.lisp.lib

import cz.bh.lisp.interpreter.Function

/**
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
abstract class NativeFunction extends Function implements Definition, Documented {

    @Override
    abstract String getSymbol()

    @Override
    String getDoc() {
        return null
    }

    @Override
    Object getValue() {
        return this
    }
}
