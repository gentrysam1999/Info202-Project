/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  gensa844
 * Created: 4/08/2020
 */

create table Product (
	Product_ID integer(10) not null,
	Product_Name varchar(50) not null,
	Description varchar(300),
	Category varchar(50) not null,
	Price decimal(6, 2) not null,
	Stock_Quantity integer(10) not null,
	constraint Product_PK primary key (Product_ID)
);

create table Customer(
        Customer_ID int not null AUTO_INCREMENT,
        Customer_UserName varchar(50) not null unique,
        First_Name varchar(30) not null,
        Last_Name varchar(30) not null,
        Email varchar(30) not null,
        Address varchar(50) not null,
        Credit_Card_Details varchar(50) not null,
        Password varchar(20) not null,
        constraint Customer_PK primary key (Customer_ID)
);

insert into Customer (Customer_UserName, First_Name, Last_Name, Email, Address, Credit_Card_Details ,Password)
values ('testCust', 'John', 'Applesee', 'johnappleseed@email.com', '123 john street', '987654321' ,'password');
values ('testCust2', 'Jenny', 'Jackson', 'jennyjackson@email.com', '123 jenny street', '123456789' ,'password');

create table Sale (
	Sale_ID int not null AUTO_INCREMENT,
        Date date not null,
        Status varchar(20) not null,
        Customer_ID int references Customer(Customer_ID),
        -- contraint Sale_Customer foreign key (Customer_ID) references Customer,
        constraint Sale_PK primary key (Sale_ID)
);

insert into Sale (Date, Status, Customer_ID)
values ('2018-11-11', 'Done', 2);

create table Sale_Item (
        Quantity_Purchased int not null,
        Sale_Price int not null,
        Product_ID int references Product(Product_ID),
        Sale_ID int references Sale(Sale_ID)
        -- contraint Sale_Item_Product foreign key (Product_ID) references Product,
        -- constraint Sale foreign key (Sale_ID) references Sale
);

insert into Sale_Item(Quantity_Purchased, Sale_Price, Product_ID, Sale_ID)
values (10, 10, 1234567, 1);