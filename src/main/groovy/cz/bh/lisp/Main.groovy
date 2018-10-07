package cz.bh.lisp

import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.interpreter.InterpreterListener
import cz.bh.lisp.lib.LibLoader

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class Main {

    static void main(String... args) {
        println "BH Lisp REPL"

        def interpreter = new Interpreter(LibLoader.createGlobalContext(), new InterpreterListener() {
            @Override
            void onResult(Object result) {
                println ">> $result"
            }

            @Override
            void onUnhandledException(LispException e) {
                println ">> ERROR: $e"
            }

            @Override
            void onUnhandledError(Exception e) {
                e.printStackTrace()
            }
        })

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
        String input
        while ((input = br.readLine()) != null && input != 'exit') {
            interpreter.eval(input)
        }
    }

}
