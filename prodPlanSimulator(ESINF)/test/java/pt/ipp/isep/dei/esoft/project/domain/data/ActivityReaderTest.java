package pt.ipp.isep.dei.esoft.project.domain.data;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityReaderTest {

    @Test
    void testValidCSVFile() throws IOException {
        // Create a temporary CSV file
        File tempFile = File.createTempFile("validActivities", ".csv");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("ActivKey,descr,duration,duration-unit,tot-cost,predecessors\n");
            writer.write("1,Activity 1,10,hours,1000,\n");
            writer.write("2,Activity 2,5,hours,500,1\n");
        }

        // Read the CSV file and validate the graph
        MapGraph<Activity, Double> graph = ActivityReader.readCSV(tempFile.getPath(), true);
        assertNotNull(graph);
        assertEquals(5, graph.numVertices()); // Includes Start and Finish nodes
        assertEquals(3, graph.numEdges());

        // Validate the structure
        List<Activity> vertices = graph.vertices();
        assertTrue(vertices.stream().anyMatch(a -> a.getDescription().equals("Start")));
        assertTrue(vertices.stream().anyMatch(a -> a.getDescription().equals("Finish")));
        assertTrue(vertices.stream().anyMatch(a -> a.getDescription().equals("Activity 1")));
        assertTrue(vertices.stream().anyMatch(a -> a.getDescription().equals("Activity 2")));
    }

    @Test
    void testInvalidCSVFileDuplicateID() throws IOException {
        // Create a temporary CSV file with duplicate IDs
        File tempFile = File.createTempFile("duplicateIDActivities", ".csv");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("ActivKey,descr,duration,duration-unit,tot-cost,predecessors\n");
            writer.write("1,Activity 1,10,hours,1000,\n");
            writer.write("1,Activity 2,5,hours,500,\n");
        }

        // Expect an exception when processing the file
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ActivityReader.readCSV(tempFile.getPath(), true);
        });
        assertTrue(exception.getMessage().contains("Duplicate activity ID"));
    }

    @Test
    void testInvalidCSVFileCircularDependency() throws IOException {
        // Create a temporary CSV file with a circular dependency
        File tempFile = File.createTempFile("circularDependency", ".csv");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("ActivKey,descr,duration,duration-unit,tot-cost,predecessors\n");
            writer.write("1,Activity 1,10,hours,1000,2\n");
            writer.write("2,Activity 2,5,hours,500,1\n");
        }

        // Expect an exception when processing the file
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ActivityReader.readCSV(tempFile.getPath(), true);
        });
        assertTrue(exception.getMessage().contains("Circular Dependencies"));
    }

    @Test
    void testInvalidCSVFileMissingPredecessor() throws IOException {
        // Create a temporary CSV file with a missing predecessor
        File tempFile = File.createTempFile("missingPredecessor", ".csv");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("ActivKey,descr,duration,duration-unit,tot-cost,predecessors\n");
            writer.write("1,Activity 1,10,hours,1000,2\n");
        }

        // Expect an exception when processing the file
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ActivityReader.readCSV(tempFile.getPath(), true);
        });
        assertTrue(exception.getMessage().contains("predecessor is"));
    }

    @Test
    void testValidationFunctions() {
        // Test valid string
        assertTrue(ActivityReader.validateString("Test"));
        assertFalse(ActivityReader.validateString(""));
        assertFalse(ActivityReader.validateString(null));

        // Test valid ID, duration, and cost
        assertDoesNotThrow(() -> {
            ActivityReader.validateParametersUnits("1", "10", "100", 1);
        });

        // Test invalid ID
        Exception idException = assertThrows(IllegalArgumentException.class, () -> {
            ActivityReader.validateParametersUnits("A", "10", "100", 1);
        });
        assertTrue(idException.getMessage().contains("Invalid ID"));

        // Test negative duration
        Exception durationException = assertThrows(IllegalArgumentException.class, () -> {
            ActivityReader.validateParametersUnits("1", "-10", "100", 1);
        });
        assertTrue(durationException.getMessage().contains("DURATION"));

        // Test negative cost
        Exception costException = assertThrows(IllegalArgumentException.class, () -> {
            ActivityReader.validateParametersUnits("1", "10", "-100", 1);
        });
        assertTrue(costException.getMessage().contains("COST"));
    }
}
