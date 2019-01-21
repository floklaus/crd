package com.crd.carrental.controller;


import com.crd.carrental.dto.ApiError;
import com.crd.carrental.dto.Booking;
import com.crd.carrental.exception.BookingNotPossibleException;
import com.crd.carrental.exception.ParameterInvalidException;
import com.crd.carrental.facade.CarRentalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.crd.carrental.CarRentalApplication.API_VERSION_1;

@RequestMapping("/api/" + API_VERSION_1)
@RestController
public class CarRentalController {

    @Autowired
    CarRentalFacade carRentalFacade;

    @RequestMapping(path = "/bookings/{carType}/{date}/{days}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Booking booking(@PathVariable("carType") String carType, @PathVariable("date") String rentalStartDate, @PathVariable("days") int days) {
        return carRentalFacade.acknowledgeBooking(carType, rentalStartDate, days);
    }

    @RequestMapping(path = "/cars/{carType}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addCar(@PathVariable("carType") String carType) {
        carRentalFacade.addCar(carType);
        return new ResponseEntity<>(
                carType, new HttpHeaders(), HttpStatus.OK);
    }


    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "booking not possible")
    @ExceptionHandler(BookingNotPossibleException.class)
    @ResponseBody
    public ApiError handleBookingNotPossible(BookingNotPossibleException ex) {
        return new ApiError(ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "bad parameter")
    @ExceptionHandler(ParameterInvalidException.class)
    @ResponseBody
    public ResponseEntity<String> handleBadPArameter(ParameterInvalidException ex) {
        return new ResponseEntity<>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}

