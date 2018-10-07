package cz.bh.lisp.interpret

import cz.bh.lisp.parser.sexp.Node

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
abstract class SimpleFunction implements Executable {

    @Override
    Object run(Interpreter interpreter, Context context, List<Node> parameters) {
        run(parameters.collect { interpreter.eval(it, context) })
    }

    abstract def run(List parameters)

}
