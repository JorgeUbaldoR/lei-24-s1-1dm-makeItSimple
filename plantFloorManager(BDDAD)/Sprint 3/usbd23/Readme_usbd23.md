# USBD24 - Expected execution time of an operation not greater than the maximum execution time of every workstation type where it may be run

### 1. User Story Description

>  As a Production Manager, I want to make sure that the expected execution time of an operation is not greater than the maximum execution time of every workstation type where it may be run. A trigger should be developed to avoid this issue in both insert and update operations.


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

    CREATE OR REPLACE TRIGGER validate_expected_time
    BEFORE INSERT OR UPDATE ON Operation
    FOR EACH ROW
    DECLARE
        max_time NUMBER;
    BEGIN
        SELECT MAX_EXECUTIONTIME
        INTO max_time
        FROM Workstation_Type Wt
        JOIN Workstation_Type_Operation_TYPE WtOt ON WtOt.Workstation_TypeWS_TYPE_ID = Wt.WS_TYPE_ID
        WHERE Operation_TYPEOPTYPE_ID = :NEW.Operation_TYPEOPTYPE_ID;

        IF :NEW.EXPECTEDTIME > max_time THEN
            RAISE_APPLICATION_ERROR(
                -20001,
                'Expected execution time exceeds the maximum execution time for the operation type.'
            );
        END IF;
    END;
    /
    
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (9999, 'Teflon painting', 3300, 'AS99999S99', 5671, NULL);


### 3. Resolution

>![Results](img/USBD23.png)

>[See results in a CSV file](csv_result/USBD23.csv)


