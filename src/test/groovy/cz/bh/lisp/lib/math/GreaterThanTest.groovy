package cz.bh.lisp.lib.math

import org.junit.Test

/**
 *
 * @version 2018-10-06
 * @author Patrik Harag
 */
class GreaterThanTest {

    def greaterThan = new GreaterThan()

    @Test(expected = IllegalArgumentException)
    void testEmpty() {
        greaterThan.run([])
    }

    @Test
    void testUnary() {
        assert greaterThan.run([1])
    }

    @Test
    void test1() {
        assert greaterThan.run([5, 1])
    }

    @Test
    void test2() {
        assert greaterThan.run([5, 4, 3, 2, 1, -1])
    }

    @Test
    void test3() {
        assert greaterThan.run([5, 3.15])
    }
    @Test
    void test4() {
        assert !greaterThan.run([1, 50])
    }

}
