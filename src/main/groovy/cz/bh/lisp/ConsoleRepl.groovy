package cz.bh.lisp

import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.interpreter.InterpreterListener
import cz.bh.lisp.lib.ExitException
import cz.bh.lisp.lib.LibLoader
import cz.bh.lisp.parser.sexp.Node

/**
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
class ConsoleRepl {

    void start(Reader reader, Writer writer) {
        def interpreter = createInterpreter(writer)

        BufferedReader br = new BufferedReader(reader)
        String input
        while ((input = br.readLine()) != null && input != 'exit') {
            interpreter.eval(input)
        }

        // TODO: dělat to podle závorek, ne podle newline
        /*
        def builder = new StringBuilder()
        int next
        while ((next = reader.read()) != -1) {
            if (next == ')' as char) {
            }
        }
        */
    }

    private Interpreter createInterpreter(Writer writer) {
        return new Interpreter(LibLoader.createGlobalContext(), new InterpreterListener() {
            @Override
            void onExpressionParsed(Node node) {
                // ignore
            }

            @Override
            void onResult(Object result) {
                writer.append ">> $result\n"
                writer.flush()
            }

            @Override
            void onUnhandledException(LispException e) {
                if (e instanceof ExitException) {
                    System.exit(e.exitCode)
                } else {
                    writer.append ">> ERROR: $e\n"
                    writer.flush()
                }
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
