package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Bottlenecks;
import pt.ipp.isep.dei.esoft.project.domain.Graph.*;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.data.ActivityReader;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BottlenecksTest {

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
    void identifyBottlenecks() {
        // Calcular o caminho crítico
        Map<String, Object> result = criticalPath.calculateCriticalPath(graph);
        List<Activity> criticalPathResult = (List<Activity>) result.get("criticalPath");

        // Testar gargalos
        Map<String, Object> bottlenecks = Bottlenecks.identifyBottlenecks(graph, criticalPathResult);

        Map<Activity, Integer> topBottlenecks = (Map<Activity, Integer>) bottlenecks.get("topBottlenecks");
        List<Activity> allBottlenecks = (List<Activity>) bottlenecks.get("allBottlenecks");

        assertNotNull(topBottlenecks);
        assertNotNull(allBottlenecks);
        assertFalse(topBottlenecks.isEmpty());
        assertFalse(allBottlenecks.isEmpty());

        assertTrue(topBottlenecks.size() <= 5);
        assertTrue(allBottlenecks.size() >= topBottlenecks.size());

        // Validar se estão ordenados corretamente por grau de dependência
        Activity prevActivity = null;
        for (Activity activity : allBottlenecks) {
            if (prevActivity != null) {
                int prevCount = prevActivity.getPredecessors().size();
                int currentCount = activity.getPredecessors().size();

                // Verifica se o grau de dependência está em ordem decrescente
                assertTrue(prevCount >= currentCount, "As atividades não estão ordenadas corretamente!");
            }
            prevActivity = activity;
        }
    }


    @Test
    void testEmptyGraph() {
        MapGraph<Activity, Double> emptyGraph = new MapGraph<>(true);
        Map<String, Object> bottlenecks = Bottlenecks.identifyBottlenecks(emptyGraph, Collections.emptyList());

        assertEquals(Collections.emptyList(), bottlenecks.get("topBottlenecks"));
        assertEquals(Collections.emptyList(), bottlenecks.get("allBottlenecks"));
    }

    @Test
    void testEmptyCriticalPath() {
        Map<String, Object> bottlenecks = Bottlenecks.identifyBottlenecks(graph, Collections.emptyList());

        assertEquals(Collections.emptyList(), bottlenecks.get("topBottlenecks"));
        assertEquals(Collections.emptyList(), bottlenecks.get("allBottlenecks"));
    }
}
