package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;

import java.util.*;

public class Bottlenecks {
    /**
     * Identifies bottleneck activities based on dependencies and critical paths.
     *
     * @param graph The directed graph representing the project.
     * @param criticalPaths The list of activities in the critical path.
     * @return A map containing top bottleneck activities and their metrics.
     */
    public static Map<String, Object> identifyBottlenecks(MapGraph<Activity, Double> graph, List<Activity> criticalPaths) {
        Map<String, Object> result = new HashMap<>();
        try {
            // Verifica se o grafo ou o caminho crítico está vazio
            if (graph == null || graph.vertices().isEmpty() || criticalPaths == null || criticalPaths.isEmpty()) {
                throw new IllegalArgumentException("Graph or critical paths are empty or null.");
            }

            Map<Activity, Integer> dependencyCount = new HashMap<>();
            Map<Activity, Integer> criticalPathFrequency = new HashMap<>();

            // Calcula o grau de dependência e frequência no caminho crítico
            for (Activity activity : graph.vertices()) {
                if (!activity.getId().toString().equals("A-7777") && !activity.getId().toString().equals("A-7778")) {
                    dependencyCount.put(activity, activity.getPredecessors().size());
                    criticalPathFrequency.put(activity, 0);
                }
            }

            // Corrigir dependencia 1 para os primeiros elementos

            for (Activity activity : dependencyCount.keySet()) {
                for (ID predecessor: activity.getPredecessors()) {
                    if (predecessor.toString().equals("A-7777")) {
                        dependencyCount.put(activity, 0);
                    }
                }
            }


            for (Activity activity : criticalPaths) {
                criticalPathFrequency.put(activity, criticalPathFrequency.get(activity) + 1);
            }

            List<Activity> bottlenecksList = new ArrayList<>(dependencyCount.keySet());
            bottlenecksList.sort((activity1, activity2) -> {
                int compareDependency = Integer.compare(dependencyCount.get(activity2), dependencyCount.get(activity1)); // Ordena pelo grau de dependência
                if (compareDependency == 0) {
                    return Integer.compare(criticalPathFrequency.get(activity2), criticalPathFrequency.get(activity1)); // Empate pela frequência
                }
                return compareDependency;
            });

            // Seleciona as Top 5 atividades
            Map<Activity, Integer> topBottlenecks = new LinkedHashMap<>();

            for (Activity activity : bottlenecksList.subList(0, Math.min(5, bottlenecksList.size()))) {
                topBottlenecks.put(activity, dependencyCount.get(activity));
            }

            // Preenche o resultado
            result.put("topBottlenecks", topBottlenecks);
            result.put("allBottlenecks", bottlenecksList);
        } catch (Exception e) {
            System.err.println("Error identifying bottlenecks: " + e.getMessage());
            result.put("topBottlenecks", Collections.emptyList());
            result.put("allBottlenecks", Collections.emptyList());
        }
        return result;
    }



}
