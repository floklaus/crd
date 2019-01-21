package com.crd.carrental.service;

import com.crd.carrental.exception.BookingNotPossibleException;
import com.crd.carrental.model.Car;
import com.crd.carrental.model.CarType;
import org.joda.time.Interval;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarRentalServiceImpl implements CarRentalService {

    private Map<CarType, List<Car>> carpool = new HashMap<>();

    @Override
    public void addCar(Car car) {
        if (carpool.containsKey(car.getCarType())) {
            carpool.get(car.getCarType()).add(car);
        } else {
            List<Car> listOfCarsForCarType = new ArrayList<>();
            listOfCarsForCarType.add(car);
            carpool.put(car.getCarType(), listOfCarsForCarType);
        }
    }

    @Override
    public List<Car> getCarpool(CarType carType) {
        return carpool.get(carType);
    }

    @Override
    public int getCarpoolSize(CarType carType) {
        if (carpool.containsKey(carType)) {
            return carpool.get(carType).size();
        } else {
            return 0;
        }
    }

    @Override
    public Car isCarAvailable(CarType carType, Interval requestedInterval) {
        List<Car> cars = carpool.get(carType);
        if (null != cars) {
            for (Car car : cars) {
                if (car.availableForInterval(requestedInterval)) {
                    return car;
                }
            }
        }
        return null;
    }

    @Override
    public Car acknowledgeBooking(CarType carType, Interval interval) throws BookingNotPossibleException {
        Car carAvailable = this.isCarAvailable(carType, interval);
        if (null != carAvailable) {
            carAvailable.addBooking(interval);
            return carAvailable;
        } else {
            throw new BookingNotPossibleException("no car available");
        }
    }


}
