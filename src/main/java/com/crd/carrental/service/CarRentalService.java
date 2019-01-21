package com.crd.carrental.service;

import com.crd.carrental.exception.BookingNotPossibleException;
import com.crd.carrental.model.Car;
import com.crd.carrental.model.CarType;
import org.joda.time.Interval;

import java.util.List;

public interface CarRentalService {
    Car acknowledgeBooking(CarType carType, Interval requestedInterval) throws BookingNotPossibleException;

    void addCar(Car var);

    List<Car> getCarpool(CarType carType);

    int getCarpoolSize(CarType carType);

    Car isCarAvailable(CarType carType, Interval requestedInterval);
}
