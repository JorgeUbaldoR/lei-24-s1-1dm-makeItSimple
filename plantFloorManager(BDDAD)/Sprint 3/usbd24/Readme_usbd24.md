# USBD24 - Add a product as an input in its own BOO

### 1. User Story Description

>  As a Production Manager, I don’t want it to be possible to add a product as an input in its own BOO. A trigger should be developed to avoid such circular references. This doesn’t apply to the BOOs of subproducts.



### 2. Resolution
>**AC1:** Minimum expected requirement: demonstrated with data imported from the
legacy system.
> 
>**AC2:**  A trigger should be developed to avoid such circular
references. This doesn’t apply to the BOOs of subproducts.


>This trigger is designed to prevent a product from being added as an input to its own Bill of Operations (BOO), which would create a logical inconsistency. It automatically executes before any new row is inserted or an existing row is updated in the BOO_INPUT table.
>
> When triggered, it retrieves the BOOProductProduct_ID from the Operation table, which represents the product associated with the operation in the new or updated record. The trigger then compares this product ID with the part number specified as an input in the same record.
>
>If the product ID matches the input part number, the trigger raises an application error with the message, "A product cannot be an input in its own BOO." This stops the operation from being completed, ensuring the data remains valid. If no match is found, the operation proceeds without interruption.

    CREATE OR REPLACE TRIGGER add_product_input_in_own_BOO
    BEFORE INSERT OR UPDATE ON BOO_INPUT
    FOR EACH ROW
    DECLARE
    ProductID Operation.BOOProductProduct_ID%TYPE;
    BEGIN
    SELECT BOOProductProduct_ID
    INTO ProductID
    FROM Operation O
    WHERE :NEW.OperationOPERATION_ID = O.OPERATION_ID;
    
        IF ProductID = :NEW.PartPARTNUMBER THEN
            RAISE_APPLICATION_ERROR(-20002,'A product cannot be an input in its own BOO.');
        END IF;
    END;
    /
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (100, 'AS12946S22', 1, 'unit');
    



### 3. Resolution

>![Results](img/USBD24.png)

>[See results in a CSV file](csv_result/USBD24.csv)


