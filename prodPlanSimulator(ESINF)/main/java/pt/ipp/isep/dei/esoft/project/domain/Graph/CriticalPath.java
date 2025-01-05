package pt.ipp.isep.dei.esoft.project.domain.Graph;

import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;

import java.util.*;

public class CriticalPath {

    /**
     * Calculates the critical path based on precomputed schedule analysis.
     * Uses earliest and latest start/finish times already calculated (USEI20).
     *
     * @param graph The directed graph representing the project.
     * @return A map containing the critical path and the total project duration.
     */
    public Map<String, Object> calculateCriticalPath(MapGraph<Activity, Double> graph) {
        Map<String, Object> result = new HashMap<>();
        try {
            // Verifica se o grafo está vazio
            if (graph == null || graph.vertices().isEmpty()) {
                throw new IllegalArgumentException("Graph is empty or null.");
            }

            List<Activity> criticalPath = new ArrayList<>();
            double totalDuration = 0;

            // Filtrar atividades válidas (excluindo Start e Finish)
            List<Activity> filteredActivities = new ArrayList<>();
            for (Activity activity : graph.vertices()) {
                if (!activity.getId().toString().equals("A-7777") && !activity.getId().toString().equals("A-7778")) {
                    filteredActivities.add(activity);
                }
            }

            // Identificar caminho crítico
            for (Activity activity : filteredActivities) {
                if (activity.getSlack() == 0 && !activity.getPredecessors().isEmpty()) { // Atividades válidas
                    List<Activity> path = new ArrayList<>();
                    double pathDuration = 0;

                    Activity current = activity;
                    while (current != null) {
                        path.add(current);
                        pathDuration += current.getDuration(); // Soma a duração

                        // Encontra o próximo na sequência simplificado sem stream
                        Activity next = null;
                        for (Activity a : filteredActivities) {
                            if (a.getSlack() == 0 && a.getPredecessors().contains(current.getId())) {
                                next = a;
                                break;
                            }
                        }
                        current = next;
                    }

                    // Se a duração do caminho for a mais longa até agora
                    if (pathDuration > totalDuration) {
                        criticalPath = path; // Mantém apenas o caminho mais longo
                        totalDuration = pathDuration;
                    }
                }
            }

            // Preenche o resultado
            result.put("criticalPath", criticalPath);
            result.put("totalDuration", totalDuration);
        } catch (Exception e) {
            System.err.println("Error calculating critical path: " + e.getMessage());
            result.put("criticalPath", Collections.emptyList());
            result.put("totalDuration", 0.0);
        }

        return result;
    }
}

