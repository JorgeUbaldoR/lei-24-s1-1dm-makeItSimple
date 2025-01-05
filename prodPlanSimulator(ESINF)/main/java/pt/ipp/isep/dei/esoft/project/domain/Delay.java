package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Graph.CriticalPath;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.util.Iterator;
import java.util.Map;

public class Delay {

    public Map<String, Object> calculateOriginalCriticalPath(MapGraph<Activity, Double> createdMap) {
        CriticalPath criticalPath = new CriticalPath();
        return criticalPath.calculateCriticalPath(createdMap);
    }

    public void updateActivityDuration(Activity activity, double delay) {
        double newDuration = activity.getDuration() + delay;
        if (newDuration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        activity.setDuration(newDuration);
    }

    public Map<String, Object> calculateDelayedCriticalPath(MapGraph<Activity, Double> createdMap) {
        CriticalPath criticalPath = new CriticalPath();
        return criticalPath.calculateCriticalPath(createdMap);
    }

    public MapGraph<Activity, Double> removeActivities(MapGraph<Activity, Double> createdMap) {
        Iterator<Activity> iterator = createdMap.vertices().iterator();
        ID start = new ID(7777, TypeID.ACTIVITY);
        ID finish = new ID(7778, TypeID.ACTIVITY);
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            ID activityId = activity.getId();

            if (start.equals(activityId) || finish.equals(activityId)) {
                createdMap.removeVertex(activity);
            }
        }
        return createdMap;
    }
}
