package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.CriticalPath;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.data.ActivityReader;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CriticalPathTest {

    private MapGraph<Activity, Double> graph;
    private CriticalPath criticalPath;

    @BeforeEach
    void setUp() {
        criticalPath = new CriticalPath();
        graph = ActivityReader.readCSV("prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/activity_small_project.csv", true);
    }

    @AfterEach
    void tearDown() {
        graph = null;
        criticalPath = null;
    }

    @Test
    void calculateCriticalPath() {
        Map<String, Object> result = criticalPath.calculateCriticalPath(graph);
        List<Activity> criticalPathResult = (List<Activity>) result.get("criticalPath");
        double totalDuration = (double) result.get("totalDuration");

        assertNotNull(criticalPathResult);
        assertFalse(criticalPathResult.isEmpty());
        assertEquals(16.0, totalDuration, 0.01); // Total esperado: 16 semanas

        // Verificar atividades específicas no caminho crítico
        List<String> expectedPath = List.of("A-2", "A-3", "A-6", "A-7", "A-11", "A-12");
        List<String> actualPath = new ArrayList<>();
        for (Activity activity : criticalPathResult) {
            actualPath.add(activity.getId().toString());
        }
        assertEquals(expectedPath, actualPath);
    }

    @Test
    void testEmptyGraph() {
        MapGraph<Activity, Double> emptyGraph = new MapGraph<>(true);
        Map<String, Object> result = criticalPath.calculateCriticalPath(emptyGraph);

        assertEquals(Collections.emptyList(), result.get("criticalPath"));
        assertEquals(0.0, result.get("totalDuration"));
    }
}
