/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class GraphPoetTest {

    @Test
    public void testAssertionsEnabled() {
        boolean assertionsEnabled = false;
        try {
            assert false; // Intentional failure
        } catch (AssertionError e) {
            assertionsEnabled = true;
        }
        assertTrue("Assertions should be enabled", assertionsEnabled);
    }

    @Test
    public void testBasicPoemGeneration() throws IOException {
        File corpus = new File("src\\poet\\basic-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "The quick brown fox";
        String expected = "The quick brown jumps fox";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testNoBridgeWords() throws IOException {
        File corpus = new File("src\\poet\\basic-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "Hello world";
        String expected = "Hello world";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testEmptyInput() throws IOException {
        File corpus = new File("src\\poet\\basic-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "";
        String expected = "";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testCaseInsensitivity() throws IOException {
        File corpus = new File("src\\poet\\case-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "Hello world";
        String expected = "Hello beautiful world";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testSpecialCharacters() throws IOException {
        File corpus = new File("src\\poet\\special-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "Hello world!";
        String expected = "Hello, cruel world!";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testBridgeWordsInMiddle() throws IOException {
        File corpus = new File("src\\poet\\bridge-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "Start middle end";
        String expected = "Start in the middle of end";
        assertEquals(expected, poet.poem(input));
    }
}
