package com.crd.carrental.facade;

import com.crd.carrental.dto.Booking;
import com.crd.carrental.exception.ParameterInvalidException;


public interface CarRentalFacade {
    Booking acknowledgeBooking(String carType, String startDate, int days);

    void addCar(String carType) throws ParameterInvalidException;
}
