package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {

    private Activity activity;
    private ID id;
    private List<ID> predecessors;
    private List<ID> successors;

    @BeforeEach
    public void setUp() {
        id = new ID(1, TypeID.ACTIVITY);
        predecessors = new ArrayList<>(Arrays.asList(new ID(0, TypeID.ACTIVITY))); // ID da atividade predecessora
        successors = new ArrayList<>(Arrays.asList(new ID(2, TypeID.ACTIVITY)));  // ID da atividade sucessora

        activity = new Activity(id, "Description", 5.0, "week", 1000.0, "USD", predecessors);
        activity.setSuccessors(successors);
    }

    @Test
    public void testGetters() {
        assertEquals("A-1", activity.getId().toString());
        assertEquals("Description", activity.getDescription());
        assertEquals(5.0, activity.getDuration());
        assertEquals("week", activity.getDurationUnit());
        assertEquals(1000.0, activity.getCost());
        assertEquals("USD", activity.getCostUnit());
        assertEquals(predecessors, activity.getPredecessors());
        assertEquals(successors, activity.getSuccessors());
    }

    @Test
    public void testSetters() {
        activity.setDescription("New Description");
        activity.setDuration(10.0);
        activity.setCost(1500.0);
        activity.setDurationUnit("month");
        activity.setCostUnit("EUR");

        assertEquals("New Description", activity.getDescription());
        assertEquals(10.0, activity.getDuration());
        assertEquals(1500.0, activity.getCost());
        assertEquals("month", activity.getDurationUnit());
        assertEquals("EUR", activity.getCostUnit());
    }

    @Test
    public void testEqualsAndHashCode() {
        Activity activity2 = new Activity(new ID(1, TypeID.ACTIVITY), "Description", 5.0, "week", 1000.0, "USD", predecessors);

        assertEquals(activity, activity2);

        assertEquals(activity.hashCode(), activity2.hashCode());

        Activity activity3 = new Activity(new ID(2, TypeID.ACTIVITY), "Different Activity", 3.0, "day", 500.0, "EUR", predecessors);
        assertNotEquals(activity, activity3);
    }

    @Test
    public void testPredecessorAndSuccessorManipulation() {
        List<ID> newPredecessors = Arrays.asList(new ID(0, TypeID.ACTIVITY), new ID(3, TypeID.ACTIVITY));
        List<ID> newSuccessors = Arrays.asList(new ID(4, TypeID.ACTIVITY));

        activity.setPredecessors(newPredecessors);
        activity.setSuccessors(newSuccessors);

        assertEquals(newPredecessors, activity.getPredecessors());
        assertEquals(newSuccessors, activity.getSuccessors());
    }

    @Test
    public void testDefaultConstructor() {
        Activity defaultActivity = new Activity(new ID(3, TypeID.ACTIVITY), "Default Activity");

        assertEquals(0.0, defaultActivity.getDuration());
        assertEquals("No duration unit", defaultActivity.getDurationUnit());
        assertEquals(0.0, defaultActivity.getCost());
        assertEquals("No cost unit", defaultActivity.getCostUnit());
    }

    @Test
    public void testEarliestAndLatestStartFinish() {
        activity.setEarliestStart(1.0);
        activity.setEarliestFinish(6.0);
        activity.setLatestStart(2.0);
        activity.setLatestFinish(7.0);
        activity.setSlack(1.0);

        assertEquals(1.0, activity.getEarliestStart());
        assertEquals(6.0, activity.getEarliestFinish());
        assertEquals(2.0, activity.getLatestStart());
        assertEquals(7.0, activity.getLatestFinish());
        assertEquals(1.0, activity.getSlack());
    }
}