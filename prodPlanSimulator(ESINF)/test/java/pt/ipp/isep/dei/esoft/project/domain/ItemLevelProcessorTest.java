package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ItemLevelProcessorTest {

    private ItemLevelProcessor itemLevelProcessor;


    @BeforeEach
    void setUp() {
        itemLevelProcessor = new ItemLevelProcessor(false, "");
    }


    @Test
    void testAssociateItemsWithTree() {
        TreeMap<Integer, Queue<Map<Item, Float>>> tree = itemLevelProcessor.getTree();


        assertNotNull(tree, "The tree should not be null.");
        assertFalse(tree.isEmpty(), "The tree should not be empty.");

        assertTrue(tree.containsKey(1), "Tree should contain height 1.");
        assertEquals(6, tree.size(), "Tree should have have height 6.");
        assertEquals(1, tree.get(1).size(), "Height 1 should have 1 queue.");

        Map<Item, Float> itemsAtHeight = tree.get(1).peek();
        assertNotNull(itemsAtHeight, "Items at height should not be null.");
        assertEquals(1.0f, itemsAtHeight.values().iterator().next(), "Item quantity should match the test data.");
    }

}