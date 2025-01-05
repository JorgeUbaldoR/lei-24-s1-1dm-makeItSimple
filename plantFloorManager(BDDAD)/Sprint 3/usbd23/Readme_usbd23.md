# USBD23 - Expected execution time of an operation not greater than the maximum execution time of every workstation type where it may be run

### 1. User Story Description

>   As a Production Manager, I want to make sure that the expected execution time of an operation is not greater than the maximum execution time of
every workstation type where it may be run. A trigger should be developed to
avoid this issue in both insert and update operations.



### 2. Resolution
>**AC1:** Minimum expected requirement: demonstrated with data imported from the
legacy system.
    
    --USBD25
    DECLARE
        operations SYS_REFCURSOR;
        products_ids SYS_REFCURSOR;
        op_id  Operation.OPERATION_ID%TYPE;
        p_id Product.Product_ID%TYPE;
    
        oi_cursor SYS_REFCURSOR;
        io_type VARCHAR2(255);  -- IO type (Input/Output)
        part VARCHAR2(255);  -- Part number
    
        is_empty BOOLEAN := FALSE;
        flag BOOLEAN := FALSE;
    BEGIN
        -- AS12945S22, AS12946S20
        products_ids := GetProductIDs('Teste');
    
        LOOP
            FETCH products_ids INTO p_id ;
    
            IF products_ids%NOTFOUND AND flag = FALSE THEN
                is_empty := TRUE;
            END IF;
    
            EXIT WHEN products_ids%NOTFOUND;
    
            operations := GetProductOperationIDs(p_id);
    
            flag := TRUE;
    
            LOOP
                FETCH operations INTO op_id ;
                EXIT WHEN operations%NOTFOUND;
                
                DBMS_OUTPUT.PUT_LINE('Operation ID: ' || op_id);
    
                oi_cursor := GetOperationInputOuput(op_id);
    
                LOOP
                    FETCH oi_cursor INTO io_type, part;
                    EXIT WHEN oi_cursor%NOTFOUND;
                    
                    DBMS_OUTPUT.PUT_LINE(io_type || ' - ' || part);
                END LOOP;
                CLOSE oi_cursor;
                DBMS_OUTPUT.PUT_LINE(CHR(10));
            END LOOP;
            CLOSE operations;
        END LOOP;
        CLOSE products_ids;
    
        IF is_empty = TRUE THEN
            DBMS_OUTPUT.PUT_LINE('Product not found' );
        END IF;
    END;
    /

    --USBD26
    DECLARE
        ops SYS_REFCURSOR;
        part_stock SYS_REFCURSOR;
        prod_ids SYS_REFCURSOR;
        op_id  BOO_INPUT.OperationOPERATION_ID%TYPE;
        p_id Product.Product_ID%TYPE;
        part_id Part.PARTNUMBER%TYPE;
        Quantity number;
        
        v_not_exists number;
        is_empty BOOLEAN := FALSE;
        flag BOOLEAN := FALSE;
    BEGIN
        --AS12945S22, AS12946S20
        prod_ids := GetProductIDs('teste');
    
        LOOP
            FETCH prod_ids INTO p_id ;
    
            IF prod_ids%NOTFOUND AND flag = FALSE THEN
                is_empty := TRUE;
                v_not_exists := 1;
            END IF;
    
            EXIT WHEN prod_ids%NOTFOUND;
            
            ops := GetProductOperationIDs(p_id);
    
            flag := TRUE;
    
            LOOP
                FETCH ops INTO op_id ;
                EXIT WHEN ops%NOTFOUND;
    
                part_stock := GetProductParts(op_id);
    
                LOOP
                    FETCH part_stock INTO part_id, Quantity ;
                    EXIT WHEN part_stock%NOTFOUND;
    
                    IF NOT CheckStock(part_id, Quantity) THEN
                        v_not_exists := 1;
                    END IF;
    
                    IF v_not_exists = 1 THEN
                        EXIT;
                    END IF;
    
                END LOOP;
                CLOSE part_stock;
                IF v_not_exists = 1 THEN
                    EXIT;
                END IF;
    
            END LOOP;
            CLOSE ops;
            IF v_not_exists = 1 THEN
                EXIT;
            END IF;
    
        END LOOP;
        CLOSE prod_ids;
    
        IF v_not_exists != 0 THEN
            DBMS_OUTPUT.PUT_LINE('Cannot fulfill the order' );
        ELSE
            DBMS_OUTPUT.PUT_LINE('Can fulfill the order' );
        END IF;
    
        IF is_empty = TRUE THEN
            DBMS_OUTPUT.PUT_LINE('Product not found' );
        END IF;
    END;
    /

    --USBD27
    DECLARE
        order_product_ids SYS_REFCURSOR;
        operations SYS_REFCURSOR;
        part_stock SYS_REFCURSOR;
        products_ids SYS_REFCURSOR;
    
        o_p_id Product.Product_ID%TYPE;
        op_id  BOO_INPUT.OperationOPERATION_ID%TYPE;
        p_id Product.Product_ID%TYPE;
        part_id Part.PARTNUMBER%TYPE;
        Quantity number(10);
    
        v_not_exists number;
        is_empty_order BOOLEAN := FALSE;
        flag BOOLEAN := FALSE;
    BEGIN
        EXECUTE IMMEDIATE 'SET TRANSACTION ISOLATION LEVEL SERIALIZABLE';
    
        order_product_ids := GetOrderProducts(9);
    
        LOOP 
            FETCH order_product_ids INTO o_p_id ;
    
            IF order_product_ids%NOTFOUND AND flag = FALSE THEN
                is_empty_order := TRUE;
                v_not_exists := 1;
            END IF;
    
            EXIT WHEN order_product_ids%NOTFOUND;
    
    
            products_ids := GetProductIDs(o_p_id);
            
            flag := TRUE;
    
            LOOP
                FETCH products_ids INTO p_id ;
                EXIT WHEN products_ids%NOTFOUND;
    
                operations := GetProductOperationIDs(p_id);
            
                LOOP
                    FETCH operations INTO op_id ;
                    EXIT WHEN operations%NOTFOUND;
            
                    part_stock := GetProductParts(op_id);
            
                    LOOP
                        FETCH part_stock INTO part_id, Quantity ;
                        EXIT WHEN part_stock%NOTFOUND;
            
                        IF NOT UpdateReserved(part_id, Quantity) THEN
                            v_not_exists := 1;
                        END IF;
            
                        IF v_not_exists = 1 THEN
                            EXIT;
                        END IF;
                    END LOOP;
                    CLOSE part_stock;
                        
                    IF v_not_exists = 1 THEN
                        EXIT;
                    END IF;
            
                END LOOP;
                CLOSE operations;
                IF v_not_exists = 1 THEN
                    EXIT;
                END IF;
                
            END LOOP;
            CLOSE products_ids;
        END LOOP;
        CLOSE order_product_ids;
    
        IF v_not_exists != 0 THEN
            DBMS_OUTPUT.PUT_LINE('Cannot fulfill the order' );
            ROLLBACK;
        ELSE
            DBMS_OUTPUT.PUT_LINE('Can fulfill the order' );
            COMMIT;
        END IF;
        
        IF is_empty_order = TRUE THEN
            DBMS_OUTPUT.PUT_LINE('Order not found' );
        END IF;
    END;
    /

    UPDATE Reserved
    SET RESERVED = 15
    WHERE PartPARTNUMBER = 'PN18544A21';

    DECLARE
        order_product_ids SYS_REFCURSOR;
        operations SYS_REFCURSOR;
        part_stock SYS_REFCURSOR;
        products_ids SYS_REFCURSOR;
    
        o_p_id Product.Product_ID%TYPE;
        op_id  BOO_INPUT.OperationOPERATION_ID%TYPE;
        p_id Product.Product_ID%TYPE;
        part_id Part.PARTNUMBER%TYPE;
        Quantity number(10);
    
        v_not_exists number;
        is_empty_order BOOLEAN := FALSE;
        flag BOOLEAN := FALSE;
    BEGIN
        EXECUTE IMMEDIATE 'SET TRANSACTION ISOLATION LEVEL SERIALIZABLE';
    
        order_product_ids := GetOrderProducts(5);
    
        LOOP 
            FETCH order_product_ids INTO o_p_id ;
    
            IF order_product_ids%NOTFOUND AND flag = FALSE THEN
                is_empty_order := TRUE;
                v_not_exists := 1;
            END IF;
    
            EXIT WHEN order_product_ids%NOTFOUND;
    
    
            products_ids := GetProductIDs(o_p_id);
            
            flag := TRUE;
    
            LOOP
                FETCH products_ids INTO p_id ;
                EXIT WHEN products_ids%NOTFOUND;
    
                operations := GetProductOperationIDs(p_id);
            
                LOOP
                    FETCH operations INTO op_id ;
                    EXIT WHEN operations%NOTFOUND;
            
                    part_stock := GetProductParts(op_id);
            
                    LOOP
                        FETCH part_stock INTO part_id, Quantity ;
                        EXIT WHEN part_stock%NOTFOUND;
            
                        IF NOT UpdateReserved(part_id, Quantity) THEN
                            v_not_exists := 1;
                        END IF;
            
                        IF v_not_exists = 1 THEN
                            EXIT;
                        END IF;
                    END LOOP;
                    CLOSE part_stock;
                        
                    IF v_not_exists = 1 THEN
                        EXIT;
                    END IF;
            
                END LOOP;
                CLOSE operations;
                IF v_not_exists = 1 THEN
                    EXIT;
                END IF;
                
            END LOOP;
            CLOSE products_ids;
        END LOOP;
        CLOSE order_product_ids;
    
        IF v_not_exists != 0 THEN
            DBMS_OUTPUT.PUT_LINE('Cannot fulfill the order' );
            ROLLBACK;
        ELSE
            DBMS_OUTPUT.PUT_LINE('Can fulfill the order' );
            COMMIT;
        END IF;
        
        IF is_empty_order = TRUE THEN
            DBMS_OUTPUT.PUT_LINE('Order not found' );
        END IF;
    END;
    /


    --USBD30
    DECLARE
        result VARCHAR2(100);
    BEGIN
        --PN12344A21 50 10 5
        result := update_stock('PN12344A21', 40);
        DBMS_OUTPUT.PUT_LINE(result);
    END;
    /

    DECLARE
        result VARCHAR2(100);
    BEGIN
        --PN12344A21 50 10 5
        result := update_stock('TESTE', 20);
        DBMS_OUTPUT.PUT_LINE(result);
    END;
    /

    DECLARE
        result VARCHAR2(100);
    BEGIN
        --PN12344A21 50 10 5
        result := update_stock('IP12945A34', 20);
        DBMS_OUTPUT.PUT_LINE(result);
    END;
    /


