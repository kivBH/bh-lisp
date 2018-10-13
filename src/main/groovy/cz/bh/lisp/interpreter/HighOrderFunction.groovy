package cz.bh.lisp.interpreter

import cz.bh.lisp.parser.sexp.Node

/**
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
abstract class HighOrderFunction implements Executable {

    @Override
    Object execute(Interpreter interpreter, Context context, List<Node> parameters) {
        run(interpreter, context, parameters.collect { interpreter.eval(it, context) })
    }

    abstract def run(Interpreter interpreter, Context context, List parameters)

}
