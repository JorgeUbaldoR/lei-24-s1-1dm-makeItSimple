package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.repository.PETRGraphRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;
import java.util.Map;

public class ShowGraphCriticalPathController {

    private PETRGraphRepository graphRepository;

    public ShowGraphCriticalPathController() {
        this.graphRepository = getGraphRepository();
    }

    private PETRGraphRepository getGraphRepository() {
        if (graphRepository == null) {
            Repositories repository = Repositories.getInstance();
            graphRepository = repository.getPetrGraphRepository();
        }
        return graphRepository;
    }

    public Map<String, Object> calculateCriticalPath(ID graphID) {
        return graphRepository.calculateCriticalPath(graphID);
    }

    public Map<String, Object> identifyBottlenecks(ID graphID) {
        return graphRepository.identifyBottlenecks(graphID);
    }


}
