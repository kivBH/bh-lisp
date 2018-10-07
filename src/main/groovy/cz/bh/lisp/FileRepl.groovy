package cz.bh.lisp

import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.interpreter.InterpreterListener
import cz.bh.lisp.lib.ExitException
import cz.bh.lisp.lib.LibLoader

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
class FileRepl extends Repl {

    boolean lastIsNewLine = true
    boolean newLineNeeded = false

    synchronized void start(Reader reader, Writer writer) {
        def interpreter = createInterpreter(writer)
        interpreter.eval(wrapReader(reader, writer))
    }

    private Reader wrapReader(Reader reader, Writer writer) {
        // writes the loaded characters to the output
        new Reader() {
            @Override
            int read(char[] cbuf, int off, int len) throws IOException {
                def result = reader.read(cbuf, off, len)

                lastIsNewLine = cbuf[off + len - 1] == ('\n' as char)
                if (!lastIsNewLine && newLineNeeded) {
                    newLineNeeded = false
                    writer.write("\n")
                }
                writer.write(cbuf, off, len)
                writer.flush()

                return result
            }

            @Override
            void close() throws IOException {
                reader.close()
            }
        }
    }

    private Interpreter createInterpreter(Writer writer) {
        return new Interpreter(LibLoader.createGlobalContext(), new InterpreterListener() {
            @Override
            void onResult(Object result) {
                printNewLineIfNeeded()
                writer.print ">> $result"
                writer.flush()
            }

            @Override
            void onUnhandledException(LispException e) {
                if (e instanceof ExitException) {
                    System.exit(e.exitCode)
                } else {
                    printNewLineIfNeeded()
                    writer.print ">> ERROR: $e"
                    writer.flush()
                }
            }

            @Override
            void onUnhandledError(Exception e) {
                printNewLineIfNeeded()
                def printWriter = new PrintWriter(writer)
                e.printStackTrace(printWriter)
                printWriter.flush()
                writer.flush()
            }

            private void printNewLineIfNeeded() {
                if (lastIsNewLine) {
                    newLineNeeded = true
                } else {
                    newLineNeeded = false
                    writer.print "\n"
                }
            }
        })
    }

}
