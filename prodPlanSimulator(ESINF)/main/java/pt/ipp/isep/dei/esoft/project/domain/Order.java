package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.enumclasses.Priority;

import java.util.Objects;

public class Order implements Comparable<Order> {


    private final ID orderID;
    private final Item item;
    private final Priority priority;
    private final double quantity;

    public Order(ID orderID, Item item, Priority priority, double quantity) {
        this.orderID = orderID;
        this.item = item;
        this.priority = priority;
        this.quantity = quantity;
    }

    public ID getOrderID() {
        return orderID;
    }

    public Item getItem() {
        return item;
    }

    public Priority getPriority() {
        return priority;
    }

    public double getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("Order[ID=%s, Item=%s, Priority=%s, Quantity=%.2f]",
                orderID, item.getName(), priority, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity && Objects.equals(orderID, order.orderID) && Objects.equals(item, order.item) && priority == order.priority;
    }

    @Override
    public int compareTo(Order o) {
        return this.priority.compareTo(o.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, item, priority, quantity);
    }
}
