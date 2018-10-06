package cz.bh.lisp.lib.meta

import cz.bh.lisp.lib.NativeFunctionDefinition
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code instance?} function.
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class InstanceP extends NativeFunctionDefinition {

    @Override
    String getSymbol() {
        return "instance?"
    }

    @Override
    String getDoc() {
        return "[c x] Evaluates x and tests if it is an instance of the class c." +
                " Returns true or false"
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 2)
        def clazz = Preconditions.requireType(parameters[0], Class)
        def object = parameters[1]

        return clazz.isInstance(object)
    }
}
