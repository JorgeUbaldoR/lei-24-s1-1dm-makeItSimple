package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PETRGraphRepository {
    private final Map<ID,MapGraph<Activity,Double>> mapGraphRepository;

    public PETRGraphRepository() {
        mapGraphRepository = new HashMap<>();
    }


    public Optional<MapGraph<Activity,Double>> addGraph(MapGraph<Activity,Double> graph,ID idGraph) {
        Optional<MapGraph<Activity,Double>> newGraph = Optional.empty();

        if (!this.mapGraphRepository.containsKey(idGraph)) {
            this.mapGraphRepository.put(idGraph,graph);
            newGraph = Optional.of(graph.clone());
        }

        return newGraph;
    }

    public Map<ID, MapGraph<Activity, Double>> getMapGraphRepository() {
        return mapGraphRepository;
    }

    public MapGraph<Activity, Double> getMapGraphByID(ID graphID){
        return this.mapGraphRepository.get(graphID);
    }

    public boolean idGraphExistInRepository(ID id){
        return this.mapGraphRepository.containsKey(id);
    }

    public Activity getActivityByID(ID graphID, ID activityID){
        MapGraph<Activity, Double> graph = this.getMapGraphByID(graphID);

        for (Activity activity : graph.vertices()) {
            if(activity.getId().equals(activityID)){
                return activity;
            }
        }
        return null;
    }


}
