package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Graph.Edge;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;

import java.util.*;

public class GraphOperations {

    public List<Activity> updateActivityDurations(MapGraph<Activity, Double> graph, Map<ID, Double> delays) {
        List<Activity> updatedActivities = new ArrayList<>();
        for (Activity activity : graph.vertices()) {
            if (delays.containsKey(activity.getId())) {
                double newDuration = activity.getDuration() + delays.get(activity.getId());
                activity.setDuration(newDuration);
                updatedActivities.add(activity);
                System.out.println("Updated Activity: " + activity + " New Duration: " + newDuration);
            }
        }
        return updatedActivities;
    }


    public void forwardPass(MapGraph<Activity, Double> graph) {
        for (Activity activity : graph.vertices()) {
            double maxFinish = 0;

            for (Edge<Activity, Double> edge : graph.incomingEdges(activity)) {
                Activity predecessor = edge.getVOrig();
                maxFinish = Math.max(maxFinish, predecessor.getEarliestFinish());
            }

            activity.setEarliestStart(maxFinish);
            activity.setEarliestFinish(maxFinish + activity.getDuration());
        }
    }


    private void backwardPass(MapGraph<Activity, Double> graph, double projectDuration) {
        for (Activity activity : graph.vertices()) {
            activity.setLatestFinish(projectDuration); // Initialize with project duration
            for (Activity succ : graph.adjVertices(activity)) {
                activity.setLatestFinish(Math.min(activity.getLatestFinish(), succ.getLatestStart()));
            }
            activity.setLatestStart(activity.getLatestFinish() - activity.getDuration());
        }
    }


    public Map<Activity, Double> calculateSlack(MapGraph<Activity, Double> graph) {
        Map<Activity, Double> slackTimes = new HashMap<>();
        for (Activity activity : graph.vertices()) {
            double slack = activity.getLatestStart() - activity.getEarliestStart();
            activity.setSlack(slack);
            slackTimes.put(activity, slack);
            System.out.println("Activity: " + activity + " Slack: " + slack);
        }
        return slackTimes;
    }



    public List<Activity> findCriticalPath(MapGraph<Activity, Double> graph) {
        List<Activity> criticalPath = new ArrayList<>();
        for (Activity activity : graph.vertices()) {
            if (activity.getSlack() == 0) {
                criticalPath.add(activity);
            }
        }
        return criticalPath;
    }


    private double calculateTotalDuration(MapGraph<Activity, Double> graph) {
        double maxFinish = 0;
        for (Activity activity : graph.vertices()) {
            maxFinish = Math.max(maxFinish, activity.getEarliestFinish());
        }
        return maxFinish;
    }


}

