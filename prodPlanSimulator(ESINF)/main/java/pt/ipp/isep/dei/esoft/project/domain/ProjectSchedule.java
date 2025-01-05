package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Graph.Algorithms;
import pt.ipp.isep.dei.esoft.project.domain.Graph.Pair;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;
import pt.ipp.isep.dei.esoft.project.repository.PETRGraphRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

import java.io.PrintWriter;
import java.util.*;


public class ProjectSchedule {

    private LinkedList<Activity> verticesList;
    private final MapGraph<Activity, Double> projectGraph;
    private PETRGraphRepository mapGraphRepository;
    private final ID graphID;
    private PrintWriter writer;

    private static final String FILE_PATH = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/output/";
    private static final Pair<String, Integer> START_PAIR = new Pair<>("Start", 7777);
    private static final Pair<String, Integer> FINISH_PAIR = new Pair<>("Finish", 7778);
    private static final ID START_ID = new ID(START_PAIR.getSecond(), TypeID.ACTIVITY);
    private static final ID FINISH_ID = new ID(FINISH_PAIR.getSecond(), TypeID.ACTIVITY);
    private static final Activity START_ACTIVITY = new Activity(START_ID, START_PAIR.getFirst());
    private static final Activity FINISH_ACTIVITY = new Activity(FINISH_ID, FINISH_PAIR.getFirst());

    public ProjectSchedule(MapGraph<Activity, Double> graph, ID graphId) {
        this.projectGraph = graph;
        this.verticesList = new LinkedList<>();
        this.mapGraphRepository = getRepositories().getPetrGraphRepository();
        this.graphID = graphId;
    }

    private Repositories getRepositories() {
        return Repositories.getInstance();
    }

    protected void generateVerticesListBFS() {
        verticesList.clear();
        verticesList = Algorithms.getTopologicalOrder(projectGraph);


    }

    public LinkedList<Activity> getVerticesList() {
        return verticesList;
    }

    public void setMapGraphRepository(PETRGraphRepository mapGraphRepository) {
        this.mapGraphRepository = mapGraphRepository;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.writer = printWriter;
    }


    protected void calculateScheduleAnalysis(boolean modify) {
        generateVerticesListBFS();

        for (Activity a : verticesList) {
            if (a.equals(START_ACTIVITY) || a.equals(FINISH_ACTIVITY)) {
                continue;
            }
            double earliestStart;
            if (a.getPredecessors().contains(START_ACTIVITY.getId()) && a.getPredecessors().size() == 1) {
                earliestStart = 0;
            } else {
                earliestStart = getMaxEarliestFinish(a.getPredecessors());
            }
            if (modify)
                a.setEarliestStart(earliestStart);

            double earliestFinish = earliestStart + a.getDuration();
            if (modify)
                a.setEarliestFinish(earliestFinish);

            if (!modify)
                printEsEf(a, earliestStart, earliestFinish);
        }

        ListIterator<Activity> iterator = verticesList.listIterator(verticesList.size());
        while (iterator.hasPrevious()) {
            Activity a = iterator.previous();
            if (a.equals(START_ACTIVITY) || a.equals(FINISH_ACTIVITY)) {
                continue;
            }

            double latestFinish;
            if (a.getSuccessors().contains(FINISH_ID) && a.getSuccessors().size() == 1) {
                latestFinish = a.getEarliestFinish();
            } else {
                latestFinish = getMinLatestStart(a.getSuccessors());
            }

            if (latestFinish == Double.MAX_VALUE) {
                latestFinish = a.getEarliestFinish();
            }
            if(modify)
                a.setLatestFinish(latestFinish);

            double latestStart = latestFinish - a.getDuration();
            if (modify)
                a.setLatestStart(latestStart);

            double slack = latestFinish - a.getEarliestFinish();
            if (modify)
                a.setSlack(slack);

            if(!modify)
                printLsLfSlack(a, latestStart, latestFinish, slack);
        }
    }

    private void printEsEf(Activity activity, double earliestStart, double earliestFinish) {
        System.out.printf("Activity: %s || Previous earliest start: %.2f || New earliest start: %.2f%n", activity.getId(), activity.getEarliestStart(), earliestStart);
        System.out.printf("Activity: %s || Previous earliest finish: %.2f || New earliest finish: %.2f%n", activity.getId(), activity.getEarliestFinish(), earliestFinish);
        System.out.println();
    }

    private void printLsLfSlack(Activity activity, double latestStart, double latestFinish, double slack) {
        System.out.printf("Activity: %s || Previous latest start: %.2f || New latest start: %.2f%n", activity.getId(), activity.getLatestStart(), latestStart);
        System.out.printf("Activity: %s || Previous latest finish: %.2f || New latest finish: %.2f%n", activity.getId(), activity.getLatestFinish(), latestFinish);
        System.out.printf("Activity: %s || Previous slack time: %.2f || New slack time: %.2f%n", activity.getId(), activity.getSlack(), slack);
        System.out.println();
    }


    private double getMaxEarliestFinish(List<ID> predecessors) {
        double max = 0;
        boolean hasValidPredecessor = false;

        for (ID pred : predecessors) {
            Activity ac = mapGraphRepository.getActivityByID(graphID, pred);
            if (ac != null) {
                hasValidPredecessor = true;
                max = Math.max(max, ac.getEarliestFinish());
            }
        }

        return hasValidPredecessor ? max : 0;
    }

    protected double getMinLatestStart(List<ID> successors) {
        double min = Double.MAX_VALUE;
        boolean hasValidSuccessor = false;

        for (ID suc : successors) {
            Activity ac = mapGraphRepository.getActivityByID(graphID, suc);
            if (ac != null) {
                hasValidSuccessor = true;
                min = Math.min(min, ac.getLatestStart());
            }
        }

        return hasValidSuccessor ? min : Double.MAX_VALUE;
    }

    public void sendProjectScheduleToFile(String fileName) {
        calculateScheduleAnalysis(true);
        try {
           writer = new PrintWriter(FILE_PATH + fileName + ".csv");
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
        } catch (Exception e) {
            System.out.println(ANSI_BRIGHT_RED + "Error writing to file " + FILE_PATH + fileName + ANSI_RESET);
        }
    }

}
