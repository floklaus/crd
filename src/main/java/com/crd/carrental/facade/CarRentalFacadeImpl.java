package com.crd.carrental.facade;

import com.crd.carrental.dto.Booking;
import com.crd.carrental.exception.BookingNotPossibleException;
import com.crd.carrental.exception.ParameterInvalidException;
import com.crd.carrental.model.Car;
import com.crd.carrental.model.CarType;
import com.crd.carrental.service.CarRentalService;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarRentalFacadeImpl implements CarRentalFacade {

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy");

    @Autowired
    CarRentalService carRentalService;

    @Override
    public Booking acknowledgeBooking(String type, String startDate, int days) throws BookingNotPossibleException, ParameterInvalidException {
        assertDays(days);
        DateTime startOfRental = assertStartDate(startDate);
        CarType carType = assertCarType(type);
        Interval requestedRentalInterval = getRentalInterval(startOfRental, days);

        return createAcknowledgedBooking(carRentalService.acknowledgeBooking(carType, requestedRentalInterval), requestedRentalInterval);
    }

    @Override
    public void addCar(String carType) throws ParameterInvalidException {
        CarType type = CarType.getType(carType);
        if (null == type) {
            throw new ParameterInvalidException("unknown car type");
        }
        Car car = new Car(type);
        carRentalService.addCar(car);
    }


    private void assertDays(int days) throws ParameterInvalidException {
        if (days < 1) {
            throw new ParameterInvalidException("number of days must be larger than 0");
        }
    }

    private DateTime assertStartDate(String startDate) {
        DateTime startOfRental;
        try {
            startOfRental = formatter.parseDateTime(startDate);
        } catch (IllegalArgumentException ex) {
            throw new ParameterInvalidException("wrong format of start date. must be MM-dd-yyyy");
        }
        if (startOfRental.isBeforeNow()) {
            throw new ParameterInvalidException("start date must be after now");
        }
        // more validations possible
        return startOfRental;
    }


    private CarType assertCarType(String type) {
        CarType carType = CarType.getType(type);
        if (null != carType) {
            return carType;
        } else {
            throw new ParameterInvalidException("wrong parameter: car type unknown");
        }
    }

    private Interval getRentalInterval(DateTime startOfRental, int days) {
        DateTime endOfRental = new DateTime(startOfRental);
        endOfRental = endOfRental.plusDays(days);
        return new Interval(startOfRental, endOfRental);
    }

    private Booking createAcknowledgedBooking(Car car, Interval interval) {
        Booking booking = new Booking();
        booking.setCar(car.getCarType().getTypeValue());
        booking.setStartDate(formatter.print(interval.getStart()));
        booking.setEndDate(formatter.print(interval.getEnd()));
        booking.setAcknowledged(true);
        return booking;

    }
}
