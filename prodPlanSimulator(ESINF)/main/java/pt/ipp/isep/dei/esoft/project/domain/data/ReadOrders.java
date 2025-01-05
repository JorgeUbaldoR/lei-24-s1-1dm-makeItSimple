package pt.ipp.isep.dei.esoft.project.domain.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pt.ipp.isep.dei.esoft.project.application.controller.OrdersController;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.Item;
import pt.ipp.isep.dei.esoft.project.domain.Order;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.Priority;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

public class ReadOrders {

    public static void ordersReader(String filePath) {
        OrdersController ordersController = new OrdersController();
        Set<String> uniqueIds = new HashSet<>();

        try (Reader reader = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("order_id", "item_id","priority","qtd")
                    .withSkipHeaderRecord()
                    .parse(reader);


            for (CSVRecord record : records) {
                String orderId = record.get("order_id");
                String itemId = record.get("item_id");
                int priority = Integer.parseInt(record.get("priority"));
                double qtd = Double.parseDouble(record.get("qtd"));

                long row = record.getRecordNumber();
                if (!uniqueIds.add(orderId.trim())) {
                    throw new IllegalArgumentException("Error in row " + row + ": Duplicate activity ID found: " + orderId);
                }

                ID idOrder = new ID(Integer.parseInt(orderId), TypeID.ORDER);
                ID idItem = new ID(Integer.parseInt(itemId), TypeID.ITEM);

                Item item = getItemByID(idItem,ordersController);
                Priority priorityObj = getPriority(priority);

                Order order = new Order(idOrder,item, priorityObj, qtd);
                boolean valid = ordersController.addOrderToRepository(order);

                if (!valid) {
                    throw new IllegalArgumentException("Error in order " + orderId);
                }
            }


        } catch (Exception e) {
            throw new IllegalArgumentException("Error processing CSV file: " + e.getMessage());
        }
    }

    private static Item getItemByID(ID idItem, OrdersController controller) {
        Item item = controller.getItemFromRepository(idItem);
        if(item == null) {
            throw new IllegalArgumentException("Error in getItemByID: Item not found: " + idItem);
        }
        return item;
    }

    private static Priority getPriority(int priority) {
        return switch (priority) {
            case 0 -> Priority.LOW;
            case 1 -> Priority.NORMAL;
            case 2 -> Priority.HIGH;
            default -> throw new IllegalArgumentException("Invalid priority: " + priority);
        };
    }

}
