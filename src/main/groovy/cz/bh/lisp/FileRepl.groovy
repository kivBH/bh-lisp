package cz.bh.lisp

import cz.bh.lisp.interpreter.Interpreter
import cz.bh.lisp.interpreter.InterpreterListener
import cz.bh.lisp.lib.ExitException
import cz.bh.lisp.lib.LibLoader
import cz.bh.lisp.parser.exceptions.LexerException
import cz.bh.lisp.parser.exceptions.ParserException
import cz.bh.lisp.parser.sexp.Node

/**
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
class FileRepl {

    private boolean lastWasNewLine = true
    private boolean newLineNeeded = false

    synchronized void start(Reader reader, PrintStream out) {
        def interpreter = createInterpreter(out)
        interpreter.eval(wrapReader(reader, out))
    }

    private Reader wrapReader(Reader reader, PrintStream out) {
        // writes the loaded characters to the output
        new Reader() {
            @Override
            int read(char[] cbuf, int off, int len) throws IOException {
                def result = reader.read(cbuf, off, len)

                String next = new String(cbuf, off, len)
                lastWasNewLine = next.endsWith('\n')
                if (!lastWasNewLine && newLineNeeded) {
                    newLineNeeded = false
                    out.print('\n')
                }
                out.print(next)
                out.flush()

                return result
            }

            @Override
            void close() throws IOException {
                reader.close()
            }
        }
    }

    private Interpreter createInterpreter(PrintStream writer) {
        def interpreter = new Interpreter(LibLoader.createGlobalContext(), new InterpreterListener() {
            @Override
            void onExpressionParsed(Node node) {
                printNewLineIfNeeded()
            }

            @Override
            void onResult(Object result) {
                writer.append ">> $result"
                writer.flush()
            }

            @Override
            void onUnhandledException(LispException e) {
                if (e instanceof ExitException) {
                    System.exit(e.exitCode)
                } else {
                    if (e instanceof ParserException || e instanceof LexerException) {  // JB: pokud chyba, neni volano onExpressionParsed
                        printNewLineIfNeeded()
                    }
                    writer.append ">> ERROR: $e"
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

            private void printNewLineIfNeeded() {
                if (lastWasNewLine) {
                    newLineNeeded = true
                } else {
                    newLineNeeded = false
                    writer.append "\n"
                }
            }
        })
        interpreter.stdOut = writer
        return interpreter
    }

}
