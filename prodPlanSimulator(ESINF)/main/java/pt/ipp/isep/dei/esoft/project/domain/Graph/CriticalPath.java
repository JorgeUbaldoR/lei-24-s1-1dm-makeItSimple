package pt.ipp.isep.dei.esoft.project.domain.Graph;

import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;

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

            // Identificar nós de início
            List<Activity> startNodes = new ArrayList<>();
            for (Activity activity : filteredActivities) {
                boolean isStartNode = false;
                for (ID id : activity.getPredecessors()) {
                    if (id.toString().equals("A-7777")) {
                        isStartNode = true;
                        break;
                    }
                }
                if (isStartNode) {
                    startNodes.add(activity);
                }
            }

            // Explorar todos os caminhos começando dos nós iniciais
            Deque<Activity> stack = new ArrayDeque<>();
            Deque<Double> durationStack = new ArrayDeque<>();
            Deque<List<Activity>> pathStack = new ArrayDeque<>();

            for (Activity start : startNodes) {
                stack.push(start);
                durationStack.push(0.0);
                pathStack.push(new ArrayList<>());

                while (!stack.isEmpty()) {
                    Activity current = stack.pop();
                    double pathDuration = durationStack.pop();
                    List<Activity> currentPath = new ArrayList<>(pathStack.pop());

                    currentPath.add(current);
                    pathDuration += current.getDuration();

                    // Verifica se é um nó final
                    boolean isEndNode = true;
                    for (ID id : current.getSuccessors()) {
                        if (!id.toString().equals("A-7778")) {
                            isEndNode = false;
                            break;
                        }
                    }

                    if (isEndNode) {
                        if (pathDuration > totalDuration) {
                            criticalPath = new ArrayList<>(currentPath);
                            totalDuration = pathDuration;
                        }
                    } else {
                        // Explorar sucessores válidos com slack = 0
                        for (Activity successor : filteredActivities) {
                            if (successor.getSlack() == 0 && current.getSuccessors().contains(successor.getId())) {
                                stack.push(successor);
                                durationStack.push(pathDuration);
                                pathStack.push(new ArrayList<>(currentPath));
                            }
                        }
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

