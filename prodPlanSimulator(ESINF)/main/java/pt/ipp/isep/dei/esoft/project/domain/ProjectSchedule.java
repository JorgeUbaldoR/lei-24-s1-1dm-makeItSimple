package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Graph.Algorithms;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.repository.PETRGraphRepository;

import java.util.*;


public class ProjectSchedule {

    private LinkedList<Activity> verticesList;
    private final MapGraph<Activity, Double> projectGraph;
    private final PETRGraphRepository mapGraphRepository;
    private final ID graphID;


    public ProjectSchedule(MapGraph<Activity, Double> graph, ID graphId) {
        this.projectGraph = graph;
        this.verticesList = new LinkedList<>();
        this.mapGraphRepository = new PETRGraphRepository();
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

}
