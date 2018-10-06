package cz.bh.lisp.lib

import org.junit.Test

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class LibLoaderTest {

    @Test
    void testSPI() {
        def context = LibLoader.createGlobalContext()
        assert context.getValue("true") == true
        assert context.getValue("false") == false
    }

}
