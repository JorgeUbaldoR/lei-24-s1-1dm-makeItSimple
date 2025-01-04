# USBD21 -  Automatically generate the physical level SQL code of the database from the Visual Paradigm model

### 1. User Story Description

> As a Product Owner, I want to automatically generate the physical level SQL code of the database from the Visual Paradigm model.


### 2. Customer Specifications and Clarifications

> **Question nº1:** Should we be capable of exporting the DB contents to excel/spreadsheet? And i do not understand with "Automatic generation of SQL input code from the spreadsheet", are we supposed to import data from or to a spreadsheet?
>
> **Answer:**
This user story covers an ETL process.
You will be provided a spreadsheet with data from a legacy system, and you will have to import that data into the new database model you are developing. The data will not be normalized and will not fit directly into the new model. You will be required to process that data in order to be able to load it into the new system.


### 3. Acceptance Criteria

>* **AC1:** Minimum expected requirement: manual creation of the data input scripts.

    DROP TABLE BOO CASCADE CONSTRAINTS;
    DROP TABLE BOO_INPUT CASCADE CONSTRAINTS;
    DROP TABLE BOO_OUTPUT CASCADE CONSTRAINTS;
    DROP TABLE BOO_Template CASCADE CONSTRAINTS;
    DROP TABLE Component CASCADE CONSTRAINTS;
    DROP TABLE Costumer CASCADE CONSTRAINTS;
    DROP TABLE "Deactivated Costumers" CASCADE CONSTRAINTS;
    DROP TABLE Intermediate_Product CASCADE CONSTRAINTS;
    DROP TABLE Operation CASCADE CONSTRAINTS;
    DROP TABLE Operation_TYPE CASCADE CONSTRAINTS;
    DROP TABLE "Order" CASCADE CONSTRAINTS;
    DROP TABLE Order_Products CASCADE CONSTRAINTS;
    DROP TABLE Part CASCADE CONSTRAINTS;
    DROP TABLE Part_Type CASCADE CONSTRAINTS;
    DROP TABLE Prod_Family CASCADE CONSTRAINTS;
    DROP TABLE Product CASCADE CONSTRAINTS;
    DROP TABLE Production_Order CASCADE CONSTRAINTS;
    DROP TABLE Production_Order_WorkStation CASCADE CONSTRAINTS;
    DROP TABLE Raw_Material CASCADE CONSTRAINTS;
    DROP TABLE Reserved CASCADE CONSTRAINTS;
    DROP TABLE Supplier CASCADE CONSTRAINTS;
    DROP TABLE Supplier_Part CASCADE CONSTRAINTS;
    DROP TABLE Work_Station CASCADE CONSTRAINTS;
    DROP TABLE Workstation_Type CASCADE CONSTRAINTS;
    DROP TABLE Workstation_Type_Operation_TYPE CASCADE CONSTRAINTS;
    
    
    CREATE TABLE BOO (
        ProductProduct_ID varchar2(255) NOT NULL,
        PRIMARY KEY (ProductProduct_ID)
    );
    
    CREATE TABLE BOO_INPUT (
        OperationOPERATION_ID number(10) NOT NULL,
        PartPARTNUMBER varchar2(255) NOT NULL,
        QUANTITY number(10) NOT NULL,
        UNIT varchar2(255) NOT NULL,
        PRIMARY KEY (OperationOPERATION_ID, PartPARTNUMBER)
    );
    
    CREATE TABLE BOO_OUTPUT (
        OperationOPERATION_ID number(10) NOT NULL,
        PartPARTNUMBER varchar2(255) NOT NULL,
        QUANTITY number(10) NOT NULL,
        UNIT varchar2(255) NOT NULL,
        PRIMARY KEY (OperationOPERATION_ID, PartPARTNUMBER)
    );
    
    CREATE TABLE BOO_Template (
        OPNUMBER number(10) NOT NULL,
        OperationOPERATION_ID number(10) NOT NULL,
        Prod_FamilyFAMILY_ID number(10) NOT NULL,
        PRIMARY KEY (OPNUMBER, OperationOPERATION_ID, Prod_FamilyFAMILY_ID)
    );
    
    CREATE TABLE Component (
        PartPARTNUMBER varchar2(255) NOT NULL,
        STOCK number(38),
        MIN_STOCK number(38) NOT NULL,
        PRIMARY KEY (PartPARTNUMBER)
    );
    
    CREATE TABLE Costumer (
        COSTUMER_ID number(10) NOT NULL,
        VAT varchar2(100) NOT NULL,
        NAME varchar2(255) NOT NULL,
        ADDRESS varchar2(255) NOT NULL,
        CITY varchar2(70) NOT NULL,
        COUNTRY varchar2(70) NOT NULL,
        ZIP varchar2(20) NOT NULL,
        PHONE number(15) NOT NULL,
        EMAIL varchar2(100) NOT NULL,
        PRIMARY KEY (COSTUMER_ID)
    );
    
    CREATE TABLE "Deactivated Costumers" (
        CostumerCOSTUMER_ID number(10) NOT NULL,
        PRIMARY KEY (CostumerCOSTUMER_ID)
    );
    
    CREATE TABLE Intermediate_Product (
        PartPARTNUMBER varchar2(255) NOT NULL,
        STOCK number(38),
        MIN_STOCK number(38) NOT NULL,
        PRIMARY KEY (PartPARTNUMBER)
    );
    
    CREATE TABLE Operation (
        OPERATION_ID number(10) NOT NULL,
        DESCRIPTION varchar2(100) NOT NULL,
        EXPECTEDTIME number(10),
        Operation_TYPEOPTYPE_ID number(10) NOT NULL,
        BOOProductProduct_ID varchar2(255) NOT NULL,
        NEXTSTEP number(10),
        PRIMARY KEY (OPERATION_ID)
    );
    
    CREATE TABLE Operation_TYPE (
        OPTYPE_ID number(10) NOT NULL,
        DESCRIPTION varchar2(255) NOT NULL,
        PRIMARY KEY (OPTYPE_ID)
    );
    
    CREATE TABLE "Order" (
        ORDER_ID number(10) NOT NULL,
        CostumerCOSTUMER_ID number(10) NOT NULL,
        DELIVERY_DATE date NOT NULL,
        ORDER_DATE date NOT NULL,
        STATUS varchar2(255) NOT NULL,
        PRIMARY KEY (ORDER_ID)
    );
    
    CREATE TABLE Order_Products (
        OrderORDER_ID number(10) NOT NULL,
        ProductProduct_ID varchar2(255) NOT NULL,
        AMOUNT_PRODUCT number(10) NOT NULL,
        PRIMARY KEY (OrderORDER_ID, ProductProduct_ID)
    );
    
    CREATE TABLE Part (
        PARTNUMBER varchar2(255) NOT NULL,
        DESCRIPTION varchar2(255) NOT NULL,
        Part_TypePART_TYPE varchar2(50) NOT NULL,
        PRIMARY KEY (PARTNUMBER)
    );
    
    CREATE TABLE Part_Type (
        PART_TYPE varchar2(50) NOT NULL,
        PRIMARY KEY (PART_TYPE)
    );
    
    CREATE TABLE Prod_Family (
        FAMILY_ID number(10) NOT NULL,
        NAME varchar2(100) NOT NULL,
        PRIMARY KEY (FAMILY_ID)
    );
    
    CREATE TABLE Product (
        Product_ID varchar2(255) NOT NULL,
        Prod_FamilyFAMILY_ID number(10) NOT NULL,
        NAME varchar2(30) NOT NULL,
        DESCRIPTION varchar2(100) NOT NULL,
        PRIMARY KEY (Product_ID)
    );
    
    CREATE TABLE Production_Order (
        PO_ID number(10) NOT NULL,
        Order_ProducstOrderORDER_ID number(10) NOT NULL,
        Order_ProductsProductPRODUCT_ID varchar2(255) NOT NULL,
        PRIMARY KEY (PO_ID)
    );
    
    CREATE TABLE Production_Order_WorkStation (
        Production_OrderPO_ID number(10) NOT NULL,
        Work_StationWS_ID number(10) NOT NULL,
        PRIMARY KEY (Production_OrderPO_ID, Work_StationWS_ID)
    );
    
    CREATE TABLE Raw_Material (
        PartPARTNUMBER varchar2(255) NOT NULL,
        STOCK number(38),
        MIN_STOCK number(38) NOT NULL,
        PRIMARY KEY (PartPARTNUMBER)
    );
    
    CREATE TABLE Reserved (
        PartPARTNUMBER varchar2(255) NOT NULL,
        RESERVED number(38),
        PRIMARY KEY (PartPARTNUMBER)
    );
    
    CREATE TABLE Supplier (
        SupplierID number(10) NOT NULL,
        START_DATE number(10) NOT NULL,
        END_DATE number(10),
        MIN_ORDER number(10) NOT NULL,
        MIN_COST number(10) NOT NULL,
        PRIMARY KEY (SupplierID)
    );
    
    CREATE TABLE Supplier_Part (
        SupplierSupplierID number(10) NOT NULL,
        PartPARTNUMBER varchar2(255) NOT NULL,
        PRIMARY KEY (SupplierSupplierID, PartPARTNUMBER)
    );
    
    CREATE TABLE Work_Station (
        WS_ID number(10) NOT NULL,
        Workstation_TypeWS_TYPE_ID varchar2(100) NOT NULL,
        NAME varchar2(30) NOT NULL,
        DESCRIPTION varchar2(255) NOT NULL,
        PRIMARY KEY (WS_ID)
    );
    
    CREATE TABLE Workstation_Type (
        WS_TYPE_ID varchar2(100) NOT NULL,
        NAME varchar2(255) NOT NULL,
        MAX_EXECUTIONTIME number(10) NOT NULL,
        SETUP_TIME number(10),
        PRIMARY KEY (WS_TYPE_ID)
    );
    
    CREATE TABLE Workstation_Type_Operation_TYPE (
        Workstation_TypeWS_TYPE_ID varchar2(100) NOT NULL,
        Operation_TYPEOPTYPE_ID number(10) NOT NULL,
        PRIMARY KEY (Workstation_TypeWS_TYPE_ID, Operation_TYPEOPTYPE_ID)
    );
        
    
    
    ALTER TABLE "Order" ADD CONSTRAINT FKOrder227226 FOREIGN KEY (CostumerCOSTUMER_ID) REFERENCES Costumer (COSTUMER_ID);
    ALTER TABLE Order_Products ADD CONSTRAINT FKOrder_Prod1393 FOREIGN KEY (OrderORDER_ID) REFERENCES "Order" (ORDER_ID);
    ALTER TABLE Order_Products ADD CONSTRAINT FKOrder_Prod378230 FOREIGN KEY (ProductProduct_ID) REFERENCES Product (Product_ID);
    ALTER TABLE Product ADD CONSTRAINT FKProduct726914 FOREIGN KEY (Prod_FamilyFAMILY_ID) REFERENCES Prod_Family (FAMILY_ID);
    ALTER TABLE BOO_Template ADD CONSTRAINT FKBOO_Templa701380 FOREIGN KEY (Prod_FamilyFAMILY_ID) REFERENCES Prod_Family (FAMILY_ID);
    ALTER TABLE Work_Station ADD CONSTRAINT FKWork_Stati805971 FOREIGN KEY (Workstation_TypeWS_TYPE_ID) REFERENCES Workstation_Type (WS_TYPE_ID);
    ALTER TABLE Production_Order ADD CONSTRAINT FKProduction976348 FOREIGN KEY (Order_ProducstOrderORDER_ID, Order_ProductsProductPRODUCT_ID) REFERENCES Order_Products (OrderORDER_ID, ProductProduct_ID);
    ALTER TABLE Production_Order_WorkStation ADD CONSTRAINT FKProduction913909 FOREIGN KEY (Work_StationWS_ID) REFERENCES Work_Station (WS_ID);
    ALTER TABLE Production_Order_WorkStation ADD CONSTRAINT FKProduction656068 FOREIGN KEY (Production_OrderPO_ID) REFERENCES Production_Order (PO_ID);
    ALTER TABLE BOO ADD CONSTRAINT FKBOO714323 FOREIGN KEY (ProductProduct_ID) REFERENCES Product (Product_ID);
    ALTER TABLE BOO_INPUT ADD CONSTRAINT FKBOO_INPUT312560 FOREIGN KEY (PartPARTNUMBER) REFERENCES Part (PARTNUMBER);
    ALTER TABLE Operation ADD CONSTRAINT FKOperation993690 FOREIGN KEY (BOOProductProduct_ID) REFERENCES BOO (ProductProduct_ID);
    ALTER TABLE BOO_INPUT ADD CONSTRAINT FKBOO_INPUT741374 FOREIGN KEY (OperationOPERATION_ID) REFERENCES Operation (OPERATION_ID);
    ALTER TABLE BOO_OUTPUT ADD CONSTRAINT FKBOO_OUTPUT969254 FOREIGN KEY (OperationOPERATION_ID) REFERENCES Operation (OPERATION_ID);
    ALTER TABLE Operation ADD CONSTRAINT FKOperation405044 FOREIGN KEY (NEXTSTEP) REFERENCES Operation (OPERATION_ID);
    ALTER TABLE Workstation_Type_Operation_TYPE ADD CONSTRAINT FKWorkstatio943732 FOREIGN KEY (Workstation_TypeWS_TYPE_ID) REFERENCES Workstation_Type (WS_TYPE_ID);
    ALTER TABLE Workstation_Type_Operation_TYPE ADD CONSTRAINT FKWorkstatio342495 FOREIGN KEY (Operation_TYPEOPTYPE_ID) REFERENCES Operation_TYPE (OPTYPE_ID);
    ALTER TABLE Operation ADD CONSTRAINT FKOperation169101 FOREIGN KEY (Operation_TYPEOPTYPE_ID) REFERENCES Operation_TYPE (OPTYPE_ID);
    ALTER TABLE "Deactivated Costumers" ADD CONSTRAINT FKDeactivate25273 FOREIGN KEY (CostumerCOSTUMER_ID) REFERENCES Costumer (COSTUMER_ID);
    ALTER TABLE Intermediate_Product ADD CONSTRAINT FKIntermedia303416 FOREIGN KEY (PartPARTNUMBER) REFERENCES Part (PARTNUMBER);
    ALTER TABLE Raw_Material ADD CONSTRAINT "FKRaw Materi920417" FOREIGN KEY (PartPARTNUMBER) REFERENCES Part (PARTNUMBER);
    ALTER TABLE Component ADD CONSTRAINT FKComponent283794 FOREIGN KEY (PartPARTNUMBER) REFERENCES Part (PARTNUMBER);
    ALTER TABLE Product ADD CONSTRAINT FKProduct889007 FOREIGN KEY (Product_ID) REFERENCES Part (PARTNUMBER);
    ALTER TABLE BOO_OUTPUT ADD CONSTRAINT FKBOO_OUTPUT601930 FOREIGN KEY (PartPARTNUMBER) REFERENCES Part (PARTNUMBER);
    ALTER TABLE Supplier_Part ADD CONSTRAINT FKSupplier_P182195 FOREIGN KEY (SupplierSupplierID) REFERENCES Supplier (SupplierID);
    ALTER TABLE Supplier_Part ADD CONSTRAINT FKSupplier_P947172 FOREIGN KEY (PartPARTNUMBER) REFERENCES Part (PARTNUMBER);
    ALTER TABLE Part ADD CONSTRAINT FKPart81837 FOREIGN KEY (Part_TypePART_TYPE) REFERENCES Part_Type (PART_TYPE);
    ALTER TABLE Reserved ADD CONSTRAINT FKReserved480771 FOREIGN KEY (PartPARTNUMBER) REFERENCES Part (PARTNUMBER);






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
    
    
    -- Part_Type
    INSERT INTO Part_Type (PART_TYPE) VALUES ('Component');
    INSERT INTO Part_Type (PART_TYPE) VALUES ('Product');
    INSERT INTO Part_Type (PART_TYPE) VALUES ('Intermediate_Product');
    INSERT INTO Part_Type (PART_TYPE) VALUES ('Raw_Material');
    
    
    -- Part
    -- Component Parts
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN12344A21', 'Screw M6 35 mm', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN52384R50', '300x300 mm 5 mm stainless steel sheet', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN52384R10', '300x300 mm 1 mm stainless steel sheet', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN18544A21', 'Rivet 6 mm', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN18544C21', 'Stainless steel handle model U6', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN18324C54', 'Stainless steel handle model R12', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN52384R45', '250x250 mm 5mm stainless steel sheet', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN52384R12', '250x250 mm 1mm stainless steel sheet', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN18324C91', 'Stainless steel handle model S26', 'Component');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN18324C51', 'Stainless steel handle model R11', 'Component');
    -- Product Parts
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12945T22', '5l 22 cm aluminium and teflon non stick pot', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12945S22', '5l 22 cm stainless steel pot', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12946S22', '5l 22 cm stainless steel pot bottom', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12947S22', '22 cm stainless steel lid', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12945S20', '3l 20 cm stainless steel pot', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12946S20', '3l 20 cm stainless steel pot bottom', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12947S20', '20 cm stainless steel lid', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12945S17', '2l 17 cm stainless steel pot', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12945P17', '2l 17 cm stainless steel sauce pan', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12945S48', '17 cm stainless steel lid', 'Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('AS12945G48', '17 cm glass lid', 'Product');
    -- Intermediate Product Parts
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12945A01', '250 mm 5 mm stainless steel disc', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12945A02', '220 mm pot base phase 1', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12945A03', '220 mm pot base phase 2', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12945A04', '220 mm pot base final', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12947A01', '250 mm 1 mm stainless steel disc', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12947A02', '220 mm lid pressed', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12947A03', '220 mm lid polished', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12947A04', '220 mm lid with handle', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12945A32', '200 mm pot base phase 1', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12945A33', '200 mm pot base phase 2', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12945A34', '200 mm pot base final', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12947A32', '200 mm lid pressed', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12947A33', '200 mm lid polished', 'Intermediate_Product');
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('IP12947A34', '200 mm lid with handle', 'Intermediate_Product');
    -- Raw Material Parts
    INSERT INTO Part (PARTNUMBER, DESCRIPTION, Part_TypePART_TYPE) VALUES ('PN94561L67', 'Coolube 2210XP', 'Raw_Material');
    
    
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
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('A4578', '600t cold forging stamping press', 120, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('A4588', '600t cold forging precision stamping press', 120, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('A4598', '1000t cold forging precision stamping press', 120, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('S3271', 'Handle rivet', 600, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('K3675', 'Packaging', 240, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('K3676', 'Packaging for large items', 300, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('C5637', 'Border trimming', 300, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('D9123', 'Spot welding', 420, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('Q5478', 'Teflon application station', 3200, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('Q3547', 'Stainless steel polishing', 1800, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('T3452', 'Assembly T1', 120, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('G9273', 'Circular glass cutting', 500, 30);
    INSERT INTO Workstation_Type (WS_TYPE_ID, NAME, MAX_EXECUTIONTIME, SETUP_TIME) VALUES ('G9274', 'Glass trimming', 400, 30);
    
    
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
    INSERT INTO BOO (ProductPRODUCT_ID) VALUES ('AS12945S20');
    
    
    -- Component
    INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN12344A21', 50, 10);
    INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN52384R50', 100, 20);
    INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN52384R10', 30, 15);
    INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN18544A21', 25, 10);
    INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN18544C21', 40, 10);
    INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN18324C54', 75, 15);
    INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN52384R45', 60, 10);
    INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN52384R12', 20, 10);
    INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN18324C91', 15, 5);
    INSERT INTO Component (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN18324C51', 90, 25);
    
    -- Intermediate Products
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12945A01', 30, 10);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12945A02', 45, 15);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12945A03', 20, 5);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12945A04', 25, 10);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12947A01', 60, 15);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12947A02', 50, 10);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12947A03', 40, 10);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12947A04', 35, 10);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12945A32', 70, 20);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12945A33', 80, 25);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12945A34', 55, 10);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12947A32', 65, 20);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12947A33', 75, 25);
    INSERT INTO Intermediate_Product (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('IP12947A34', 50, 15);
    
    -- Raw Materials
    INSERT INTO Raw_Material (PartPARTNUMBER, STOCK, MIN_STOCK) VALUES ('PN94561L67', 200, 50);
    
    
    
    -- Reserved
    -- For Components
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN12344A21', 5);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN52384R50', 10);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN52384R10', 0);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN18544A21', 5);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN18544C21', 0);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN18324C54', 20);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN52384R45', 10);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN52384R12', 0);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN18324C91', 3);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN18324C51', 10);
    
    -- For Intermediate Products
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12945A01', 5);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12945A02', 10);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12945A03', 0);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12945A04', 5);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12947A01', 20);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12947A02', 15);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12947A03', 0);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12947A04', 5);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12945A32', 10);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12945A33', 15);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12945A34', 5);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12947A32', 5);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12947A33', 10);
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('IP12947A34', 15);
    
    -- For Raw Materials
    INSERT INTO Reserved (PartPARTNUMBER, RESERVED) VALUES ('PN94561L67', 25);
    
    
    
    
    
    
    
    -- AS12946S22
    -- Operation 115: Operation 115 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (115, 'Operation 115 description', 600, 'AS12946S22', 5659, NULL);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (115, 'IP12945A04', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (115, 'PN18544A21', 4, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (115, 'PN18544C21', 2, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (115, 'AS12946S22', 1, 'unit');
    
    -- Operation 114: Operation 114 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (114, 'Operation 114 description', 300, 'AS12946S22', 5653, 115);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (114, 'IP12945A03', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (114, 'IP12945A04', 1, 'unit');
    
    -- Operation 112: Operation 112 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (112, 'Operation 112 description', 120, 'AS12946S22', 5651, 114);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (112, 'IP12945A02', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (112, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (112, 'IP12945A03', 1, 'unit');
    
    -- Operation 103: Operation 103 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (103, 'Operation 103 description', 90, 'AS12946S22', 5649, 112);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (103, 'IP12945A01', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (103, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (103, 'IP12945A02', 1, 'unit');
    
    -- Operation 100: Operation 100 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (100, 'Operation 100 description', 120, 'AS12946S22', 5647, 103);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (100, 'PN52384R50', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (100, 'IP12945A01', 1, 'unit');
    
    
    
    
    -- AS12947S22
    -- Operation 124: Operation 124 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (124, 'Operation 124 description', 1200, 'AS12947S22', 5667, NULL);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (124, 'IP12947A04', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (124, 'AS12947S22', 1, 'unit');
    
    -- Operation 123: Operation 123 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (123, 'Operation 123 description', 150, 'AS12947S22', 5661, 124);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (123, 'IP12947A03', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (123, 'PN18324C54', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (123, 'PN12344A21', 3, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (123, 'IP12947A04', 1, 'unit');
    
    -- Operation 122: Operation 122 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (122, 'Operation 122 description', 240, 'AS12947S22', 5657, 123);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (122, 'IP12947A02', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (122, 'IP12947A03', 1, 'unit');
    
    -- Operation 121: Operation 121 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (121, 'Operation 121 description', 60, 'AS12947S22', 5655, 122);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (121, 'IP12947A01', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (121, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (121, 'IP12947A02', 1, 'unit');
    
    -- Operation 120: Operation 120 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (120, 'Operation 120 description', 105, 'AS12947S22', 5647, 121);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (120, 'PN52384R10', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (120, 'IP12947A01', 1, 'unit');
    
    
    
    
    
    -- AS12945S22
    -- Operation 130: Operation 130 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (130, 'Operation 130 description', 240, 'AS12945S22', 5663, NULL);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (130, 'AS12947S22', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (130, 'AS12946S22', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (130, 'AS12945S22', 1, 'unit');
    
    
    
    
    -- AS12946S20
    -- Operation 154: Operation 154 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (154, 'Operation 154 description', 600, 'AS12946S20', 5659, NULL);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (154, 'IP12945A34', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (154, 'PN18544C21', 2, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (154, 'PN18544A21', 4, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (154, 'AS12946S20', 1, 'unit');
    
    -- Operation 153: Operation 153 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (153, 'Operation 153 description', 320, 'AS12946S20', 5653, 154);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (153, 'IP12945A33', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (153, 'IP12945A34', 1, 'unit');
    
    -- Operation 152: Operation 152 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (152, 'Operation 152 description', 120, 'AS12946S20', 5651, 153);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (152, 'IP12945A32', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (152, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (152, 'IP12945A33', 1, 'unit');
    
    -- Operation 151: Operation 151 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (151, 'Operation 151 description', 90, 'AS12946S20', 5649, 152);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (151, 'IP12945A01', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (151, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (151, 'IP12945A32', 1, 'unit');
    
    -- Operation 150: Operation 150 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (150, 'Operation 150 description', 120, 'AS12946S20', 5647, 151);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (150, 'PN52384R50', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (150, 'IP12945A01', 1, 'unit');
    
    
    
    
    -- AS12947S20
    -- Operation 164: Operation 164 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (164, 'Operation 164 description', 1200, 'AS12947S20', 5667, NULL);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (164, 'IP12947A34', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (164, 'AS12947S20', 1, 'unit');
    
    -- Operation 163: Operation 163 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (163, 'Operation 163 description', 150, 'AS12947S20', 5661, 164);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (163, 'IP12947A33', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (163, 'PN18324C54', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (163, 'PN12344A21', 3, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (163, 'IP12947A34', 1, 'unit');
    
    -- Operation 162: Operation 162 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (162, 'Operation 162 description', 240, 'AS12947S20', 5657, 163);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (162, 'IP12947A32', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (162, 'IP12947A33', 1, 'unit');
    
    -- Operation 161: Operation 161 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (161, 'Operation 161 description', 60, 'AS12947S20', 5655, 162);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (161, 'IP12947A01', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (161, 'PN94561L67', 5, 'ml');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (161, 'IP12947A32', 1, 'unit');
    
    -- Operation 160: Operation 160 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (160, 'Operation 160 description', 90, 'AS12947S20', 5647, 161);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (160, 'PN52384R10', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (160, 'IP12947A01', 1, 'unit');
    
    
    
    -- AS12945S20
    -- Operation 170: Operation 170 description
    INSERT INTO Operation (OPERATION_ID, DESCRIPTION, EXPECTEDTIME, BOOProductPRODUCT_ID, Operation_TYPEOPTYPE_ID, NEXTSTEP)
    VALUES (170, 'Operation 170 description', 240, 'AS12945S20', 5663, NULL);
    
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (170, 'AS12946S20', 1, 'unit');
    INSERT INTO BOO_INPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (170, 'AS12947S20', 1, 'unit');
    INSERT INTO BOO_OUTPUT (OperationOPERATION_ID, PartPARTNUMBER, QUANTITY, UNIT) VALUES (170, 'AS12945S20', 1, 'unit');
    
    
    
    
    --Supplier
    INSERT INTO Supplier (SupplierID, START_DATE, END_DATE, MIN_ORDER, MIN_COST) VALUES (1, 20230101, 20241231, 100, 500);
    INSERT INTO Supplier (SupplierID, START_DATE, END_DATE, MIN_ORDER, MIN_COST) VALUES (2, 20220101, NULL, 50, 300);
    INSERT INTO Supplier (SupplierID, START_DATE, END_DATE, MIN_ORDER, MIN_COST) VALUES (3, 20230115, 20250115, 200, 700);
    
    
    --Supplier_Part
    --Components
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (1, 'PN12344A21');
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (1, 'PN52384R50');
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (2, 'PN18544A21');
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (3, 'PN18324C54');
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (2, 'PN52384R45');
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (1, 'PN18324C91');
    --Intermediate Products
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (2, 'IP12945A01');
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (2, 'IP12945A02');
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (3, 'IP12947A01');
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (1, 'IP12945A33');
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (3, 'IP12947A34');
    --Raw Materials
    INSERT INTO Supplier_Part (SupplierSupplierID, PartPARTNUMBER) VALUES (1, 'PN94561L67');




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





