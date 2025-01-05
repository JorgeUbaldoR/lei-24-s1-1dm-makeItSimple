package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Order;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class OrdersRepository {
    private final PriorityQueue<Order> ordersList;

    public OrdersRepository() {
        this.ordersList = new PriorityQueue<>();
    }

    public boolean addOrder(Order order) {
        if(checkIDOrder(order)){
            return false;
        }
        ordersList.offer(order);
        return true;
    }

    private boolean checkIDOrder(Order order) {
        return ordersList.contains(order);
    }

    public PriorityQueue<Order> getOrdersList() {
        return new PriorityQueue<>(ordersList);
    }
}
