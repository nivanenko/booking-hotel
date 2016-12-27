---------------------------
-- Populate Customer table
---------------------------

INSERT INTO Customers (cust_email, cust_name, cust_country, cust_pass) 
VALUES ('firstcustomer@gmail.com', 'Tom', 'USA', 'customer');

INSERT INTO Customers (cust_email, cust_name, cust_country, cust_pass) 
VALUES ('secondcustomer@gmail.com', 'Piotr', 'Poland', 'customer');

INSERT INTO Customers (cust_email, cust_name, cust_country, cust_pass) 
VALUES ('firstowner@gmail.com', 'Vladimir', 'Russia', 'owner');

INSERT INTO Customers (cust_email, cust_name, cust_country, cust_pass) 
VALUES ('secondowner@gmail.com', 'Nazar', 'Ukraine', 'owner');

---------------------------
-- Populate Owners table
---------------------------

INSERT INTO Owners (owner_email) VALUES ('firstowner@gmail.com');
INSERT INTO Owners (owner_email) VALUES ('secondowner@gmail.com');

---------------------------
-- Populate Hotels table
---------------------------

INSERT INTO Hotels (hotel_id, hotel_description, hotel_name, hotel_address, hotel_owner, hotel_city) 
VALUES (1, 'Well located hotel offers you quiet and clean rooms', 'Krakow Glory',
 'NoFantasy street 44', 'firstowner@gmail.com', 'Krakow');
 
INSERT INTO Hotels (hotel_id, hotel_description, hotel_name, hotel_address, hotel_owner, hotel_city) 
VALUES (2, 'Well located hotel offers you quiet and clean rooms', 'Astoria',
 'NoFantasy street 28', 'firstowner@gmail.com', 'Berlin');

INSERT INTO Hotels (hotel_id, hotel_description, hotel_name, hotel_address, hotel_owner, hotel_city) 
VALUES (3, 'Well located hotel offers you something', 'Royal hotel',
 'NoFantasy street 57', 'firstowner@gmail.com', 'Warsaw');

INSERT INTO Hotels (hotel_id, hotel_description, hotel_name, hotel_address, hotel_owner, hotel_city) 
VALUES (4, 'Well located hotel offers you cookies', 'Voyage',
 'NoFantasy street 12', 'firstowner@gmail.com', 'Krakow');

INSERT INTO Hotels (hotel_id, hotel_description, hotel_name, hotel_address, hotel_owner, hotel_city) 
VALUES (5, 'Welcome, guests!', 'Ukraine',
 'NoFantasy street 76', 'firstowner@gmail.com', 'Kiev');

INSERT INTO Hotels (hotel_id, hotel_description, hotel_name, hotel_address, hotel_owner, hotel_city) 
VALUES (6, 'Well located hotel offers you sweets', 'Elite Hall',
 'NoFantasy street 99', 'secondowner@gmail.com', 'Kiev');

INSERT INTO Hotels (hotel_id, hotel_description, hotel_name, hotel_address, hotel_owner, hotel_city) 
VALUES (7, 'Well located hotel offers you a beer', 'Beer House',
 'NoFantasy street 93', 'secondowner@gmail.com', 'Berlin');

INSERT INTO Hotels (hotel_id, hotel_description, hotel_name, hotel_address, hotel_owner, hotel_city) 
VALUES (8, 'Well located hotel offers you rooms', 'Warsaw Stroy',
 'NoFantasy street 11', 'secondowner@gmail.com', 'Warsaw');

INSERT INTO Hotels (hotel_id, hotel_description, hotel_name, hotel_address, hotel_owner, hotel_city) 
VALUES (9, 'Well located hotel offers you hookers', 'Prime Hotel',
 'NoFantasy street 69', 'secondowner@gmail.com', 'Kiev');

INSERT INTO Hotels (hotel_id, hotel_description, hotel_name, hotel_address, hotel_owner, hotel_city) 
VALUES (10, 'Go away, you are not welcome!', 'Go to hell',
 'NoFantasy street 56', 'secondowner@gmail.com', 'Berlin'); 
 
 
---------------------------
-- Populate Rooms table
---------------------------

INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (1, 2, 'For couple', 1, 75);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (2, 3, 'For family', 1, 150);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (3, 1, 'Alone', 2, 50);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (4, 2, 'For married', 3, 99);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (5, 2, 'For married', 8, 79);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (6, 4, 'For big family', 3, 199);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (7, 5, 'Very big family', 4, 249);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (8, 2, 'Cozy room', 5, 99);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (9, 2, 'Cozy ', 6, 299);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (10, 1, 'Forever alone', 6, 49);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (11, 3, 'Young family', 7, 99);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (12, 2, 'Couple only', 8, 399);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (13, 2, 'Couple only', 9, 39);
INSERT INTO Rooms (room_id, room_capacity, room_description, room_hotel, room_price) 
VALUES (14, 2, 'Couple only', 10, 199);

---------------------------
-- Populate Bookings table
---------------------------

INSERT INTO Bookings (booking_id, booking_date_in, booking_date_out, booking_room, booking_customer) 
VALUES (1, TO_DATE('2017/01/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2017/01/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 1, 'firstcustomer@gmail.com');

INSERT INTO Bookings (booking_id, booking_date_in, booking_date_out, booking_room, booking_customer) 
VALUES (2, TO_DATE('2017/01/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2017/01/05 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 2, 'secondcustomer@gmail.com');

INSERT INTO Bookings (booking_id, booking_date_in, booking_date_out, booking_room, booking_customer) 
VALUES (3, TO_DATE('2017/02/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2017/01/07 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 3, 'firstcustomer@gmail.com');

INSERT INTO Bookings (booking_id, booking_date_in, booking_date_out, booking_room, booking_customer) 
VALUES (4, TO_DATE('2017/01/21 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2017/01/23 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 4, 'secondcustomer@gmail.com');

INSERT INTO Bookings (booking_id, booking_date_in, booking_date_out, booking_room, booking_customer) 
VALUES (5, TO_DATE('2017/01/11 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2017/01/13 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 4, 'firstcustomer@gmail.com');

INSERT INTO Bookings (booking_id, booking_date_in, booking_date_out, booking_room, booking_customer) 
VALUES (6, TO_DATE('2017/08/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2017/08/13 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 6, 'secondcustomer@gmail.com');

INSERT INTO Bookings (booking_id, booking_date_in, booking_date_out, booking_room, booking_customer) 
VALUES (7, TO_DATE('2017/07/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2017/07/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 7, 'firstcustomer@gmail.com');

INSERT INTO Bookings (booking_id, booking_date_in, booking_date_out, booking_room, booking_customer) 
VALUES (8, TO_DATE('2017/06/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2017/06/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 8, 'secondcustomer@gmail.com');

INSERT INTO Bookings (booking_id, booking_date_in, booking_date_out, booking_room, booking_customer) 
VALUES (9, TO_DATE('2017/05/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2017/06/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 9, 'firstcustomer@gmail.com');

INSERT INTO Bookings (booking_id, booking_date_in, booking_date_out, booking_room, booking_customer) 
VALUES (10, TO_DATE('2017/04/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2017/04/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 10, 'secondcustomer@gmail.com');