# car rental project for crd

@author: Florian Klaus

@email: florian.klaus.17@gmail.com`


This project is to demonstrate java programming skill of me. It is a REST-API with spring boot.

please start application with

`mvn spring-boot:run`

and access http://localhost:8080/swagger-ui.html in your browser to see a user interface.

available car type are final: "sedan", "suv", "mini-van"

create new car in carpool
PUT http://localhost:8080/api/v1/cars/suv

add new booking for a car
GET http://localhost:8080/api/v1/bookings/suv/07-01-2019/6

