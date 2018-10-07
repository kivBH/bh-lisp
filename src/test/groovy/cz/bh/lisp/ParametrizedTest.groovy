package cz.bh.lisp

import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
@RunWith(Parameterized.class)
class ParametrizedTest {

    private static File DIR = new File("src/test/resources")

    @Parameterized.Parameters(name = "{0}")
    static Collection<Object[]> data() {
        def tests = [
                "literals"
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
        def writer = new StringWriter()
        generate(writer)

        Assert.assertEquals(reference.getText("utf-8"), writer.toString())
    }

    @Test
    @Ignore
    void generate() {
        reference.withWriter("utf-8") { writer ->
            generate(writer)
        }
    }

    void generate(Writer writer) {
        // note: we don't want buffered stream
        def inputStream = new FileInputStream(source)
        try {
            def reader = new InputStreamReader(inputStream, "utf-8")
            def repl = new FileRepl()
            repl.start(reader, writer)
        } finally {
            inputStream.close()
        }
    }
}
