/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuoGame;

import JPuyo.Block;
import JPuyo.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author eugeniolr
 */
public class BlockDuoTest {
    
    public BlockDuoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getDegrees method, of class BlockDuo.
     */
    @Test
    public void testGetDegrees() {
        System.out.println("getDegrees");
        BlockDuo instance = null;
        int expResult = 0;
        int result = instance.getDegrees();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDegrees method, of class BlockDuo.
     */
    @Test
    public void testSetDegrees() {
        System.out.println("setDegrees");
        int degrees = 0;
        BlockDuo instance = null;
        instance.setDegrees(degrees);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPivot method, of class BlockDuo.
     */
    @Test
    public void testGetPivot() {
        System.out.println("getPivot");
        BlockDuo instance = null;
        Block expResult = null;
        Block result = instance.getPivot();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExtension method, of class BlockDuo.
     */
    @Test
    public void testGetExtension() {
        System.out.println("getExtension");
        BlockDuo instance = null;
        Block expResult = null;
        Block result = instance.getExtension();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBoard method, of class BlockDuo.
     */
    @Test
    public void testSetBoard() {
        System.out.println("setBoard");
        Board board = null;
        BlockDuo instance = null;
        instance.setBoard(board);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of right method, of class BlockDuo.
     */
    @Test
    public void testRight() {
        System.out.println("right");
        BlockDuo instance = null;
        instance.right();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of left method, of class BlockDuo.
     */
    @Test
    public void testLeft() {
        System.out.println("left");
        BlockDuo instance = null;
        instance.left();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rotateR method, of class BlockDuo.
     */
    @Test
    public void testRotateR() {
        Board board = new Board(10,10);
        BlockDuo bd = board.spawnBlockDuo(5);
        for(int i = 0; i < 8; i++){
            bd.rotateR();
            board.drawTerminal();
        }
    }

    /**
     * Test of rotateL method, of class BlockDuo.
     */
    @Test
    public void testRotateL() {
        System.out.println("rotateL");
        BlockDuo instance = null;
        instance.rotateL();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fall method, of class BlockDuo.
     */
    @Test
    public void testFall() {
        System.out.println("fall");
        BlockDuo instance = null;
        instance.fall();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drop method, of class BlockDuo.
     */
    @Test
    public void testDrop() {
        System.out.println("drop");
        BlockDuo instance = null;
        instance.drop();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class BlockDuo.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        BlockDuo instance = null;
        instance.update();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
