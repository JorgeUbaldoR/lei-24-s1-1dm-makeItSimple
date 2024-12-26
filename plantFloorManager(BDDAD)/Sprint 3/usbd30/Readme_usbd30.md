# USBD30 - Use/consume a material/component and deduct the given amount from the stock

### 1. User Story Description

>  As a Factory Manager, I want to use/consume a material/component, i.e. to deduct a given amount from the stock. The operation should not be allowed if the remaining stock falls below the currently reserved quantity.

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

    CREATE OR REPLACE FUNCTION update_stock (
        p_PartPARTNUMBER IN Part.PARTNUMBER%TYPE,
        p_Amount IN NUMBER) 
    RETURN VARCHAR2
    IS
        v_Part_Type VARCHAR2(50);
    BEGIN
        EXECUTE IMMEDIATE 'SET TRANSACTION ISOLATION LEVEL SERIALIZABLE';

        BEGIN
            SELECT Part_TypePART_TYPE
            INTO v_Part_Type
            FROM Part
            WHERE PARTNUMBER = p_PartPARTNUMBER;

            IF v_Part_Type = 'Component' THEN
                UPDATE Component
                SET STOCK = STOCK - p_Amount
                WHERE PartPARTNUMBER = p_PartPARTNUMBER
                AND STOCK - p_Amount >= (RESERVED+MIN_STOCK);

                IF SQL%ROWCOUNT = 0 THEN
                    RAISE_APPLICATION_ERROR(-20001, 'Insufficient stock to complete the operation.');
                END IF;

            ELSIF v_Part_Type = 'Raw Material' THEN
                UPDATE "Raw Material"
                SET STOCK = STOCK - p_Amount
                WHERE PartPARTNUMBER = p_PartPARTNUMBER
                AND STOCK - p_Amount >= (RESERVED+MIN_STOCK);

                IF SQL%ROWCOUNT = 0 THEN
                    RAISE_APPLICATION_ERROR(-20001, 'Insufficient stock to complete the operation.');
                END IF;

            ELSE
                RAISE_APPLICATION_ERROR(-20002, 'The following part was not a component or material');
            END IF;
        END;
        COMMIT;

        RETURN 'Stock update successful';

        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                RETURN 'Error: ' || SQLERRM;
    END;
    /
    
    
    DECLARE
        result VARCHAR2(100);
    BEGIN
        --PN12344A21 50 10 5
        result := update_stock('PN12344A21', 40);
        DBMS_OUTPUT.PUT_LINE(result);
    END;
    /


### 3. Resolution

>![Results](img/USBD30.png)

>[See results in a CSV file](csv_result/USBD30.csv)


