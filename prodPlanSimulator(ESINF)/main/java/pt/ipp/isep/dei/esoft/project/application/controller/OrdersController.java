package pt.ipp.isep.dei.esoft.project.application.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.domain.TreeClasses.Node;
import pt.ipp.isep.dei.esoft.project.domain.TreeClasses.ProductionTree;
import pt.ipp.isep.dei.esoft.project.domain.data.ReadOrders;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;
import pt.ipp.isep.dei.esoft.project.files.ExportTimes;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.ui.console.ProductionTreeUI;

import java.io.*;
import java.util.*;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_BRIGHT_RED;

public class OrdersController {
    private final String PATH_WORKSTATIONS = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/SQL Developer/files/Workstations_LAPR.csv";
    private final String PATH_BOO = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/SQL Developer/files/BOO_LAPR.csv";
    private final String PATH_BOO_RESULT = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/BooOrders.csv";
    private OrdersRepository ordersrepository;
    private ItemRepository itemRepository;
    private MachineRepository machineRepository;
    private OperationRepository operationRepository;
    private ProductionTreeUI productionTreeUI;
    private SimulatorController simulatorController;

    public OrdersController() {
        ordersrepository = getOrdersRepository();
        itemRepository = getItemRepository();
        machineRepository = getMachineRepository();
        operationRepository = getOperationRepository();
        productionTreeUI = new ProductionTreeUI();
        simulatorController = new SimulatorController();
    }

    private OperationRepository getOperationRepository() {
        if (operationRepository == null) {
            operationRepository = Repositories.getInstance().getOperationRepository();
        }
        return operationRepository;
    }

    private MachineRepository getMachineRepository() {
        if (machineRepository == null) {
            Repositories repositories = Repositories.getInstance();
            machineRepository = repositories.getMachineRepository();
        }
        return machineRepository;
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

    public Queue<Machine> readMachinhesCSV() {
        Queue<Machine> machines = new LinkedList<>();
        try(Reader reader = new FileReader(PATH_WORKSTATIONS)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("workstation","name_oper","time")
                    .withSkipHeaderRecord()
                    .parse(reader);

            for (CSVRecord record : records) {
                ID workstation = new ID(Integer.parseInt(record.get("workstation")),TypeID.MACHINE) ;
                String name = record.get("name_oper");
                float time = Float.parseFloat(record.get("time"));

                Operation operation = getOperationByName(name);

                if (operation == null) {
                    throw new RuntimeException("Operation '" + name + "' not found!");
                }

                Machine machine = new Machine(workstation,operation,time);
                Optional<Machine> result = getMachineRepository().addMachine(machine);

                machines.offer(machine);
                if (result.isEmpty()) {
                    throw new RuntimeException("Could not add machine");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return machines;
    }

    private Operation getOperationByName(String name) {
        return getOperationRepository().getOperationByName(name);
    }

    public void createProductionTree(){
        Queue<Order> orders = getOrdersRepository().getOrdersList();
        int numOrders = orders.size();

        for (int i = 0; i < numOrders; i++) {
            Order order = orders.poll();
            ID itemID = order.getItem().getItemID();
            writeBooForID(itemID.getSerial());

            productionTreeUI.ordersBOOProduction("Order ("+order.getOrderID()+")",PATH_BOO_RESULT);
            productionTreeUI.auxilaryOrder(itemID,(float)order.getQuantity(),PATH_BOO_RESULT);

            Map<ID,Float> list = simulatorController.startSimulationOrders();
            System.out.println();
            for (Map.Entry<ID,Float> entry : list.entrySet()) {
                ExportTimes.updateReservedTable(entry.getValue(),entry.getKey().getSerial());
            }
        }

    }

    private void writeBooForID(int serial) {
        try {
            Reader reader = new FileReader(PATH_BOO);
            BufferedReader bufferedReader = new BufferedReader(reader);

            Writer writer = new FileWriter(new File(PATH_BOO_RESULT));
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write("op_id;item_id;item_qtd;(;op1;op_qtd1;op2;op_qtd2;opN;op_qtdN;);(;item_id1;item_qtd1;item_id1;item_qtd1;item_id1;item_qtd1;)\n");

            String line;
            boolean startWriting = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Divide a linha em colunas (assumindo separador ";")
                String[] columns = line.split(";");

                // Verifica se a linha contém a coluna item_id com o valor `serial`
                if (!startWriting && columns.length > 1) {
                    try {
                        int itemId = Integer.parseInt(columns[1].trim());
                        if (itemId == serial) {
                            startWriting = true; // Inicia a escrita a partir desta linha
                        }
                    } catch (NumberFormatException e) {
                        // Ignora erros de parsing de números
                    }
                }

                // Escreve a linha no arquivo de saída se startWriting for true
                if (startWriting) {
                    bufferedWriter.write(line + "\n");
                }
            }

            // Fecha os recursos
            bufferedReader.close();
            bufferedWriter.close();

            //System.out.println("Arquivo gerado com sucesso em: " + PATH_BOO_RESULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
