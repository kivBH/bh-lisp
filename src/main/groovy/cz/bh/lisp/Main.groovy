package cz.bh.lisp

import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.interpreter.InterpreterListener
import cz.bh.lisp.lib.ExitException
import cz.bh.lisp.lib.LibLoader
import cz.bh.lisp.parser.sexp.Node

/**
 *
 * @version 2018-12-26
 * @author Patrik Harag
 */
class Main {

    static void main(String... args) {
        if (args.size() > 0) {
            def fileName = args.first()
            try {
                evalFile(fileName)
            } catch (FileNotFoundException e) {
                System.err.println("File not found: $fileName")
            }
        } else {
            startRepl()
        }
    }

    private static void evalFile(String fileName) {
        def interpreter = createInterpreter()
        def file = new File(fileName)
        file.withInputStream {
            interpreter.eval(new InputStreamReader(it, "utf-8"))
        }
    }

    private static Interpreter createInterpreter() {
        new Interpreter(LibLoader.createGlobalContext(), new InterpreterListener() {
            @Override
            void onExpressionParsed(Node node) {
                // ignore
            }

            @Override
            void onResult(Object result) {
                // ignore
            }

            @Override
            void onUnhandledException(LispException e) {
                if (e instanceof ExitException) {
                    System.exit(e.exitCode)
                } else {
                    System.err.println(e)
                }
            }

            @Override
            void onUnhandledError(Exception e) {
                e.printStackTrace(System.err)
            }
        })
    }

    private static void startRepl() {
        println "BH Lisp REPL"

        def repl = new ConsoleRepl()
        repl.start(new InputStreamReader(System.in), new OutputStreamWriter(System.out))
    }

}
