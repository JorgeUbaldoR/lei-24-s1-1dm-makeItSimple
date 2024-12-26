# USBD28 - List of all reserved materials and components, their quantity and the ID of the supplier

### 1. User Story Description

>  As a Production Manager, I want to have a list of all reserved materials and components, their quantity and the ID of the supplier.



### 2. Resolution
>**AC1:** Minimum expected requirement: demonstrated with data imported from the
legacy system.
> 
>**AC2:** A function should return a cursor with all the product
parts and their quantity. The individual components should be included when a
part is a subproduct made at the factory

>This script defines a PL/SQL function called list_parts_used_product, which returns a cursor containing a list of parts used by a specific product, along with their quantities. The function accepts a product_id as input and opens a SYS_REFCURSOR to retrieve the relevant data.
>
>The first part of the query selects parts directly associated with the product by joining the BOO_INPUT and Operation tables. It sums the quantities for each part number and groups the results by part number.
>
>The second part of the query handles parts related to the product indirectly. It selects parts from the BOO_OUTPUT table, ensuring that parts are not linked directly to the product but are part of a more complex relationship. The query joins BOO_OUTPUT and BOO_INPUT and filters based on a subquery that identifies parts associated with the subproducts through specific operations. This query also sums the quantities and groups them by part number.

    SELECT 
        sp.SupplierSupplierID AS Supplier_ID,
        c.PartPARTNUMBER AS Part_ID,
        c.RESERVED AS Reserved_Quantity
    FROM
        Component c
    JOIN
        Supplier_Part sp ON c.PartPARTNUMBER = sp.PartPARTNUMBER
    WHERE
        c.RESERVED > 0
    UNION ALL
    SELECT
        sp.SupplierSupplierID AS Supplier_ID,
        rm.PartPARTNUMBER AS Part_ID,
        rm.RESERVED AS Reserved_Quantity
    FROM
        "Raw Material" rm
    JOIN
        Supplier_Part sp ON rm.PartPARTNUMBER = sp.PartPARTNUMBER
    WHERE
        rm.RESERVED > 0
    ORDER BY SUPPLIER_ID;



### 3. Resolution

>![Results](img/USBD28.png)

>[See results in a CSV file](csv_result/USBD28.csv)


