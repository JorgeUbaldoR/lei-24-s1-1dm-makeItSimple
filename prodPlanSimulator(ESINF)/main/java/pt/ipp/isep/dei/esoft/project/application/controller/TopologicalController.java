package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.Pair;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.repository.PETRGraphRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Optional;

public class TopologicalController {

    private PETRGraphRepository graphRepository;


    public TopologicalController() {
        this.graphRepository = getGraphRepository();
    }


    private PETRGraphRepository getGraphRepository() {
        if (graphRepository == null) {
            Repositories repository = Repositories.getInstance();
            graphRepository = repository.getPetrGraphRepository();
        }
        return graphRepository;
    }

    public MapGraph<Activity, Double> getGraph(ID graphID) {
        return graphRepository.getMapGraph(graphID);
    }
}
