package dbm;

import global.LibLoader;
import lib.DBMLib;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBMTest {
    private final int inf = 2147483646;

    @BeforeClass
    public static void setUpBeforeClass() {
        LibLoader.load();
    }

    @Test
    public void testDbmValid1() {
        assertTrue(DBMLib.dbm_isValid(new int[]{1, 1, inf, 1}, 2));
    }

    @Test
    public void testDbmValid2() {
        assertTrue(DBMLib.dbm_isValid(new int[]{1, 1, 1, 1}, 2));
    }

    @Test
    public void testDbmValid3() {
        assertTrue(DBMLib.dbm_isValid(new int[]{1, -3, 11, 1}, 2));
    }

    @Test
    public void testDbmNotValid1() {
        assertFalse(DBMLib.dbm_isValid(new int[]{0, 0, 0, 0}, 2));
    }

    @Test
    public void testDbmNotValid2() {
        assertFalse(DBMLib.dbm_isValid(new int[]{-1, 0, 0, 0}, 2));
    }

    @Test
    public void testRaw2Bound1(){
        assertEquals(0, DBMLib.raw2bound(1));
    }

    @Test
    public void testRaw2Bound2(){
        assertEquals(1073741823, DBMLib.raw2bound(inf));
    }

    @Test
    public void testBound2Raw1(){
        assertEquals(1, DBMLib.boundbool2raw(0, false));
    }

    @Test
    public void testBound2Raw2(){
        assertEquals(2147483647, DBMLib.boundbool2raw(1073741823, false));
    }

    @Test
    public void testDbmInit1() {
        assertArrayEquals(new int[]{1, 1, inf, 1}, DBMLib.dbm_init(new int[]{0, 0, 0, 0}, 2));
    }

    @Test
    public void testDbmInit2() {
        assertArrayEquals(new int[]{1, 1, 1, inf, 1, inf, inf, inf, 1},
                DBMLib.dbm_init(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, 3)
        );
    }

    @Test
    public void testDbmConstrain1() {
        assertArrayEquals(new int[]{1, 1, 11, 1},
                DBMLib.dbm_constrain1(new int[]{1, 1, inf, 1}, 2, 1, 0, 5)
        );
    }

    @Test
    public void testDbmConstrain2() {
        assertArrayEquals(new int[]{1, -3, 11, 1},
                DBMLib.dbm_constrain1(new int[]{1, 1, 11, 1}, 2, 0, 1, -2)
        );
    }

    @Test
    public void testDbmReset1() {
        assertArrayEquals(new int[]{1, 1, 1, 1},
                DBMLib.dbm_updateValue(new int[]{1, -3, 11, 1}, 2, 1, 0)
        );
    }

    @Test
    public void testDbmReset2() {
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 5, 5, 1},
                DBMLib.dbm_updateValue(new int[]{1, 1, 1, 7, 1, 7, 5, 5, 1}, 3, 1, 0)
        );
    }

    @Test
    public void testDbmFuture1() {
        assertArrayEquals(new int[]{1, 1, inf, 1}, DBMLib.dbm_up(new int[]{1, 1, 1, 1}, 2));
    }

    @Test
    public void testDbmFuture2() {
        assertArrayEquals(new int[]{1, -3, inf, 1}, DBMLib.dbm_up(new int[]{1, -3, 11, 1}, 2));
    }

    @Test
    public void testDbmIntersects1() {
        assertTrue(DBMLib.dbm_intersection(new int[]{1, 1, 11, 1}, new int[]{1, 1, inf, 1}, 2));
    }

    @Test
    public void testDbmIntersects2() {
        assertTrue(DBMLib.dbm_intersection(
                new int[]{1, -9, 1, 1, inf, 1, inf, inf, inf, inf, 1, inf, inf, inf, inf, 1},
                new int[]{1, 1, 1, 1, 13, 1, 13, 13, inf, inf, 1, inf, inf, inf, inf, 1}, 4)
        );
    }

    @Test
    public void testDbmIntersects3() {
        assertTrue(DBMLib.dbm_intersection(
                new int[]{1, 1, -29, 1, inf, 1, inf, inf, inf, inf, 1, inf, inf, inf, inf, 1},
                new int[]{1, 1, 1, 1, 13, 1, 13, 13, inf, inf, 1, inf, inf, inf, inf, 1}, 4)
        );
    }

    @Test
    public void testDbmNotIntersects1() {
        assertFalse(DBMLib.dbm_intersection(new int[]{1, 1, 11, 1}, new int[]{1, -15, inf, 1}, 2));
    }

    @Test
    public void testDbmNotIntersects2() {
        assertFalse(DBMLib.dbm_intersection(
                new int[]{1, 1, 1, 1, 11, 1, 11, 11, inf, inf, 1, inf, inf, inf, inf, 1},
                new int[]{1, -15, 1, 1, inf, 1, inf, inf, inf, inf, 1, inf, inf, inf, inf, 1}, 4)
        );
    }

    @Test
    public void testDbmFreeAllDown1() {
        assertArrayEquals(new int[]{1, 1, 11, 1}, DBMLib.dbm_freeAllDown(new int[]{1, -3, 11, 1}, 2));
    }

    @Test
    public void testDbmFreeAllDown2() {
        assertArrayEquals(new int[]{1, 1, 11, 1}, DBMLib.dbm_freeAllDown(new int[]{1, 1, 11, 1}, 2));
    }

    @Test
    public void testDbmFreeAllDown3() {
        assertArrayEquals(new int[]{1, 1, 1, 1, 15, 1, 15, 15, 23, 23, 1, 23, 115, 115, 115, 1},
                DBMLib.dbm_freeAllDown(new int[]{1, -9, -3, -27, 15, 1, 11, -13, 23, 13, 1, -5, 115, 105, 111, 1}, 4)
        );
    }
}
