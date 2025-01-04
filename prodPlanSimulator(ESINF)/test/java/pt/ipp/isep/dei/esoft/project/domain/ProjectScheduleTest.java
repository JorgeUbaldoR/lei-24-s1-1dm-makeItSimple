package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.repository.PETRGraphRepository;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectScheduleTest {

    private MapGraph<Activity, Double> graph;
    private PETRGraphRepository repository;
    private ProjectSchedule projectSchedule;
    private Activity activity1;
    private Activity activity2;
    private Activity activity3;

    @BeforeEach
    public void setUp() {
        graph = new MapGraph<>(true);
        repository = new PETRGraphRepository();

        ID id1 = new ID(1, TypeID.ACTIVITY);
        ID id2 = new ID(2, TypeID.ACTIVITY);
        ID id3 = new ID(3, TypeID.ACTIVITY);

        activity1 = new Activity(id1, "Activity 1", 5.0, "week", 1000.0, "USD", Arrays.asList());
        activity2 = new Activity(id2, "Activity 2", 3.0, "week", 500.0, "USD", Arrays.asList(id1));
        activity3 = new Activity(id3, "Activity 3", 2.0, "week", 300.0, "USD", Arrays.asList(id2));


        graph.addVertex(activity1);
        graph.addVertex(activity2);
        graph.addVertex(activity3);

        projectSchedule = new ProjectSchedule(graph, new ID(999, TypeID.ACTIVITY));
        projectSchedule.setMapGraphRepository(repository);
    }

    @Test
    public void testCalculateScheduleAnalysis() {
        projectSchedule.calculateScheduleAnalysis();

        List<ID> sucList1 = Arrays.asList(activity2.getId()); // Sucessores de activity1
        List<ID> sucList2 = Arrays.asList(activity3.getId()); // Sucessores de activity2


        assertEquals(Double.MAX_VALUE, projectSchedule.getMinLatestStart(sucList1), 0.1);
        assertEquals(Double.MAX_VALUE, projectSchedule.getMinLatestStart(sucList2), 0.1);

        assertEquals(0.0, activity1.getEarliestStart(), 0.1);
        assertEquals(5.0, activity1.getEarliestFinish(), 0.1);

        assertEquals(0.0, activity2.getEarliestStart(), 0.1);
        assertEquals(3.0, activity2.getEarliestFinish(), 0.1);

        assertEquals(0.0, activity3.getEarliestStart(), 0.1);
        assertEquals(2.0, activity3.getEarliestFinish(), 0.1);

        assertEquals(5.0, activity1.getLatestFinish(), 0.1);
        assertEquals(3.0, activity2.getLatestFinish(), 0.1);
        assertEquals(2.0, activity3.getLatestFinish(), 0.1);

        assertEquals(0.0, activity1.getSlack(), 0.1);
        assertEquals(0.0, activity2.getSlack(), 0.1);
        assertEquals(0.0, activity3.getSlack(), 0.1);
    }

    @Test
    public void testSendProjectScheduleToFile() throws FileNotFoundException {
        String fileName = "test_schedule_output";

        PrintWriter writer = new PrintWriter(fileName + ".csv");

        projectSchedule.setPrintWriter(writer);

        projectSchedule.sendProjectScheduleToFile(fileName);

        writer.close();
        assertTrue(new File(fileName + ".csv").exists());
    }

    @Test
    public void testGenerateVerticesListBFS() {
        projectSchedule.generateVerticesListBFS();

        assertNotNull(projectSchedule.getVerticesList());
        assertTrue(projectSchedule.getVerticesList().size() > 0);
    }
}