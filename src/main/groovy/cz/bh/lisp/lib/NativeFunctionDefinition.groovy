package cz.bh.lisp.lib

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
abstract class NativeFunctionDefinition extends NativeFunction implements Definition, Documented {

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
