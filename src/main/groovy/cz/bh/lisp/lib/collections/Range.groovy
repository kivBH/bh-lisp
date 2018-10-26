package cz.bh.lisp.lib.collections

import cz.bh.lisp.lib.NativeFunction
import cz.bh.lisp.lib.Preconditions

/**
 * Defines the {@code range} function.
 *
 * @version 2018-10-26
 * @author Patrik Harag
 */
class Range extends NativeFunction {

    @Override
    String getSymbol() {
        return "range"
    }

    @Override
    String getDoc() {
        return "[start end]" +
                " Returns a lazy list of nums from start (inclusive) to end (exclusive)."
    }

    @Override
    def run(List parameters) {
        Preconditions.requireParameters(parameters, 2)
        Number from = Preconditions.requireType(parameters[0], Number)
        Number to = Preconditions.requireType(parameters[1], Number)
        return from..<to
    }
}
