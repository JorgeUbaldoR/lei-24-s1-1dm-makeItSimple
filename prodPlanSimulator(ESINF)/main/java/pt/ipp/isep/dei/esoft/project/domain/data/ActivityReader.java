package pt.ipp.isep.dei.esoft.project.domain.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.Algorithms;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;

/**
 * The ActivityReader class is responsible for reading and processing an Activity CSV file.
 * It creates a graph of activities with dependencies, validates input data, and checks for circular dependencies in the graph.
 */
public class ActivityReader {
    private static final int START_OF_PREDECESSORS = 5;  // Index for the 'predecessors' column in the new format
    private static final int START_ID_DEFAULT = 7777;
    private static final int FINISH_ID_DEFAULT = 7778;
    /**
     * Reads an activity CSV file and creates a graph of activities with their dependencies.
     *
     * @param filePath The path to the CSV file containing activity data.
     * @param isDirected Whether the graph should be directed or undirected.
     * @return A MapGraph containing the activities as vertices and their dependencies as edges.
     * @throws IllegalArgumentException If there is an error processing the CSV file, such as invalid data or circular dependencies.
     *
     * Time Complexity: O(n + m), where n is the number of records (activities) in the CSV file and m is the number of edges (dependencies).
     */
    public static MapGraph<Activity, Double> readCSV(String filePath, boolean isDirected) {
        MapGraph<Activity, Double> graph = new MapGraph<>(isDirected);
        Set<String> uniqueIds = new HashSet<>();

        try (Reader reader = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("ActivKey", "descr", "duration", "duration-unit", "tot-cost", "predecessors")
                    .withSkipHeaderRecord()
                    .parse(reader);

            Map<ID, Activity> activityMap = new HashMap<>();
            Set<ID> predecessorsSet = new HashSet<>();

            ID startActivityID = new ID(START_ID_DEFAULT, TypeID.ACTIVITY);
            ID finishID = new ID(FINISH_ID_DEFAULT, TypeID.ACTIVITY);

            Activity startActivity = new Activity(startActivityID, "Start");
            Activity finishActivity = new Activity(finishID, "Finish");

            graph.addVertex(startActivity);
            graph.addVertex(finishActivity);

            activityMap.put(startActivityID, startActivity);

            for (CSVRecord record : records) {
                String id = record.get("ActivKey");
                String description = record.get("descr");
                String duration = record.get("duration");
                String durationUnit = record.get("duration-unit");
                String cost = record.get("tot-cost");
                String predecessors = record.get("predecessors");

                List<ID> predecessorIds = new LinkedList<>();
                long row = record.getRecordNumber();
                int idValid = validateParametersUnits(id, duration, cost, row);

                if (!uniqueIds.add(id.trim())) {
                    throw new IllegalArgumentException("Error in row " + row + ": Duplicate activity ID found: " + id);
                }

                // Parse the comma-separated list of predecessor IDs
                if (!predecessors.isEmpty()) {
                    String[] predecessorList = predecessors.split(",");
                    for (String pred : predecessorList) {
                        String validID = getFinalID(pred);
                        checkString(validID, "PREDECESSOR", row);
                        checkConversionAndNegativeInt(validID, "PREDECESSOR", row);
                        ID predecessorID = new ID(Integer.parseInt(validID), TypeID.ACTIVITY);
                        predecessorIds.add(predecessorID);
                        predecessorsSet.add(predecessorID);
                    }
                } else {
                    predecessorIds.add(startActivityID);
                }

                // Create the Activity object
                Activity activity = new Activity(
                        new ID(idValid, TypeID.ACTIVITY),
                        description,
                        Double.parseDouble(duration.trim()),
                        durationUnit,
                        Double.parseDouble(cost.trim()),
                        "unit",
                        predecessorIds
                );

                activityMap.put(activity.getId(), activity);
                graph.addVertex(activity);
            }

            addFinishPredecessors(activityMap,predecessorsSet,graph,finishActivity);


            // Add edges for dependencies
            for (Activity activity : graph.vertices()) {
                for (ID predId : activity.getPredecessors()) {
                    Activity predAct = activityMap.get(predId);
                    if (predAct == null) {
                        throw new IllegalArgumentException("ERROR IN FILE: For activity [" + activity.getId() +
                                "] whose predecessor is [" + predId + "] was not found in vertex of the graph.");
                    }
                    graph.addEdge(predAct, activity, predAct.getDuration());
                }
            }

            // Add successors to each activity
            for (Activity activity : graph.vertices()) {
                List<ID> successors = new ArrayList<>();
                for (Activity otherActivity : graph.adjVertices(activity)) {
                    successors.add(otherActivity.getId());
                }
                activity.setSuccessors(successors);
            }

            // Check for circular dependencies
            if (Algorithms.hasCircularDependencies(graph)) {
                throw new IllegalArgumentException("ERROR IN FILE: Graph has Circular Dependencies.");
            }

            return graph;

        } catch (Exception e) {
            throw new IllegalArgumentException("Error processing CSV file: " + e.getMessage());
        }
    }

    private static void addFinishPredecessors(Map<ID, Activity> activityMap, Set<ID> predecessorsSet, MapGraph<Activity, Double> graph,Activity finishActivity) {
        List<ID> predecessorIds = new LinkedList<>();
        for(ID activity : activityMap.keySet()) {
            if (!predecessorsSet.contains(activity) && activity.getSerial() != START_ID_DEFAULT) {
                predecessorIds.add(activity);
            }
        }
        finishActivity.setPredecessors(predecessorIds);
        activityMap.put(finishActivity.getId(), finishActivity);
    }

    /**
     * Extracts and returns the numeric part of a string ID.
     *
     * @param id The string ID to extract the numeric part from.
     * @return The numeric part of the string as a string.
     *
     * Time Complexity: O(n), where n is the length of the string id.
     */
    private static String getFinalID(String id) {
        StringBuilder num = new StringBuilder();
        id.chars().forEach(c -> {
            if (c >= '0' && c <= '9') {
                num.append((char)c);
            }
        });
        return num.toString();
    }

    //-------------------------- Validations Input ---------------------------------------------

    /**
     * Validates if the provided string is not null or empty.
     *
     * @param string The string to validate.
     * @return true if the string is not null or empty, false otherwise.
     *
     * Time Complexity: O(1)
     */
    public static boolean validateString(String string) {
        return (string != null && !string.trim().isEmpty());
    }

    /**
     * Validates the parameters for ID, duration, and cost. Checks for empty strings and converts the values to integers and doubles.
     *
     * @param id The ID of the activity.
     * @param duration The duration of the activity.
     * @param cost The cost of the activity.
     * @param row The row number of the CSV record.
     * @return The valid ID as an integer.
     * @throws IllegalArgumentException If any of the parameters are invalid.
     *
     * Time Complexity: O(1), constant time for the validations and conversions.
     */
    static int validateParametersUnits(String id, String duration, String cost, long row) {
        checkString(id, "ID", row);
        checkString(duration, "DURATION", row);
        checkString(cost, "COST", row);
        return checkConversion(id, duration, cost, row);
    }

    /**
     * Checks that the given string parameter is not null or empty.
     *
     * @param param The parameter to check.
     * @param token The name of the parameter (for error messages).
     * @param row The row number of the CSV record.
     * @throws IllegalArgumentException If the parameter is null or empty.
     *
     * Time Complexity: O(1)
     */
    private static void checkString(String param, String token, long row) {
        if (!validateString(param)) {
            throw new IllegalArgumentException("Error in row " + row + ": Invalid " + token + " - make sure it is not empty or null");
        }
    }

    /**
     * Converts and checks the validity of the ID, duration, and cost parameters.
     * Ensures that they are valid numbers and not negative.
     *
     * @param id The ID of the activity.
     * @param duration The duration of the activity.
     * @param cost The cost of the activity.
     * @param row The row number of the CSV record.
     * @return The valid ID as an integer.
     * @throws IllegalArgumentException If the parameters are not valid or negative.
     *
     * Time Complexity: O(1), constant time for the conversions and checks.
     */
    private static int checkConversion(String id, String duration, String cost, long row) {
        String num = getFinalID(id);
        checkConversionAndNegativeInt(num.toString(), "ID", row);
        checkConversionAndNegativeDouble(duration, "DURATION", row);
        checkConversionAndNegativeDouble(cost, "COST", row);
        return Integer.parseInt(num.toString());
    }

    /**
     * Checks if the given integer parameter is a valid number and not negative.
     *
     * @param param The parameter to check.
     * @param token The name of the parameter (for error messages).
     * @param row The row number of the CSV record.
     * @throws IllegalArgumentException If the parameter is not a valid number or is negative.
     *
     * Time Complexity: O(1)
     */
    private static void checkConversionAndNegativeInt(String param, String token, long row) {
        try {
            if (Integer.parseInt(param) < 0) {
                sendNegativeError(token, param);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error in row " + row + ": Invalid " + token + " -> [" + param + "] is not a number.");
        }
    }

    /**
     * Checks if the given double parameter is a valid number and not negative.
     *
     * @param param The parameter to check.
     * @param token The name of the parameter (for error messages).
     * @param row The row number of the CSV record.
     * @throws IllegalArgumentException If the parameter is not a valid number or is negative.
     *
     * Time Complexity: O(1)
     */
    private static void checkConversionAndNegativeDouble(String param, String token, long row) {
        try {
            if (Double.parseDouble(param) < 0) {
                sendNegativeError(token, param);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error in row " + row + ": Invalid " + token + " -> [" + param + "] is not a number.");
        }
    }

    /**
     * Throws an exception if a parameter is negative.
     *
     * @param token The name of the parameter (for error messages).
     * @param param The parameter value.
     * @throws IllegalArgumentException If the parameter is negative.
     *
     * Time Complexity: O(1)
     */
    private static void sendNegativeError(String token, String param) {
        throw new IllegalArgumentException("Invalid " + token + " -> [" + param + "] cannot be negative.");
    }
}
