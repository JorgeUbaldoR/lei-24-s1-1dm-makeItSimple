package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Graph.CriticalPath;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Delay {

    /**
     * Calculates the critical path by calling a method in CriticalPath class
     *
     * @param createdMap needed for the calculation of the critical path
     * @return A map with the critical path
     */
    public Map<String, Object> calculateCriticalPath(MapGraph<Activity, Double> createdMap) {
        CriticalPath criticalPath = new CriticalPath();
        return criticalPath.calculateCriticalPath(createdMap);
    }

    /**
     * Updates the duration of a Activity
     *
     * @param activity to be updated
     * @param duration of the activity
     */
    public void updateActivityDuration(Activity activity, double duration) {
        double newDuration = activity.getDuration() + duration;
        if (newDuration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        activity.setDuration(newDuration);
    }

    /**
     * Removes the IDs A-7777 and A-7778 since they are just a placeholder for start and finish
     *
     * @param createdMap where the IDs should be removed from
     * @return a new map where there are no start and finish placeholders
     */
    public MapGraph<Activity, Double> removeActivities(MapGraph<Activity, Double> createdMap) {
        ID start = new ID(7777, TypeID.ACTIVITY);
        ID finish = new ID(7778, TypeID.ACTIVITY);

        List<Activity> toRemove = new ArrayList<>();
        for (Activity activity : createdMap.vertices()) {
            ID activityId = activity.getId();
            if (start.equals(activityId) || finish.equals(activityId)) {
                toRemove.add(activity);
            }
        }

        for (Activity activity : toRemove) {
            createdMap.removeVertex(activity);
        }

        return createdMap;
    }
}
