package cz.bh.lisp

import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.interpreter.InterpreterListener
import cz.bh.lisp.lib.LibLoader

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
abstract class Repl {

    abstract void start(Reader reader, Writer writer)

    protected Interpreter createInterpreter(Writer writer) {
        return new Interpreter(LibLoader.createGlobalContext(), new InterpreterListener() {
            @Override
            void onResult(Object result) {
                writer.println ">> $result"
                writer.flush()
            }

            @Override
            void onUnhandledException(LispException e) {
                writer.println ">> ERROR: $e"
                writer.flush()
            }

            @Override
            void onUnhandledError(Exception e) {
                def printWriter = new PrintWriter(writer)
                e.printStackTrace(printWriter)
                printWriter.flush()
                writer.flush()
            }
        })
    }

}
