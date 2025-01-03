package pt.ipp.isep.dei.esoft.project.domain.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class WriterGraphTest {

    private static MapGraph<Activity, Double> mockGraphDirected;
    private static MapGraph<Activity, Double> mockGraphUndirected;
    private static  String filePath = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/test.csv";

    @BeforeAll
    static void setup() throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("""
                        ActivKey,descr,duration,duration-unit,tot-cost,predecessors
                        A1,Preparar materiais,5,dias,1000,
                        AS2,Montar estrutura,10,dias,2000,fbb1
                        ASS3,Instalar sistemas,7,dias,1500,bfbf2
                        SDSF4,Testar o sistema,3,dias,500,bffb3
                        FDF5,Documentar o projeto,4,dias,300,fbbf2
                    """);
        }
    }


    @Test
    void testWriteGraphToUmlFileDirected() throws IOException {
        mockGraphDirected = ActivityReader.readCSV(filePath,true);
        String nameGraph = "testGraph";
        WriterGraph.writePETRGraph(nameGraph, mockGraphDirected);

        // Check File Content
        File file = new File("prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/output/MapGraph_"+nameGraph+"_Directed.puml");
        assertTrue(file.exists(), "Graph UML file should be created.");

        String content = Files.readString(file.toPath());
        assertTrue(content.contains("@startuml"), "File should contain PlantUML start marker.");
        assertTrue(content.contains("A-1"), "Operation 1 should be included in the BOO file.");
        assertTrue(content.contains("[label = \"5.0 (dias)    \"]"), "Item A should be included in the BOO file.");

        file.delete();
    }

    @Test
    void testWriteGraphToUmlFileUndirected() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> ActivityReader.readCSV(filePath,false));
    }


}