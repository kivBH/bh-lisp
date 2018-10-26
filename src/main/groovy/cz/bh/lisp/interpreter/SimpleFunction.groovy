package cz.bh.lisp.interpreter

import cz.bh.lisp.parser.sexp.Node

/**
 *
 * @version 2018-10-26
 * @author Patrik Harag
 */
abstract class SimpleFunction extends HighOrderFunction {

    @Override
    final Object execute(Interpreter interpreter, Context context, List<Node> parameters) {
        run(parameters.collect { interpreter.eval(it, context) })
    }

    @Override
    final def run(Interpreter interpreter, Context context, List parameters) {
        run(parameters)
    }

    abstract def run(List parameters)

}
