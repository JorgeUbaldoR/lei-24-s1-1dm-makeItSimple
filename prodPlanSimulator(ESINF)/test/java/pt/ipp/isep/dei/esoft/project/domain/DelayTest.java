package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class DelayTest {

    private Delay delay;
    private MapGraph<Activity, Double> createdMap;
    private Activity activity1;
    private Activity activity2;

    @BeforeEach
    void setUp() {
        delay = new Delay();
        createdMap = new MapGraph<>(true);

        // Initialize some activities
        ID id1 = new ID(1001, TypeID.ACTIVITY);
        ID id2 = new ID(1002, TypeID.ACTIVITY);
        ID startId = new ID(7777, TypeID.ACTIVITY);
        ID finishId = new ID(7778, TypeID.ACTIVITY);

        activity1 = new Activity(id1, "Activity 1", 5.0, "hours", 100.0, "USD", new ArrayList<>());
        activity2 = new Activity(id2, "Activity 2", 10.0, "hours", 200.0, "USD", new ArrayList<>());
        Activity start = new Activity(startId, "Start Placeholder", 0.0, "hours", 0.0, "USD", new ArrayList<>());
        Activity finish = new Activity(finishId, "Finish Placeholder", 0.0, "hours", 0.0, "USD", new ArrayList<>());

        // Add activities to the map
        createdMap.addVertex(activity1);
        createdMap.addVertex(activity2);
        createdMap.addVertex(start);
        createdMap.addVertex(finish);

        // Add edges
        createdMap.addEdge(start, activity1, 1.0);
        createdMap.addEdge(activity1, activity2, 2.0);
        createdMap.addEdge(activity2, finish, 1.0);
    }

    @Test
    void testUpdateActivityDuration() {
        // Positive duration increment
        delay.updateActivityDuration(activity1, 2.0);
        assertEquals(7.0, activity1.getDuration(), "Duration should increase by 2.0");

        // Negative duration increment
        delay.updateActivityDuration(activity1, -3.0);
        assertEquals(4.0, activity1.getDuration(), "Duration should decrease by 3.0");

        // Negative duration resulting in invalid state
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            delay.updateActivityDuration(activity1, -10.0);
        });
        assertEquals("Duration cannot be negative", exception.getMessage());
    }

    @Test
    void testRemoveActivities() {
        // Remove start and finish placeholders
        MapGraph<Activity, Double> updatedMap = delay.removeActivities(createdMap);

        // Ensure the placeholders are removed
        List<Activity> vertices = updatedMap.vertices();
        assertEquals(2, vertices.size(), "The updated map should only contain 2 activities");
        assertFalse(vertices.contains(new Activity(new ID(7777, TypeID.ACTIVITY), "Start Placeholder", 0.0, "hours", 0.0, "USD", new ArrayList<>())), "Start placeholder should be removed");
        assertFalse(vertices.contains(new Activity(new ID(7778, TypeID.ACTIVITY), "Finish Placeholder", 0.0, "hours", 0.0, "USD", new ArrayList<>())), "Finish placeholder should be removed");

        // Ensure existing activities are still present
        assertTrue(vertices.contains(activity1), "Activity 1 should remain in the map");
        assertTrue(vertices.contains(activity2), "Activity 2 should remain in the map");
    }
}
