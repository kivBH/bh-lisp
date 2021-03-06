package cz.bh.lisp.lib

import cz.bh.lisp.interpreter.SimpleFunction

/**
 *
 * @version 2018-12-10
 * @author Patrik Harag
 */
abstract class NativeSimpleFunction extends SimpleFunction implements Definition, Documented {

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

    @Override
    String toString() {
        return NativeSimpleFunction.name + "[?]"
    }
}
