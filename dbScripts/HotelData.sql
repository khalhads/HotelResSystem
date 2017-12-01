USE MyHotelReservation;

insert into Hotel (id, name ) values(1, 'Holiday Inn'), (2, 'Hilton');

insert into Admin (  id , first_name, last_name , login_id,password)
	values(1, 'John', 'Doe', 'john', 'johndoe'),
			(2, 'Jane', 'Doe', 'jane', 'janedoe');
            
insert into PhoneBook (id, hotel_id, phone_no ) 
	values(1, 1, '999-999-9999'), (2, 1, '999-999-8888'),
		  (3, 2, '999-999-7777'), (4, 2, '999-999-6666');

insert into HotelAddress (id, hotel_id,street, city ,State ,country,zip ) 
	values(1, 1, '1 Main st', 'Newark', 'New Jersey','USA', '08564'), 
		  (2, 2, '2 Main st', 'New York', 'New York','USA', '10002');


insert into Service (id, hotel_id, price, type) 
	values(1, 1, 23.60,  'Valet Parking'), 
		  (2, 1, 25.90, 'Car Wash'),
          (3, 1, 30.99,'Laundry'), 
		  (4, 2, 30.99,'Laundry');
                  
    
  insert into Room ( id , hotel_id ,room_no, price, description, room_type,floor_no, max_people ,reserved)   
    values(1, 1, 111, 99.99, "Facing South", "Single", 1, 2, 0), 
		  (2, 1, 211, 99.99, "Facing East", "Double", 2, 3, 0),
          (3, 2, 311, 99.99, "Facing West", "Double", 3, 3, 0), 
		  (4, 2, 411, 199.99, "Facing North","Suite", 4, 6, 0);
 
 
  insert into Breakfast ( id ,hotel_id , price, description, type)   
    values(1, 1, 25.95, "Egg & toast",  "Regular"), 
		  (2, 1, 35.95, "Egg & toast + Tea and Orange Juice", "Gourmet"),
          (3, 2, 45.95, "Order whatever you like", "Supreme"), 
		  (4, 1, 35.95, "Egg & toast + Tea and Orange Juice",  "Gourmet");
          
insert into Discount ( id ,hotel_id ,fromDay, toDay)   
    values(1, 1, 2, 5), 
		  (2, 1, 2, 5),
          (3, 2, 2, 5),
		  (4, 2, 2, 5);
 
 /*
select * from Hotel;
select * from PhoneBook;
select * from HotelAddress;
select * from ServiceOption;
select * from RoomOption;
select * from Room;
select * from Discount;
select * from Customer;
BreakReview_Evaluatedselect * from Reservation;
*/

