DROP DATABASE MyHotelReservation;
CREATE DATABASE MyHotelReservation;
USE MyHotelReservation;

DROP TABLE IF  EXISTS Hotel;
CREATE TABLE Hotel (
    id  INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(128),
    PRIMARY KEY (ID)
);

DROP TABLE IF  EXISTS PhoneBook;
CREATE TABLE PhoneBook (
    id  INT NOT NULL AUTO_INCREMENT,
    hotel_id INT,
    phone_no VARCHAR(20),
    PRIMARY KEY (ID),
    FOREIGN KEY (hotel_id)
        REFERENCES Hotel (id)
);

DROP TABLE IF  EXISTS HotelAddress;
CREATE TABLE HotelAddress (
    id  INT NOT NULL AUTO_INCREMENT,
    hotel_id INT,
    street VARCHAR(255),
    city VARCHAR(255),
    State VARCHAR(255),
    country VARCHAR(255),
    zip VARCHAR(25),
    PRIMARY KEY (ID),
    FOREIGN KEY (hotel_id)
        REFERENCES Hotel (id)
);

DROP TABLE IF  EXISTS Customer;
CREATE TABLE Customer (
    id  INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(30),
    last_name VARCHAR(60),
    address VARCHAR(255),
    login_id  VARCHAR(30) NOT NULL UNIQUE,
    password  VARCHAR(30),
    PRIMARY KEY (ID)
);


DROP TABLE IF  EXISTS Admin;
CREATE TABLE Admin (
    id  INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(30),
    last_name VARCHAR(60),
    login_id  VARCHAR(30) NOT NULL UNIQUE,
    password  VARCHAR(30),
    PRIMARY KEY (ID)
);

DROP TABLE IF  EXISTS CreditCard;
CREATE TABLE CreditCard (
    id  INT NOT NULL AUTO_INCREMENT,
    card_number VARCHAR(30),
    person_name VARCHAR(60),
    billing_address VARCHAR(255),
    expiration_date VARCHAR(20),
    security_code VARCHAR(10),
    PRIMARY KEY (ID)
);



DROP TABLE IF  EXISTS Breakfast;
CREATE TABLE Breakfast (
    id  INT NOT NULL AUTO_INCREMENT,
	hotel_id INT,
    description VARCHAR(255),
    price FLOAT,
    
    type VARCHAR(20),
    PRIMARY KEY (ID),
    FOREIGN KEY (hotel_id)
        REFERENCES Hotel(id)
);

DROP TABLE IF  EXISTS Room;
CREATE TABLE Room (
    id  INT NOT NULL AUTO_INCREMENT,
    hotel_id INT,
    room_no INT,
    room_type VARCHAR(20),
     price FLOAT,
    description VARCHAR(255),
    floor_no INT,
    max_people INT,
    reserved BIT,
    PRIMARY KEY (ID),
    FOREIGN KEY (hotel_id)
        REFERENCES Hotel(id)
);

DROP TABLE IF  EXISTS Discount;
CREATE TABLE Discount (
    id  INT NOT NULL AUTO_INCREMENT,
    hotel_id INT,
    fromDay INT,
    toDay INT,
    PRIMARY KEY (ID),
    FOREIGN KEY (hotel_id)
        REFERENCES Hotel(id)
);


DROP TABLE IF  EXISTS Service;
CREATE TABLE Service (
	id  INT NOT NULL AUTO_INCREMENT,
    hotel_id INT,
    type VARCHAR(20),
    price FLOAT,
    PRIMARY KEY (ID),
    FOREIGN KEY (hotel_id)
        REFERENCES Hotel(id)
);
DROP TABLE IF  EXISTS ServiceOption;
CREATE TABLE ServiceOption (
    id  INT NOT NULL AUTO_INCREMENT,
    service_id INT,
    
    PRIMARY KEY (ID),
    FOREIGN KEY (service_id)
        REFERENCES Service (id)
);

DROP TABLE IF  EXISTS BreakfastOption;
CREATE TABLE BreakfastOption (
    id  INT NOT NULL AUTO_INCREMENT,

    breakfast_id INT,
    
    PRIMARY KEY (ID),
    FOREIGN KEY (breakfast_id)
        REFERENCES Breakfast (id)
);

DROP TABLE IF  EXISTS RoomOption;
CREATE TABLE RoomOption (
    id  INT NOT NULL AUTO_INCREMENT,
    room_id INT,
    discount_id INT,
   
    PRIMARY KEY (ID),
    FOREIGN KEY (room_id)
        REFERENCES Room (id),
	FOREIGN KEY (discount_id)
        REFERENCES Discount (id)
);

DROP TABLE IF  EXISTS Reservation;
CREATE TABLE Reservation (
    id  INT NOT NULL AUTO_INCREMENT,
    room_option_id INT,
    breakfast_option_id INT,
    service_option_id INT,
    customer_id INT,
    creditcard_id INT,
    total_cost FLOAT,
    checkIn VARCHAR(20),
    checkOut VARCHAR(20),
    PRIMARY KEY (ID),
    FOREIGN KEY (room_option_id)
        REFERENCES RoomOption (id),
	FOREIGN KEY (breakfast_option_id)
        REFERENCES BreakfastOption (id),
	FOREIGN KEY (service_option_id)
        REFERENCES ServiceOption (id),
    FOREIGN KEY (customer_id)
        REFERENCES Customer (id),
    FOREIGN KEY (creditcard_id)
        REFERENCES CreditCard (id)
);


 
DROP TABLE IF  EXISTS Review;
CREATE TABLE Review (
    id  INT NOT NULL AUTO_INCREMENT,
    customer_id INT,
    review_type CHAR,
    description VARCHAR(255),
    PRIMARY KEY (ID),
    FOREIGN KEY (customer_id)
        REFERENCES Customer (id)
);