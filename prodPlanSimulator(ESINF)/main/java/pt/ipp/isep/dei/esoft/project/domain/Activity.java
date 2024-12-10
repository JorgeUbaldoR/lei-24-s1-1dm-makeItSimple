package pt.ipp.isep.dei.esoft.project.domain;

import java.util.List;
import java.util.Objects;

public class Activity {

    private final ID id;
    private String description;
    private double duration;
    private String durationUnit;
    private double cost;
    private String costUnit;
    private List<ID> predecessors;

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
        return Double.compare(duration, activity.duration) == 0 && Double.compare(cost, activity.cost) == 0 && Objects.equals(id, activity.id) && Objects.equals(description, activity.description) && Objects.equals(durationUnit, activity.durationUnit) && Objects.equals(costUnit, activity.costUnit) && Objects.equals(predecessors, activity.predecessors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, duration, durationUnit, cost, costUnit, predecessors);
    }
}
