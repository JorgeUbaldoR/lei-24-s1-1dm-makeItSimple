package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.repository.PETRGraphRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Optional;

public class ProjectScheduleController {

    private PETRGraphRepository graphRepository;


    public ProjectScheduleController() {
        this.graphRepository = getGraphRepository();
    }


    private PETRGraphRepository getGraphRepository() {
        if (graphRepository == null) {
            Repositories repository = Repositories.getInstance();
            graphRepository = repository.getPetrGraphRepository();
        }
        return graphRepository;
    }

    public Optional<String> getFileName(String fileName) {
        return graphRepository.getFileName(fileName);

    }
}
