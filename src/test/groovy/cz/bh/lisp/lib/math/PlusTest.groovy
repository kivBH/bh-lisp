package cz.bh.lisp.lib.math

import org.junit.Test

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class PlusTest {

    def plus = new Plus()

    @Test
    void testInt() {
        def result = plus.run([1, (long) 2, (int) 3, -1])
        assert result == 5
        assert result instanceof BigInteger
    }

    @Test
    void testFloat() {
        def result = plus.run([1, (long) 2, (int) 3, 3.14])
        assert result == 9.14
        assert result instanceof BigDecimal
    }
}
