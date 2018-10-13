package cz.bh.lisp

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 *
 * @version 2018-10-13
 * @author Patrik Harag
 */
@RunWith(Parameterized.class)
class ParametrizedTest {

    private static File DIR = new File("src/test/resources")

    @Parameterized.Parameters(name = "{0}")
    static Collection<Object[]> data() {
        def tests = [
                "general",
                "math",
                "conditionals",
                "collections",
                "flow",
                "java-interop",
        ]

        tests.collect {
            [new File(DIR, it + ".bhl"), new File(DIR, it + ".txt")].toArray()
        }
    }


    private final File source
    private final File reference

    ParametrizedTest(File source, File reference) {
        this.source = source
        this.reference = reference
    }

    @Test
    void compare() {
        def buffer = new ByteArrayOutputStream()
        generate(source, new PrintStream(buffer, true, "utf-8"))

        String expected = reference.getText("utf-8").replace('\r', '')
        String actual = new String(buffer.toByteArray(), "utf-8").replace('\r', '')
        Assert.assertEquals(expected, actual)
    }

    static void generate(File source, PrintStream out) {
        // note: we don't want buffered stream
        def inputStream = new FileInputStream(source)
        try {
            def reader = new InputStreamReader(inputStream, "utf-8")
            def repl = new FileRepl()
            repl.start(reader, out)
        } finally {
            inputStream.close()
        }
    }

    static void main(String... args) {
        data().each {
            def source = it[0] as File
            def reference = it[1] as File
            reference.withOutputStream { out ->
                generate(source, new PrintStream(out, true, "utf-8"))
            }
        }
    }
}
