package cz.bh.lisp.lib

import cz.bh.lisp.interpreter.Executable

/**
 *
 * @version 2018-12-10
 * @author Patrik Harag
 */
abstract class NativeMacro implements Definition, Executable, Documented {

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
        return NativeMacro.name + "[?]"
    }
}
