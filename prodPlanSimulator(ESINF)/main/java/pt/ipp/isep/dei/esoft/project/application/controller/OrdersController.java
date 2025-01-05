package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.Item;
import pt.ipp.isep.dei.esoft.project.domain.Order;
import pt.ipp.isep.dei.esoft.project.domain.data.ReadOrders;
import pt.ipp.isep.dei.esoft.project.repository.ItemRepository;
import pt.ipp.isep.dei.esoft.project.repository.OrdersRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.PriorityQueue;
import java.util.Queue;

public class OrdersController {
    private OrdersRepository ordersrepository;
    private ItemRepository itemRepository;

    public OrdersController() {
        ordersrepository = getOrdersRepository();
        itemRepository = getItemRepository();
    }

    private ItemRepository getItemRepository() {
        if (itemRepository == null) {
            Repositories repositories = Repositories.getInstance();
            itemRepository = repositories.getItemRepository();
        }
        return itemRepository;
    }

    private OrdersRepository getOrdersRepository() {
        if (ordersrepository == null) {
            Repositories repositories = Repositories.getInstance();
            ordersrepository = repositories.getOrdersRepository();
        }
        return ordersrepository;
    }

    public boolean addOrderToRepository(Order order) {
        return getOrdersRepository().addOrder(order);
    }

    public Item getItemFromRepository(ID itemID) {
        return getItemRepository().getItemNameById(itemID);
    }

    public PriorityQueue<Order> readOrderCSV(String filePath) {
        ReadOrders.ordersReader(filePath);
        return getOrdersRepository().getOrdersList();
    }

}
