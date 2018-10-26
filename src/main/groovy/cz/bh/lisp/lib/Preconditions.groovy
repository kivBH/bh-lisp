package cz.bh.lisp.lib

import cz.bh.lisp.parser.sexp.ListLiteralNode
import cz.bh.lisp.parser.sexp.Node
import cz.bh.lisp.parser.sexp.SymbolNode

/**
 *
 * @version 2018-10-26
 * @author Patrik Harag
 */
class Preconditions {

    static void requireParameters(List parameters, int count) {
        if (parameters.size() != count) {
            throw new IllegalArgumentException(
                    "Expected number of parameters: ${count}, but was: ${parameters.size()}")
        }
    }

    static void requireParameters(List parameters, Range<Integer> range) {
        if (!(parameters.size() in range)) {
            throw new IllegalArgumentException(
                    "Expected number of parameters: ${range}, but was: ${parameters.size()}")
        }
    }

    static void requireParametersAtLeast(List parameters, int count) {
        if (parameters.size() < count) {
            throw new IllegalArgumentException(
                    "Expected number of parameters: ${count}+, but was: ${parameters.size()}")
        }
    }

    static void requireParametersMultipleOf(List parameters, int mod) {
        if (parameters.size() % mod != 0) {
            throw new IllegalArgumentException(
                    "Expected number of parameters is multiple of ${mod}, but was: ${parameters.size()}")
        }
    }

    static List<Node> requireListLiteralNodeForBindingParameterMultipleOf(Node n, int mod) {
        if(n instanceof ListLiteralNode) {
            if (n.list.size() % mod != 0) {
                throw new IllegalArgumentException(
                        "Expected number of binding parameters is multiple of ${mod}, but was: ${n.list.size()}")
            }
            return n.list
        }
        else {
            throw new IllegalArgumentException(
                    "Expecting binding parameters in list literals []")
        }
    }

    static String requireSymbolNodeForBinding(instance) {
        if (instance instanceof SymbolNode) {
            return instance.val
        }

        throw new IllegalArgumentException(
                    "Symbol expected in binding, but was: ${instance.class}")
    }

    static <T> T requireType(instance, Class<T> clazz) {
        if (!clazz.isInstance(instance)) {
            throw new IllegalArgumentException(
                    "Expected parameter type: ${clazz}, but was: ${instance.class}")
        }
        return (T) instance
    }

}
