package pt.ipp.isep.dei.esoft.project.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

public class Activity {

    private static final double DEAFULT_DURATION = 0;
    private static final double DEAFULT_COST = 0;
    private static final String DEFUALT_DURATION_UNIT = "No duration unit";
    private static final String DEFUALT_COST_UNIT = "No cost unit";

    private final ID id;
    private String description;
    private double duration;
    private String durationUnit;
    private double cost;
    private String costUnit;
    private List<ID> predecessors;
    private List<ID> successors;

    // Attributes for forward and backward pass
    private double earliestStart;
    private double earliestFinish;
    private double latestStart;
    private double latestFinish;
    private double slack;


    public Activity(ID id, String description, double duration, String durationUnit,
                    double cost, String costUnit, List<ID> predecessors) {
        this.id = id;
        this.description = description;
        this.duration = duration;
        this.durationUnit = durationUnit;
        this.cost = cost;
        this.costUnit = costUnit;
        this.predecessors = predecessors;
        this.successors = new ArrayList<>();

    }

    public Activity(ID id, String description) {
        this.id = id;
        this.description = description;
        this.duration = DEAFULT_DURATION;
        this.durationUnit = DEFUALT_DURATION_UNIT;
        this.cost = DEAFULT_COST;
        this.costUnit = DEFUALT_COST_UNIT;
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
    }

    public ID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getDuration() {
        return duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public double getCost() {
        return cost;
    }

    public String getCostUnit() {
        return costUnit;
    }

    public List<ID> getPredecessors() {
        return predecessors;
    }

    public List<ID> getSuccessors() {
        return successors;
    }

    public double getEarliestStart() {
        return earliestStart;
    }

    public double getEarliestFinish() {
        return earliestFinish;
    }

    public double getLatestStart() {
        return latestStart;
    }

    public double getLatestFinish() {
        return latestFinish;
    }

    public double getSlack() {
        return slack;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setSuccessors(List<ID> successors) {
        this.successors = successors;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setCostUnit(String costUnit) {
        this.costUnit = costUnit;
    }

    public void setPredecessors(List<ID> predecessors) {
        this.predecessors = predecessors;
    }

    public void setEarliestStart(double earliestStart) {
        this.earliestStart = earliestStart;
    }

    public void setEarliestFinish(double earliestFinish) {
        this.earliestFinish = earliestFinish;
    }

    public void setLatestStart(double latestStart) {
        this.latestStart = latestStart;
    }

    public void setLatestFinish(double latestFinish) {
        this.latestFinish = latestFinish;
    }

    public void setSlack(double slack) {
        this.slack = slack;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, duration, durationUnit, cost, costUnit, predecessors);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("[" + ANSI_BRIGHT_WHITE).append(id.toString()).append(ANSI_RESET + "] \"Duration: ").append(duration + " (" + durationUnit + ") ");
        string.append("| Cost: ").append(cost + " (" + costUnit + ")\"");
        return string.toString();
    }
}
