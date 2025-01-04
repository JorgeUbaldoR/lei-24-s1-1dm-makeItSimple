# USBD23 - Thoroughly test of the code

### 1. User Story Description

>  As a Production Manager, I want to get the list of parts used in a product.


### 2. Resolution
>**AC1:** Minimum expected requirement: demonstrated with data imported from the
legacy system.
> 
>**AC2:** The Product Owner will supply some testing data. Other may
have to be created by the team.

>This script defines a PL/SQL function called list_parts_used_product, which returns a cursor containing a list of parts used by a specific product, along with their quantities. The function accepts a product_id as input and opens a SYS_REFCURSOR to retrieve the relevant data.
>
>The first part of the query selects parts directly associated with the product by joining the BOO_INPUT and Operation tables. It sums the quantities for each part number and groups the results by part number.
>
>The second part of the query handles parts related to the product indirectly. It selects parts from the BOO_OUTPUT table, ensuring that parts are not linked directly to the product but are part of a more complex relationship. The query joins BOO_OUTPUT and BOO_INPUT and filters based on a subquery that identifies parts associated with the subproducts through specific operations. This query also sums the quantities and groups them by part number.

    CREATE OR REPLACE FUNCTION list_parts_used_product(
        product_id IN Operation.BOOProductProduct_ID%TYPE
    )
    RETURN SYS_REFCURSOR
    IS
        list_parts_cursor SYS_REFCURSOR;
    BEGIN
        OPEN list_parts_cursor FOR
            -- Base case: Select parts directly associated with the product
            SELECT BI.PartPARTNUMBER, SUM(BI.QUANTITY) AS QUANTITY
            FROM BOO_INPUT BI
            JOIN Operation O ON BI.OperationOPERATION_ID = O.OPERATION_ID
            WHERE O.BOOProductProduct_ID = product_id
            GROUP BY BI.PartPARTNUMBER
    
            UNION ALL
    
            SELECT BO.PartPARTNUMBER, SUM(BO.QUANTITY) AS QUANTITY
            FROM BOO_OUTPUT BO
            JOIN Operation O ON BO.OperationOPERATION_ID = O.OPERATION_ID
            JOIN BOO_INPUT BI ON BI.OperationOPERATION_ID = BO.OperationOPERATION_ID
            WHERE BO.PartPARTNUMBER NOT LIKE O.BOOProductProduct_ID
                AND O.BOOProductProduct_ID IN (
                    SELECT BI.PartPARTNUMBER
                    FROM BOO_INPUT BI
                    JOIN Part P ON BI.PartPARTNUMBER = P.PARTNUMBER
                    JOIN Operation O ON BI.OperationOPERATION_ID = O.OPERATION_ID
                    WHERE O.BOOProductProduct_ID = product_id and P.TYPE LIKE 'Product'
                    GROUP BY BI.PartPARTNUMBER
                ) 
            GROUP BY BO.PartPARTNUMBER;
    
        RETURN list_parts_cursor;
    END;
    /

    DECLARE
        parts_cursor	SYS_REFCURSOR;
        part_number	BOO_INPUT.PartPARTNUMBER%TYPE;
        quantity	BOO_INPUT.QUANTITY%TYPE;
    BEGIN
        -- Call the function with product 'AS12945S22'
        parts_cursor := list_parts_used_product('AS12945S22');
    
        -- Loop through the result set and display parts and quantities
        LOOP
            FETCH parts_cursor INTO part_number, quantity;
            EXIT WHEN parts_cursor%NOTFOUND;
            
            DBMS_OUTPUT.PUT_LINE('Part Number: ' || part_number || ', Quantity: ' || quantity);
        END LOOP;
        
        -- Close the cursor after processing
        CLOSE parts_cursor;
    END;
    /


### 3. Resolution

>![Results](img/USBD22.png)

>[See results in a CSV file](csv_result/USBD22.csv)


