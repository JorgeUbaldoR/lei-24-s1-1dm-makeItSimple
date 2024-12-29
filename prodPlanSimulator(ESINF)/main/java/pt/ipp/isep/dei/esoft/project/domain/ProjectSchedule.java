package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Graph.Algorithms;
import pt.ipp.isep.dei.esoft.project.domain.Graph.Pair;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.repository.PETRGraphRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class ProjectSchedule {

    private LinkedList<Activity> verticesList;
    private final MapGraph<Activity, Double> projectGraph;
    private final PETRGraphRepository mapGraphRepository;
    private final ID graphID;

    private static final String FILE_PATH = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/output/";
    private static final Pair<String, Integer> START_PAIR = new Pair<>("START", 7777);
    private static final Pair<String, Integer> FINISH_PAIR = new Pair<>("FINISH", 7778);

    public ProjectSchedule(MapGraph<Activity, Double> graph, ID graphId) {
        this.projectGraph = graph;
        this.verticesList = new LinkedList<>();
        this.mapGraphRepository = getRepositories().getPetrGraphRepository();
        this.graphID = graphId;
    }

    private Repositories getRepositories() {
        return Repositories.getInstance();
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
        }

        ListIterator<Activity> iterator = verticesList.listIterator(verticesList.size());
        while (iterator.hasPrevious()) {
            Activity a = iterator.previous();

            double latestFinish = a.getSuccessors().isEmpty() ? a.getEarliestFinish() : getMinLatestStart(a.getSuccessors());
            a.setLatestFinish(latestFinish);

            double latestStart = latestFinish - a.getDuration();
            a.setLatestStart(latestStart);

            double slack = latestFinish - a.getEarliestFinish();
            a.setSlack(slack);
        }
    }

    private double getMaxEarliestFinish(List<ID> predecessors) {
        double max = Double.MIN_VALUE;
        for (ID pred : predecessors) {
            Activity ac = mapGraphRepository.getActivityByID(graphID, pred);
            if (ac != null) {
                max = Math.max(max, ac.getEarliestFinish());
            }
        }
        return max;
    }

    private double getMinLatestStart(List<ID> predecessors) {
        double min = Double.MAX_VALUE;
        for (ID pred : predecessors) {
            Activity ac = mapGraphRepository.getActivityByID(graphID, pred);
            if (ac != null) {
                min = Math.min(min, ac.getLatestStart());
            }
        }
        return min;
    }

    public void sendProjectScheduleToFile(String fileName) {
        calculateScheduleAnalysis();
        try {
            PrintWriter writer = new PrintWriter(FILE_PATH + fileName + ".csv");
            writer.println("act_id;cost;duration;es;ls;ef;lf;prev_act_ids...");
            for (Activity a : verticesList) {
                if (a.getId().getSerial() != START_PAIR.getSecond() && a.getId().getSerial() != FINISH_PAIR.getSecond()) {
                    writer.printf("%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f", a.getId(), a.getCost(), a.getDuration(),
                            a.getEarliestStart(), a.getLatestStart(), a.getEarliestFinish(), a.getLatestFinish());
                    for (ID pred : a.getPredecessors()) {
                        if (pred.getSerial() != START_PAIR.getSecond()) {
                            writer.print(";" + pred);
                        } else {
                            writer.print(";" + START_PAIR.getFirst());
                        }
                    }
                    writer.println();
                } else if (a.getId().getSerial() == FINISH_PAIR.getSecond()) {
                    writer.println(FINISH_PAIR.getFirst());
                } else {
                    writer.println(START_PAIR.getFirst());
                }

            }

            writer.close();
        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "Error writing to file " + FILE_PATH + fileName + ANSI_RESET);
        }
    }

}
