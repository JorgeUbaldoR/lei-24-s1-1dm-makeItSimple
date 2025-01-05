package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Order;

/**
 * Singleton class that manages multiple repositories used in the application.
 * This class provides access to different repository instances such as
 * AuthenticationRepository, MachineRepository, ItemRepository, IDRepository,
 * and OperationRepository.
 */
public class Repositories {

    private static Repositories instance;
    private final AuthenticationRepository authenticationRepository;
    private final MachineRepository machineRepository;
    private final ItemRepository itemRepository;
    private final IDRepository idRepository;
    private final OperationRepository operationRepository;
    private final PETRGraphRepository petrGraphRepository;
    private final OrdersRepository ordersRepository;


    /**
     * Private constructor for Repositories.
     * Initializes the various repositories used in the application.
     */
    private Repositories() {
        authenticationRepository = new AuthenticationRepository();
        machineRepository = new MachineRepository();
        itemRepository = new ItemRepository();
        idRepository = new IDRepository();
        operationRepository = new OperationRepository(itemRepository.getItemList());
        petrGraphRepository = new PETRGraphRepository();
        ordersRepository = new OrdersRepository();
    }

    /**
     * Provides access to the singleton instance of Repositories.
     *
     * @return the single instance of Repositories
     */
    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
    }

    /**
     * Gets the AuthenticationRepository instance.
     *
     * @return the AuthenticationRepository instance
     */
    public AuthenticationRepository getAuthenticationRepository() {
        return authenticationRepository;
    }

    /**
     * Gets the MachineRepository instance.
     *
     * @return the MachineRepository instance
     */
    public MachineRepository getMachineRepository() {
        return machineRepository;
    }

    /**
     * Gets the ItemRepository instance.
     *
     * @return the ItemRepository instance
     */
    public ItemRepository getItemRepository() {
        return itemRepository;
    }

    /**
     * Gets the IDRepository instance.
     *
     * @return the IDRepository instance
     */
    public IDRepository getIDRepository() {
        return idRepository;
    }

    /**
     * Gets the OperationRepository instance.
     *
     * @return the OperationRepository instance
     */
    public OperationRepository getOperationRepository() {
        return operationRepository;
    }

    /**
     * Gets the OperationRepository instance.
     *
     * @return the OperationRepository instance
     */
    public PETRGraphRepository getPetrGraphRepository() {
        return petrGraphRepository;
    }

    /**
     * Gets the OrderRepository instance.
     *
     * @return the OrderRepository instance
     */
    public OrdersRepository getOrdersRepository() {
        return ordersRepository;
    }
}