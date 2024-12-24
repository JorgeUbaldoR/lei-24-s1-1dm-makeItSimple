package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Graph.Algorithms;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.repository.PETRGraphRepository;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class ProjectSchedule {

    private LinkedList<Activity> verticesList;
    private final MapGraph<Activity, Double> projectGraph;
    private final PETRGraphRepository mapGraphRepository;
    private final ID graphID;
    private static final String FILE_PATH = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/output";

    public ProjectSchedule(MapGraph<Activity, Double> graph, ID graphId, PETRGraphRepository mapGraphRepository) {
        this.projectGraph = graph;
        this.verticesList = new LinkedList<>();
        this.mapGraphRepository = mapGraphRepository;
        this.graphID = graphId;
    }

    private void generateVerticesListBFS() {
        verticesList.clear();
        verticesList = Algorithms.BreadthFirstSearchAll(projectGraph);
    }

    public LinkedList<Activity> getVerticesList() {
        return verticesList;
    }

    private void calculateScheduleAnalysis() {
        generateVerticesListBFS();

        for (Activity a : verticesList) {
            double earliestStart = a.getPredecessors().isEmpty() ? 0 : getMaxEarliestFinish(a.getPredecessors());
            a.setEarliestStart(earliestStart);

            double earliestFinish = earliestStart + a.getDuration();
            a.setEarliestFinish(earliestFinish);

            double latestFinish = a.getPredecessors().isEmpty() ? earliestFinish : getMinLatestStart(a.getPredecessors());
            a.setLatestFinish(latestFinish);

            double latestStart = latestFinish - a.getDuration();
            a.setLatestStart(latestStart);

            a.setSlack(latestFinish - earliestFinish);

        }
    }

    private double getMaxEarliestFinish(List<ID> predecessors) {
        double max = Double.NEGATIVE_INFINITY;
        for (ID pred : predecessors) {
            Activity ac = mapGraphRepository.getActivityByID(graphID, pred);
            if (ac != null) {
                max = Math.max(max, ac.getEarliestFinish());
            }
        }
        return max;
    }

    private double getMinLatestStart(List<ID> predecessors) {
        double min = Double.POSITIVE_INFINITY;
        for (ID pred : predecessors) {
            Activity ac = mapGraphRepository.getActivityByID(graphID, pred);
            if (ac != null) {
                min = Math.min(min, ac.getLatestStart());
            }
        }
        return min;
    }

    public void sendProjectScheduleToFile(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(FILE_PATH + fileName);
            writer.println("act_id,cost,duration,es,ls,ef,lf,prev_act_ids...");
            for (Activity a : verticesList) {
                writer.printf("%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", a.getId(), a.getCost(), a.getDuration(),
                        a.getEarliestStart(), a.getLatestStart(), a.getEarliestFinish(), a.getLatestFinish());
                for (ID pred : a.getPredecessors()) {
                    writer.print("," + pred.toString());
                }
                writer.println();
            }


        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "Error writing to file " + FILE_PATH + fileName + ANSI_RESET);
        }
    }

}
