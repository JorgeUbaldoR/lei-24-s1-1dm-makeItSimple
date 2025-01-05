package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.Item;
import pt.ipp.isep.dei.esoft.project.domain.Operation;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.io.IOException;
import java.util.*;

import static pt.ipp.isep.dei.esoft.project.domain.data.ReadTreeInfo.getOpOrItem;

/**
 * Repository class that manages operations associated with machines.
 * This class allows for the addition and retrieval of operations.
 */
public class OperationRepository {
    private final Set<Operation> operations;
    private final Map<ID, Operation> idToOperation;

    /**
     * Constructs an OperationRepository instance.
     * Initializes the set to hold operations.
     *
     * @param items a list of items to fill the repository with operations
     */
    public OperationRepository(List<Item> items) {
        this.operations = new HashSet<>();
        this.idToOperation = new HashMap<>();
        //fillOperations();
        fillOperations(items);
    }

    public OperationRepository() {
        this.operations = new HashSet<>();
        this.idToOperation = new HashMap<>();
    }

    /**
     * Adds an operation to the repository if it is not already present.
     *
     * @param operation the operation to be added
     * @return an Optional containing the cloned operation if added successfully, or empty if it was already present
     */
    public Optional<Operation> addOperation(Operation operation) {
        Optional<Operation> op = Optional.empty();
        if (!operations.contains(operation)) {
            op = Optional.of(operation.clone());
            operations.add(operation);
            idToOperation.put(operation.getOperationId(), operation);
        } else {
            return op;
        }
        return op;
    }


    /**
     * Populates the operations list with operations extracted from the provided items.
     * <p>
     * This method iterates over each item in the provided list and retrieves the list of operations
     * associated with each item. Each operation is cloned to ensure that the original operation objects
     * are not modified when added to the operations list. The cloned operations are then added to the
     * internal list of operations.
     *
     * @param items a list of Item objects from which operations are extracted and added to the operations list.
     */
    public void fillOperations(List<Item> items) {
        try {

            String PATH_OPERATIONS = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/operations.csv";
            String PATH_OPERATIONS_LAPR = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/SQL Developer/files/OPERATION.csv";

            List<String[]> operationDetails = getOpOrItem(PATH_OPERATIONS);
            List<String[]> operationDetailsLapr = getOpOrItem(PATH_OPERATIONS_LAPR);


            for (Item item : items) {
                List<Operation> queue = item.getOperationList();

                for (Operation operation : queue) {

                    for (String[] operationDetail : operationDetails) {
                        if (operation.getOperationId().getSerial() == Integer.parseInt(operationDetail[0])) {
                            operation.setOperationName(operationDetail[1].trim());
                            idToOperation.put(operation.getOperationId(), operation);
                            operations.add(operation);
                            break;
                        }
                    }


                    for (String[] operationDetail : operationDetailsLapr) {
                        if (operation.getOperationId().getSerial() == Integer.parseInt(operationDetail[0])) {
                            operation.setOperationName(operationDetail[1].trim());
                            idToOperation.put(operation.getOperationId(), operation);
                            operations.add(operation);
                            break;
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading operations from file");
        }
    }

    /**
     * Retrieves a list of all operations in the repository.
     *
     * @return a list containing all operations
     */
    public List<Operation> getOperations() {
        return new ArrayList<>(operations);
    }

    /**
     * Retrieves all operations from the operations list.
     *
     * @return An Optional containing a list of operations if available; otherwise, an empty Optional.
     */
    public Optional<List<Operation>> getAllOperations() {
        Optional<List<Operation>> op = Optional.empty();
        List<Operation> operations = getOperations();
        if (!operations.isEmpty()) {
            op = Optional.of(operations);
        }
        return op;
    }


    /**
     * Registers a new operation with the given name and description.
     *
     * @param name        The name of the operation to be registered.
     * @param description The description of the operation.
     * @return An Optional containing the registered operation if successful; otherwise, an empty Optional.
     */
    public Optional<Operation> registerOperation(String name, String description, ID operationID) {
        Optional<Operation> optionalValue = Optional.empty();

        Operation operation = new Operation(name, operationID, description);

        if (operations.add(operation)) {
            optionalValue = Optional.of(operation);
        }
        return optionalValue;
    }


    /**
     * Registers a new operation with the given name.
     *
     * @param name        The name of the operation to be registered.
     * @param operationID The id  of the operation to be registered.
     * @return An Optional containing the registered operation if successful; otherwise, an empty Optional.
     */
    public Optional<Operation> registerOperation(String name, ID operationID) {
        Optional<Operation> optionalValue = Optional.empty();

        Operation operation = new Operation(name, operationID);

        if (operations.add(operation)) {
            optionalValue = Optional.of(operation);
        }
        return optionalValue;
    }

    /**
     * Retrieves the name of an operation by its ID.
     *
     * @param id The ID of the operation.
     * @return The name of the operation if found, or null if not found.
     */
    public String getNameByID(ID id) {
        for (Operation operation : operations) {
            if (operation.getOperationId().equals(id)) {
                return operation.getOperationName();
            }
        }
        return null;
    }

    /**
     * Retrieves the map of operation IDs to operations.
     *
     * @return The map containing operation IDs as keys and operations as values.
     */
    public Map<ID, Operation> getIdToOperation() {
        return idToOperation;
    }


    public Operation getOperationByName(String name) {
        for (Operation operation : operations) {
            if (operation.getOperationName().equals(name)) {
                return operation;
            }
        }
        return null;
    }
}
