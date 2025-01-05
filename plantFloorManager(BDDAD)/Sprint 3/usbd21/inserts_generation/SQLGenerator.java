import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SQLGenerator {

    private static final String BOO = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/BOO.csv";
    private static final String BOO_INPUT = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/BOO_INPUT.csv";
    private static final String BOO_OUTPUT = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/BOO_OUTPUT.csv";
    private static final String BOO_TEMPLATE = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/BOO_TEMPLATE.csv";
    private static final String COMPONENT = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/COMPONENT.csv";
    private static final String COSTUMER = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/COSTUMER.csv";
    private static final String DEACTIVATED_CUSTOMERS = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/Deactivated_Costumers.csv";
    private static final String INTERMEDIATE_PRODUCT = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/INTERMEDIATE_PRODUCT.csv";
    private static final String OPERATION = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/OPERATION.csv";
    private static final String OPERATION_TYPE = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/OPERATION_TYPE.csv";
    private static final String ORDER = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/Order.csv";
    private static final String ORDER_PRODUCTS = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/ORDER_PRODUCTS.csv";
    private static final String PART = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/PART.csv";
    private static final String PART_TYPE = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/PART_TYPE.csv";
    private static final String PROD_FAMILY = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/PROD_FAMILY.csv";
    private static final String PRODUCT = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/PRODUCT.csv";
    private static final String PRODUCTION_ORDER = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/PRODUCTION_ORDER.csv";
    private static final String PRODUCTION_ORDER_WORKSTATION = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/PRODUCTION_ORDER_WORKSTATION.csv";
    private static final String RAW_MATERIAL = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/RAW_MATERIAL.csv";
    private static final String RESERVED = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/RESERVED.csv";
    private static final String SUPPLIER = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/SUPPLIER.csv";
    private static final String SUPPLIER_PART = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/SUPPLIER_PART.csv";
    private static final String WORKSTATION = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/WORK_STATION.csv";
    private static final String WORKSTATION_TYPE = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/WORKSTATION_TYPE.csv";
    private static final String WORKSTATION_TYPE_OPERATION_TYPE = "plantFloorManager(BDDAD)/Sprint 3/usbd21/data base/WORKSTATION_TYPE_OPERATION_TYPE.csv";

    public static void main(String[] args) {
        costumer();
        prodFamily();
        part();
        product();
        order();
        orderProducts();
        operationType();
        booTemplate();
        workstationType();
        workStation();
        workstationTypeOperationType();
        boo();
        component();
        intermediateProduct();
        rawMaterial();
        operation();
        booInput();
        booOutput();
        deactivatedCustomers();
        partType();
        productionOrder();
        productionOrderWorkstation();
        reserved();
        supplier();
        supplierPart();
    }

    private static void boo() {
        processCSV(BOO, "--Inserts for boo", columns -> {
            String sql = "INSERT INTO BOO (ProductProduct_ID) VALUES ('"
                    + columns[0] + "');";
            System.out.println(sql);
        });
    }

    private static void booInput() {
        processCSV(BOO_INPUT, "--Inserts for boo_input", columns -> {
            String sql = "INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES ("
                    + columns[0] + ", '" + columns[1] + "', " + columns[2] + ", '" + columns[3] + "');";
            System.out.println(sql);
        });
    }

    private static void booOutput() {
        processCSV(BOO_OUTPUT, "--Inserts for boo_output", columns -> {
            String sql = "INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES ("
                    + columns[0] + ", '" + columns[1] + "', " + columns[2] + ", '" + columns[3] + "');";
            System.out.println(sql);
        });
    }

    private static void booTemplate() {
        processCSV(BOO_TEMPLATE, "--Inserts for boo_template", columns -> {
            String sql = "INSERT INTO BOO_TEMPLATE (OPNUMBER, OperationOPERATION_ID, Prod_FamilyFAMILY_ID) VALUES ("
                    + columns[0] + ", " + columns[1] + ", " + columns[2] + ");";
            System.out.println(sql);
        });
    }

    private static void component() {
        processCSV(COMPONENT, "--Inserts for component", columns -> {
            String sql = "INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('"
                    + columns[0] + "', " + columns[1] + ", " + columns[2] + ");";
            System.out.println(sql);
        });
    }

    private static void costumer() {
        processCSV(COSTUMER, "--Inserts for customer", columns -> {
            String sql = "INSERT INTO Customer (COSTUMER_ID, VAT, NAME, ADDRESS, CITY, COUNTRY, ZIP, PHONE, EMAIL) VALUES ("
                    + columns[0] + ", '" + columns[1] + "', '" + columns[2] + "', '" + columns[3] + "', '"
                    + columns[4] + "', '" + columns[5] + "', '" + columns[6] + "', " + columns[7] + ", '"
                    + columns[8] + "');";
            System.out.println(sql);
        });
    }

    private static void intermediateProduct() {
        processCSV(INTERMEDIATE_PRODUCT, "--Inserts for intermediate product", columns -> {
            String sql = "INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('"
                    + columns[0] + "', " + columns[1] + ", " + columns[2] + ");";
            System.out.println(sql);
        });
    }

    private static void operation() {
        processCSV(OPERATION, "--Inserts for operation", columns -> {
            // Start building the SQL statement
            String sql = "INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, Operation_TYPEOPTYPE_ID, BOOProductProduct_ID, NEXTSTEP) VALUES ("
                    + columns[0] + ", '" + columns[1] + "', ";

            // Handle the EXPECTEDTIME column (use NULL if it's empty)
            if (columns[2].isEmpty()) {
                sql += "NULL, ";
            } else {
                sql += columns[2] + ", ";
            }

            // Add the Operation_TYPEOPTYPE_ID column value
            sql += columns[3] + ", ";

            // Add the BOOProductProduct_ID column value
            sql += "'" + columns[4] + "', ";

            // Handle the NEXTSTEP column (use NULL if it's missing or empty)
            if (columns.length > 5 && !columns[5].isEmpty()) {
                sql += columns[5] + ");";
            } else {
                sql += "NULL);";  // If NEXTSTEP is missing, set it as NULL
            }

            // Print the generated SQL statement
            System.out.println(sql);
        });
    }


    private static void operationType() {
        processCSV(OPERATION_TYPE, "--Inserts for operation_type", columns -> {
            String sql = "INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES ("
                    + columns[0] + ", '" + columns[1] + "');";
            System.out.println(sql);
        });
    }

    private static void order() {
        processCSV(ORDER, "--Inserts for order", columns -> {
            String sql = "INSERT INTO \"Order\" (ORDER_ID, CustomerCOSTUMER_ID, DELIVERY_DATE, ORDER_DATE, STATUS) VALUES ("
                    + columns[0] + ", " + columns[1] + ", TO_DATE('" + columns[2] + "', 'dd/mm/yyyy'), TO_DATE('"
                    + columns[3] + "', 'dd/mm/yyyy'), '" + columns[4] + "');";
            System.out.println(sql);
        });
    }

    private static void orderProducts() {
        processCSV(ORDER_PRODUCTS, "--Inserts for order_products", columns -> {
            String sql = "INSERT INTO Order_Products (OrderORDER_ID, ProductProduct_ID, AMOUNT_PRODUCT) VALUES ("
                    + columns[0] + ", '" + columns[1] + "', " + columns[2] + ");";
            System.out.println(sql);
        });
    }

    private static void part() {
        processCSV(PART, "--Inserts for part", columns -> {
            String sql = "INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('"
                    + columns[0] + "', '" + columns[1] + "', '" + columns[2] + "');";
            System.out.println(sql);
        });
    }

    private static void prodFamily() {
        processCSV(PROD_FAMILY, "--Inserts for prod_family", columns -> {
            String sql = "INSERT INTO Prod_Family (FAMILY_ID, NAME) VALUES ("
                    + columns[0] + ", '" + columns[1] + "');";
            System.out.println(sql);
        });
    }

    private static void product() {
        processCSV(PRODUCT, "--Inserts for product", columns -> {
            String sql = "INSERT INTO Product (Product_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION) VALUES ('"
                    + columns[0] + "', " + columns[1] + ", '" + columns[2] + "', '" + columns[3] + "');";
            System.out.println(sql);
        });
    }

    private static void rawMaterial() {
        processCSV(RAW_MATERIAL, "--Inserts for raw_material", columns -> {
            String sql = "INSERT INTO Raw_Material (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('"
                    + columns[0] + "', " + columns[1] + ", " + columns[2] + ");";
            System.out.println(sql);
        });
    }

    private static void workStation() {
        processCSV(WORKSTATION, "--Inserts for work_station", columns -> {
            String sql = "INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES ("
                    + columns[0] + ", '" + columns[1] + "', '" + columns[2] + "', '" + columns[3] + "');";
            System.out.println(sql);
        });
    }

    private static void workstationType() {
        processCSV(WORKSTATION_TYPE, "--Inserts for workstation_type", columns -> {
            String sql = "INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('"
                    + columns[0] + "', '" + columns[1] + "', " + columns[2] + ", " + columns[3] + ");";
            System.out.println(sql);
        });
    }

    private static void workstationTypeOperationType() {
        processCSV(WORKSTATION_TYPE_OPERATION_TYPE, "--Inserts for workstation_type_operation_type", columns -> {
            String sql = "INSERT INTO Workstation_Type_Operation_Type (WS_TYPE_ID, Operation_TypeOPTYPE_ID) VALUES ('"
                    + columns[0] + "', " + columns[1] + ");";
            System.out.println(sql);
        });
    }

    private static void deactivatedCustomers() {
        processCSV(DEACTIVATED_CUSTOMERS, "--Inserts for deactivated_customers", columns -> {
            String sql = "INSERT INTO \"Deactivated Costumers\" (CostumerCOSTUMER_ID) VALUES (" + columns[0] + ");";
            System.out.println(sql);
        });
    }

    private static void partType() {
        processCSV(PART_TYPE, "--Inserts for part_type", columns -> {
            String sql = "INSERT INTO Part_Type (PART_TYPE) VALUES ('" + columns[0] + "');";
            System.out.println(sql);
        });
    }

    private static void productionOrder() {
        processCSV(PRODUCTION_ORDER, "--Inserts for production_order", columns -> {
            String sql = "INSERT INTO Production_Order (PO_ID, Order_ProducstOrderORDER_ID, Order_ProductsProductPRODUCT_ID) VALUES ("
                    + columns[0] + ", " + columns[1] + ", '" + columns[2] + "');";
            System.out.println(sql);
        });
    }

    private static void productionOrderWorkstation() {
        processCSV(PRODUCTION_ORDER_WORKSTATION, "--Inserts for production_order_workstation", columns -> {
            String sql = "INSERT INTO Production_Order_WorkStation (Production_OrderPO_ID, Work_StationWS_ID) VALUES ("
                    + columns[0] + ", " + columns[1] + ");";
            System.out.println(sql);
        });
    }

    private static void reserved() {
        processCSV(RESERVED, "--Inserts for reserved", columns -> {
            String sql = "INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('" + columns[0] + "', " + columns[1] + ");";
            System.out.println(sql);
        });
    }

    private static void supplier() {
        processCSV(SUPPLIER, "--Inserts for supplier", columns -> {
            String sql = "INSERT INTO Supplier (SupplierID, START_DATE, END_DATE, MIN_ORDER, MIN_COST) VALUES ("
                    + columns[0] + ", " + columns[1] + ", " + columns[2] + ", " + columns[3] + ", " + columns[4] + ");";
            System.out.println(sql);
        });
    }

    private static void supplierPart() {
        processCSV(SUPPLIER_PART, "--Inserts for supplier_part", columns -> {
            String sql = "INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES ("
                    + columns[0] + ", '" + columns[1] + "');";
            System.out.println(sql);
        });
    }


    private static void processCSV(String filePath, String comment, DataProcessor processor) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            System.out.println(comment);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(",");
                processor.process(columns);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private interface DataProcessor {
        void process(String[] columns);
    }
}
