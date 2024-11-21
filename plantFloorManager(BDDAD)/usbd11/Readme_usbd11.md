# USBD11 -  Insert the provided data into the system

### 1. User Story Description

> As a Product Owner, I want to insert the provided data into the system.


### 2. Customer Specifications and Clarifications

> **Question nº1:** Should we be capable of exporting the DB contents to excel/spreadsheet? And i do not understand with "Automatic generation of SQL input code from the spreadsheet", are we supposed to import data from or to a spreadsheet?
>
> **Answer:**
This user story covers an ETL process.
You will be provided a spreadsheet with data from a legacy system, and you will have to import that data into the new database model you are developing. The data will not be normalized and will not fit directly into the new model. You will be required to process that data in order to be able to load it into the new system.


### 3. Acceptance Criteria

>* **AC1:** Minimum expected requirement: manual creation of the data input scripts.

    -- Costumer
    INSERT INTO Costumer (COSTUMER_ID, VAT, NAME, ADDRESS, CITY, COUNTRY, ZIP, PHONE, EMAIL)
    VALUES (456, 'PT501245987', 'Carvalho & Carvalho, Lda', 'Tv. Augusto Lessa 23', 'Porto', 'Portugal', '4200-047', 3518340500, 'idont@care.com');
    INSERT INTO Costumer (COSTUMER_ID, VAT, NAME, ADDRESS, CITY, COUNTRY, ZIP, PHONE, EMAIL)
    VALUES (785, 'PT501245488', 'Tudo para a Casa, Lda', 'R. Dr. Barros 93', 'São Mamede de Infesta', 'Portugal', '4465-219', 3518340500, 'me@neither.com');
    INSERT INTO Costumer (COSTUMER_ID, VAT, NAME, ADDRESS, CITY, COUNTRY, ZIP, PHONE, EMAIL)
    VALUES (657, 'PT501242417', 'Sair de Cena', 'Edifício Cristal lj18, R. António Correia de Carvalho 88', 'Vila Nova de Gaia', 'Portugal', '4400-023', 3518340500, 'some@email.com');
    INSERT INTO Costumer (COSTUMER_ID, VAT, NAME, ADDRESS, CITY, COUNTRY, ZIP, PHONE, EMAIL)
    VALUES (348, 'CZ6451237810', 'U Fleku', 'Křemencova 11', 'Nové Město', 'Czechia', '110 00', 4201234567, 'some.random@email.cz');
    
    -- Product Family
    INSERT INTO Prod_Family (FAMILY_ID, NAME)
    VALUES (125, 'Pro Line Pots');
    INSERT INTO Prod_Family (FAMILY_ID, NAME)
    VALUES (130, 'La Belle Pots');
    INSERT INTO Prod_Family (FAMILY_ID, NAME)
    VALUES (132, 'Pro Line Pans');
    INSERT INTO Prod_Family (FAMILY_ID, NAME)
    VALUES (145, 'Pro Line Lids');
    INSERT INTO Prod_Family (FAMILY_ID, NAME)
    VALUES (146, 'Pro Clear Lids');
    
    -- Part
    -- Component Parts
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN12344A21', 'Screw M6 35 mm', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN52384R50', '300x300 mm 5 mm stainless steel sheet', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN52384R10', '300x300 mm 1 mm stainless steel sheet', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN18544A21', 'Rivet 6 mm', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN18544C21', 'Stainless steel handle model U6', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN18324C54', 'Stainless steel handle model R12', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN52384R45', '250x250 mm 5mm stainless steel sheet', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN52384R12', '250x250 mm 1mm stainless steel sheet', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN18324C91', 'Stainless steel handle model S26', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN18324C51', 'Stainless steel handle model R11', 'Component');
    -- Product Parts
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12945T22', '5l 22 cm aluminium and teflon non stick pot', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12945S22', '5l 22 cm stainless steel pot', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12946S22', '5l 22 cm stainless steel pot bottom', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12947S22', '22 cm stainless steel lid', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12945S20', '3l 20 cm stainless steel pot', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12946S20', '3l 20 cm stainless steel pot bottom', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12947S20', '20 cm stainless steel lid', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12945S17', '2l 17 cm stainless steel pot', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12945P17', '2l 17 cm stainless steel sauce pan', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12945S48', '17 cm stainless steel lid', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('AS12945G48', '17 cm glass lid', 'Product');
    -- Intermediate Product Parts
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12945A01', '250 mm 5 mm stainless steel disc', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12945A02', '220 mm pot base phase 1', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12945A03', '220 mm pot base phase 2', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12945A04', '220 mm pot base final', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12947A01', '250 mm 1 mm stainless steel disc', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12947A02', '220 mm lid pressed', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12947A03', '220 mm lid polished', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12947A04', '220 mm lid with handle', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12945A32', '200 mm pot base phase 1', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12945A33', '200 mm pot base phase 2', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12945A34', '200 mm pot base final', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12947A32', '200 mm lid pressed', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12947A33', '200 mm lid polished', 'Intermediate Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('IP12947A34', '200 mm lid with handle', 'Intermediate Product');
    -- Raw Material Parts
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('PN94561L67', 'Coolube 2210XP', 'Raw Material');
    
    
    -- Product
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12945T22', 130, 'La Belle 22 5L Pot', '5L 22 cm aluminium and Teflon non-stick pot');
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12945S22', 125, 'Pro 22 5L Pot', '5L 22 cm stainless steel pot');
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12946S22', 125, 'Pro 22 5L Pot Bottom', '5L 22 cm stainless steel pot bottom');
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12947S22', 145, 'Pro 22 Lid', '22 cm stainless steel lid');
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12945S20', 125, 'Pro 20 3L Pot', '3L 20 cm stainless steel pot');
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12946S20', 125, 'Pro 20 3L Pot Bottom', '3L 20 cm stainless steel pot bottom');
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12947S20', 145, 'Pro 20 Lid', '20 cm stainless steel lid');
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12945S17', 125, 'Pro 17 2L Pot', '2L 17 cm stainless steel pot');
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12945P17', 132, 'Pro 17 2L Sauce Pan', '2L 17 cm stainless steel sauce pan');
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12945S48', 145, 'Pro 17 Lid', '17 cm stainless steel lid');
    INSERT INTO Product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION)
    VALUES ('AS12945G48', 146, 'Pro Clear 17 Lid', '17 cm glass lid');
    
    
    -- Order
    INSERT INTO "Order" (ORDER_ID, CostumerCOSTUMER_ID, ORDER_DATE, DELIVERY_DATE, STATUS)
    VALUES (1, 785, TO_DATE('15/09/2024', 'DD/MM/YYYY'), TO_DATE('23/09/2024', 'DD/MM/YYYY'), 'Pending');
    INSERT INTO "Order" (ORDER_ID, CostumerCOSTUMER_ID, ORDER_DATE, DELIVERY_DATE, STATUS)
    VALUES (2, 657, TO_DATE('15/09/2024', 'DD/MM/YYYY'), TO_DATE('26/09/2024', 'DD/MM/YYYY'), 'Shipped');
    INSERT INTO "Order" (ORDER_ID, CostumerCOSTUMER_ID, ORDER_DATE, DELIVERY_DATE, STATUS)
    VALUES (3, 348, TO_DATE('15/09/2024', 'DD/MM/YYYY'), TO_DATE('25/09/2024', 'DD/MM/YYYY'), 'Delivered');
    INSERT INTO "Order" (ORDER_ID, CostumerCOSTUMER_ID, ORDER_DATE, DELIVERY_DATE, STATUS)
    VALUES (4, 785, TO_DATE('18/09/2024', 'DD/MM/YYYY'), TO_DATE('25/09/2024', 'DD/MM/YYYY'), 'Pending');
    INSERT INTO "Order" (ORDER_ID, CostumerCOSTUMER_ID, ORDER_DATE, DELIVERY_DATE, STATUS)
    VALUES (5, 657, TO_DATE('18/09/2024', 'DD/MM/YYYY'), TO_DATE('25/09/2024', 'DD/MM/YYYY'), 'Shipped');
    INSERT INTO "Order" (ORDER_ID, CostumerCOSTUMER_ID, ORDER_DATE, DELIVERY_DATE, STATUS)
    VALUES (6, 348, TO_DATE('18/09/2024', 'DD/MM/YYYY'), TO_DATE('26/09/2024', 'DD/MM/YYYY'), 'Delivered');
    INSERT INTO "Order" (ORDER_ID, CostumerCOSTUMER_ID, ORDER_DATE, DELIVERY_DATE, STATUS)
    VALUES (7, 456, TO_DATE('21/09/2024', 'DD/MM/YYYY'), TO_DATE('26/09/2024', 'DD/MM/YYYY'), 'Shipped');
    
    
    -- Order Products
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (1, 'AS12945S22', 5);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (1, 'AS12945S20', 15);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (2, 'AS12945S22', 10);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (2, 'AS12945P17', 20);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (3, 'AS12945S22', 10);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (3, 'AS12945S20', 10);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (4, 'AS12945S20', 24);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (4, 'AS12945S22', 16);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (4, 'AS12945S17', 8);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (5, 'AS12945S22', 12);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (6, 'AS12945S17', 8);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (6, 'AS12945P17', 16);
    INSERT INTO Order_Products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT)
    VALUES (7, 'AS12945S22', 8);
    
    
    -- Operation_TYPE
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5647, 'Disc cutting');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5649, 'Initial pot base pressing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5651, 'Final pot base pressing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5653, 'Pot base finishing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5655, 'Lid pressing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5657, 'Lid finishing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5659, 'Pot handles riveting');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5661, 'Lid handle screw');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5663, 'Pot test and packaging');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5665, 'Handle welding');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5667, 'Lid polishing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5669, 'Pot base polishing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5671, 'Teflon painting');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5681, 'Initial pan base pressing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5682, 'Final pan base pressing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5683, 'Pan base finishing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5685, 'Handle gluing');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5688, 'Pan test and packaging');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5672, 'Operation type 5672 description');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5673, 'Operation type 5673 description');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5674, 'Operation type 5674 description');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5675, 'Operation type 5675 description');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5684, 'Operation type 5684 description');
    INSERT INTO Operation_TYPE (OPTYPE_ID, DESCRIPTION) VALUES (5686, 'Operation type 5686 description');
    
    
    -- BOO_Template
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5647, 1);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5647, 2);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5649, 3);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5651, 4);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5653, 5);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5659, 6);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5669, 7);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5655, 8);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5657, 9);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5661, 10);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5667, 11);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (125, 5663, 12);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (132, 5681, 1);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (132, 5682, 2);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (132, 5683, 3);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (132, 5665, 4);
    INSERT INTO BOO_Template (Prod_FamilyFAMILY_ID, OperationOPERATION_ID, OPNUMBER)VALUES (132, 5688, 5);
    
    
    -- Workstation_Type
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('A4578', '600t cold forging stamping press');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('A4588', '600t cold forging precision stamping press');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('A4598', '1000t cold forging precision stamping press');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('S3271', 'Handle rivet');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('K3675', 'Packaging');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('K3676', 'Packaging for large items');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('C5637', 'Border trimming');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('D9123', 'Spot welding');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('Q5478', 'Teflon application station');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('Q3547', 'Stainless steel polishing');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('T3452', 'Assembly T1');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('G9273', 'Circular glass cutting');
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME) VALUES ('G9274', 'Glass trimming');
    
    
    -- Work_Station
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (9875, 'A4578', 'Press 01', '220-630t cold forging press');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (9886, 'A4578', 'Press 02', '220-630t cold forging press');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (9847, 'A4588', 'Press 03', '220-630t precision cold forging press');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (9855, 'A4588', 'Press 04', '160-1000t precision cold forging press');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (8541, 'S3271', 'Rivet 02', 'Rivet station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (8543, 'S3271', 'Rivet 03', 'Rivet station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (6814, 'K3675', 'Packaging 01', 'Packaging station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (6815, 'K3675', 'Packaging 02', 'Packaging station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (6816, 'K3675', 'Packaging 03', 'Packaging station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (6821, 'K3675', 'Packaging 04', 'Packaging station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (6822, 'K3676', 'Packaging 05', 'Packaging station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (8167, 'D9123', 'Welding 01', 'Spot welding station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (8170, 'D9123', 'Welding 02', 'Spot welding station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (8171, 'D9123', 'Welding 03', 'Spot welding station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (7235, 'T3452', 'Assembly 01', 'Product assembly station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (7236, 'T3452', 'Assembly 02', 'Product assembly station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (7238, 'T3452', 'Assembly 03', 'Product assembly station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (5124, 'C5637', 'Trimming 01', 'Metal trimming station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (4123, 'Q3547', 'Polishing 01', 'Metal polishing station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (4124, 'Q3547', 'Polishing 02', 'Metal polishing station');
    INSERT INTO Work_Station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES (4125, 'Q3547', 'Polishing 03', 'Metal polishing station');
    
    
    -- Workstation_Type_Operation_TYPE
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4578', 5647);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4588', 5647);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4598', 5647);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4588', 5649);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4598', 5649);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4588', 5651);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4598', 5651);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('C5637', 5653);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4588', 5655);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4598', 5655);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('C5637', 5657);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('S3271', 5659);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('T3452', 5661);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('K3675', 5663);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('D9123', 5665);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('Q3547', 5667);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('Q3547', 5669);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('Q5478', 5671);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4588', 5681);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4598', 5681);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4588', 5682);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('A4598', 5682);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('C5637', 5683);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('D9123', 5685);
    INSERT INTO Workstation_Type_Operation_TYPE (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('K3675', 5688);
    
    
    
    -- BOO
    INSERT INTO BOO (ProductPRODUCT_ID) VALUES ('AS12946S22');
    INSERT INTO BOO (ProductPRODUCT_ID) VALUES ('AS12947S22');
    INSERT INTO BOO (ProductPRODUCT_ID) VALUES ('AS12945S22');
    INSERT INTO BOO (ProductPRODUCT_ID) VALUES ('AS12946S20');
    INSERT INTO BOO (ProductPRODUCT_ID) VALUES ('AS12947S20');
    
    -- Component
    INSERT INTO Component (PartPARTNUMBER) VALUES ('PN12344A21');
    INSERT INTO Component (PartPARTNUMBER) VALUES ('PN52384R50');
    INSERT INTO Component (PartPARTNUMBER) VALUES ('PN52384R10');
    INSERT INTO Component (PartPARTNUMBER) VALUES ('PN18544A21');
    INSERT INTO Component (PartPARTNUMBER) VALUES ('PN18544C21');
    INSERT INTO Component (PartPARTNUMBER) VALUES ('PN18324C54');
    INSERT INTO Component (PartPARTNUMBER) VALUES ('PN52384R45');
    INSERT INTO Component (PartPARTNUMBER) VALUES ('PN52384R12');
    INSERT INTO Component (PartPARTNUMBER) VALUES ('PN18324C91');
    INSERT INTO Component (PartPARTNUMBER) VALUES ('PN18324C51');
    
    
    -- Intermediate Products
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12945A01');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12945A02');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12945A03');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12945A04');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12947A01');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12947A02');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12947A03');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12947A04');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12945A32');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12945A33');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12945A34');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12947A32');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12947A33');
    INSERT INTO "Intermediate Product" (PartPARTNUMBER) VALUES ('IP12947A34');
    
    
    -- Raw Materials
    INSERT INTO "Raw Material" (PartPARTNUMBER) VALUES ('PN94561L67');
    
    
    -- Operation
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (115, 'Pot handles riveting', 'AS12946S22', 5659, NULL);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (114, 'Pot base finishing', 'AS12946S22', 5653, 115);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (112, 'Final pot base pressing', 'AS12946S22', 5651, 114);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (103, 'Initial pot base pressing', 'AS12946S22', 5649, 112);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (100, 'Disc cutting', 'AS12946S22', 5647, 103);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (124, 'Handle welding', 'AS12947S22', 5667, NULL);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (123, 'Pot test and packaging', 'AS12947S22', 5661, 124);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (122, 'Lid handle screw', 'AS12947S22', 5657, 123);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (121, 'Lid finishing', 'AS12947S22', 5655, 122);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (120, 'Lid pressing', 'AS12947S22', 5647, 121);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (130, 'Pan test and packaging', 'AS12945S22', 5663, NULL);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (150, 'Initial assembly of product', 'AS12946S20', 5671, NULL);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (151, 'Pan base welding', 'AS12946S20', 5672, 150);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (152, 'Lid fitting', 'AS12946S20', 5673, 151);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (153, 'Handle attachment', 'AS12946S20', 5674, 152);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (154, 'Final quality check and packaging', 'AS12946S20', 5675, 153);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (160, 'Handle fitting', 'AS12947S20', 5681, NULL);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (161, 'Base welding', 'AS12947S20', 5682, 160);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (162, 'Lid creation and welding', 'AS12947S20', 5683, 161);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (163, 'Final product assembly', 'AS12947S20', 5684, 162);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (164, 'Product quality check and packaging', 'AS12947S20', 5685, NULL);
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (170, 'Initial assembly of product', 'AS12947S20', 5686, NULL);
    
    
    -- BOO_INPUT
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (100, 'PN52384R50', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (103, 'IP12945A01', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (103, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (112, 'IP12945A02', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (112, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (114, 'IP12945A03', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (115, 'IP12945A04', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (115, 'PN18544C21', 2, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (120, 'PN52384R10', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (121, 'IP12947A01', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (121, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (122, 'IP12947A02', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (123, 'IP12947A03', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (123, 'PN18324C54', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (124, 'IP12947A04', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (130, 'AS12946S22', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (130, 'AS12947S22', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (150, 'PN52384R50', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (151, 'IP12945A01', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (151, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (152, 'IP12945A32', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (152, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (153, 'IP12945A33', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (154, 'IP12945A34', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (154, 'PN18544C21', 2, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (160, 'PN52384R10', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (161, 'IP12947A01', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (161, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (162, 'IP12947A32', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (163, 'IP12947A33', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (163, 'PN18324C51', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (164, 'IP12947A34', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (170, 'AS12946S20', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (170, 'AS12947S20', 1, 'unit');
    
    -- BOO_OUTPUT
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (100, 'IP12945A01', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (103, 'IP12945A02', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (112, 'IP12945A03', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (114, 'IP12945A04', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (115, 'AS12946S22', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (120, 'IP12947A01', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (121, 'IP12947A02', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (122, 'IP12947A03', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (123, 'IP12947A04', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (124, 'AS12947S22', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (130, 'AS12945S22', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (150, 'IP12945A01', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (151, 'IP12945A32', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (152, 'IP12945A33', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (153, 'IP12945A34', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (154, 'AS12946S20', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (160, 'IP12947A01', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (161, 'IP12947A32', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (162, 'IP12947A33', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (163, 'IP12947A34', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (164, 'AS12947S20', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (170, 'AS12945S20', 1, 'unit');




>* **AC2:** Minimum requirement above the expected: automatic generation of SQL
  input code from the spreadsheet (e.g., Excel formulas, scripts in any other
  language, etc.).

```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SQLGenerator {
  /* EXEMPLE
  private static final String BOM = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/bom.csv";
  */
  private static final String COSTUMER = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/costumer.csv";
  private static final String ORDER = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/orders.csv";
  private static final String PROD_FAMILY = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/product_family.csv";
  private static final String PRODUCT = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/products.csv";
  private static final String PART = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/part.csv";
  private static final String ORDER_PRODUCTS = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/order_products.csv";
  private static final String WORKSTATION_TYPES = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/workstation_types.csv";
  private static final String WORKSTATIONS = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/workstation.csv";
  private static final String OPERATION = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/operations.csv";
  private static final String BOO = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/boo.csv";
  private static final String BOO_INPUT = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/boo_input.csv";
  private static final String BOO_OUTPUT = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/boo_output.csv";
  private static final String BOO_TEMPLATE = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/boo_template.csv";
  private static final String COMPONENT = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/component.csv";
  private static final String INTERMEDIATE_PRODUCT = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/intermediate_product.csv";
  private static final String OPERATION_TYPE = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/operation_type.csv";
  private static final String RAW_MATERIAL = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/raw_material.csv";
  private static final String WORKSTATION_TYPE_OPERATION_TYPE = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/data_base/workstation_type_operation.csv";

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
  }



  private static void boo() {
    processCSV(BOO, "--Inserts for boo", columns -> {
      String sql = "INSERT INTO boo (ProductPRODUCT_ID) VALUES ('"
              + columns[0] + "');";
      System.out.println(sql);
    });
  }

  private static void booInput() {
    processCSV(BOO_INPUT, "--Inserts for boo_input", columns -> {
      String sql = "INSERT INTO boo_input (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES ("
              + columns[0] + ", '" + columns[1] + "', " + columns[2] + ", '" + columns[3] + "');";
      System.out.println(sql);
    });
  }

  private static void booOutput() {
    processCSV(BOO_OUTPUT, "--Inserts for boo_output", columns -> {
      String sql = "INSERT INTO boo_output (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES ("
              + columns[0] + ", '" + columns[1] + "', " + columns[2] + ", '" + columns[3] + "');";
      System.out.println(sql);
    });
  }

  private static void booTemplate() {
    processCSV(BOO_TEMPLATE, "--Inserts for boo_template", columns -> {
      String sql = "INSERT INTO boo_template (OPNUMBER, OperationOPERATION_ID, Prod_FamilyFAMILY_ID) VALUES ("
              + columns[0] + ", " + columns[1] + ", " + columns[2] + ");";
      System.out.println(sql);
    });
  }

  private static void component() {
    processCSV(COMPONENT, "--Inserts for component", columns -> {
      String sql = "INSERT INTO component (PartPARTNUMBER) VALUES ('"
              + columns[0] + "');";
      System.out.println(sql);
    });
  }

  private static void costumer() {
    processCSV(COSTUMER, "--Inserts for costumer", columns -> {
      String sql = "INSERT INTO costumer (COSTUMER_ID, VAT, NAME, ADDRESS, CITY, COUNTRY, ZIP, PHONE, EMAIL) VALUES ("
              + columns[0] + ", '" + columns[1] + "', '" + columns[2] + "', '" + columns[3] + "', '"
              + columns[4] + "', '" + columns[5] + "', '" + columns[6] + "', " + columns[7] + ", '"
              + columns[8] + "');";
      System.out.println(sql);
    });
  }

  private static void intermediateProduct() {
    processCSV(INTERMEDIATE_PRODUCT, "--Inserts for intermediate product", columns -> {
      String sql = "INSERT INTO intermediate_product (PartPARTNUMBER) VALUES ('"
              + columns[0] + "');";
      System.out.println(sql);
    });
  }

  private static void operation() {
    processCSV(OPERATION, "--Inserts for operation", columns -> {
      String sql = "INSERT INTO operation (OPERATION_ID, DESCRIPTION, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP) VALUES ("
              + columns[0] + ", '" + columns[1] + "', '" + columns[2] + "', " + columns[3] + ", " + columns[4] + ");";
      System.out.println(sql);
    });
  }

  private static void operationType() {
    processCSV(OPERATION_TYPE, "--Inserts for operation_type", columns -> {
      String sql = "INSERT INTO operation_type (OPTYPE_ID, DESCRIPTION) VALUES ("
              + columns[0] + ", '" + columns[1] + "');";
      System.out.println(sql);
    });
  }

  private static void order() {
    processCSV(ORDER, "--Inserts for order", columns -> {
      String sql = "INSERT INTO \"Order\" (ORDER_ID, CostumerCOSTUMER_ID, DELIVERY_DATE, ORDER_DATE, STATUS) VALUES ("
              + columns[0] + ", " + columns[1] + ", TO_DATE('" + columns[2] + "', 'dd/mm/yyyy'), TO_DATE('"
              + columns[3] + "', 'dd/mm/yyyy') " + columns[4] + "');";
      System.out.println(sql);
    });
  }

  private static void orderProducts() {
    processCSV(ORDER_PRODUCTS, "--Inserts for order_products", columns -> {
      String sql = "INSERT INTO order_products (OrderORDER_ID, ProductPRODUCT_ID, AMOUNT_PRODUCT) VALUES ("
              + columns[0] + ", '" + columns[1] + "', " + columns[2] + ");";
      System.out.println(sql);
    });
  }

  private static void part() {
    processCSV(PART, "--Inserts for part", columns -> {
      String sql = "INSERT INTO part (PARTNUMBER, DESCRIPTION, TYPE) VALUES ('"
              + columns[0] + "', '" + columns[1] + "', '" + columns[2] + "');";
      System.out.println(sql);
    });
  }

  private static void prodFamily() {
    processCSV(PROD_FAMILY, "--Inserts for prod_family", columns -> {
      String sql = "INSERT INTO prod_family (FAMILY_ID, NAME) VALUES ("
              + columns[0] + ", '" + columns[1] + "');";
      System.out.println(sql);
    });
  }

  private static void product() {
    processCSV(PRODUCT, "--Inserts for product", columns -> {
      String sql = "INSERT INTO product (PRODUCT_ID, Prod_FamilyFAMILY_ID, NAME, DESCRIPTION) VALUES ('"
              + columns[0] + "', " + columns[1] + ", '" + columns[2] + "', '" + columns[3] + "');";
      System.out.println(sql);
    });
  }

  private static void rawMaterial() {
    processCSV(RAW_MATERIAL, "--Inserts for raw_material", columns -> {
      String sql = "INSERT INTO raw_material (PartPARTNUMBER) VALUES ('"
              + columns[0] + "');";
      System.out.println(sql);
    });
  }

  private static void workStation() {
    processCSV(WORKSTATIONS, "--Inserts for work_station", columns -> {
      String sql = "INSERT INTO work_station (WS_ID, Workstation_TypeWS_TYPE_ID, NAME, DESCRIPTION) VALUES ("
              + columns[0] + ", '" + columns[1] + "', '" + columns[2] + "', '" + columns[3] + "');";
      System.out.println(sql);
    });
  }

  private static void workstationType() {
    processCSV(WORKSTATION_TYPES, "--Inserts for workstation_type", columns -> {
      String sql = "INSERT INTO workstation_type (WS_TYPE_ID, NAME) VALUES ('"
              + columns[0] + "', '" + columns[1] + "');";
      System.out.println(sql);
    });
  }

  private static void workstationTypeOperationType() {
    processCSV(WORKSTATION_TYPE_OPERATION_TYPE, "--Inserts for workstation_type_operation_type", columns -> {
      String sql = "INSERT INTO workstation_type_operation_type (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID) VALUES ('"
              + columns[0] + "', " + columns[1] + ");";
      System.out.println(sql);
    });
  }


    /* EXEMPLO
    private static void bom() {
        processCSV(BOM, "--Inserts for bom", columns -> {
            String sql = "INSERT INTO bom (ProductPRODUCT_ID, PARTNUMBER, DESCRIPTION, QUANTITY) VALUES ('"
                    + columns[0] + "', '" + columns[1] + "', '" + columns[2] + "', " + columns[3] + ");";
            System.out.println(sql);
        });
    }*/

  private static void processCSV(String filePath, String header, CSVProcessor processor) {
    try {
      Scanner scanner = new Scanner(new File(filePath));
      if (scanner.hasNextLine()) scanner.nextLine(); // Skip header
      System.out.println(header);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] columns = line.split(";");
        processor.process(columns);
      }
      System.out.println();
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + e.getMessage());
    }
  }

  @FunctionalInterface
  private interface CSVProcessor {
    void process(String[] columns);
  }
}

```





