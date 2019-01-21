package com.crd.carrental.service;

import com.crd.carrental.exception.BookingNotPossibleException;
import com.crd.carrental.model.Car;
import com.crd.carrental.model.CarType;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CarRentalServiceImplTest {

    @InjectMocks
    CarRentalServiceImpl carRentalService;

    @Before
    public void setup() {
        carRentalService = new CarRentalServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void acknowledgeBooking() {
        Car car = new Car(CarType.SEDAN);
        when(carRentalService.isCarAvailable(any(), any())).thenReturn(car);

        Car bookedCar = carRentalService.acknowledgeBooking(CarType.SEDAN, getIntervalInFuture(1, 3));

        assertNotNull(bookedCar);

        assertSame(bookedCar.getCarType(), CarType.SEDAN);
    }

    @Test
    public void addOneCarForOneCarType() {
        Car car = new Car(CarType.getType("sedan"));
        carRentalService.addCar(car);
        assertTrue(carRentalService.getCarpoolSize(CarType.getType("sedan")) == 1);

    }

    @Test
    public void addMultipleCarForOneCarType() {
        Car car1 = new Car(CarType.getType("sedan"));
        carRentalService.addCar(car1);
        Car car2 = new Car(CarType.getType("sedan"));
        carRentalService.addCar(car2);
        Car car3 = new Car(CarType.getType("sedan"));
        carRentalService.addCar(car3);
        Car car4 = new Car(CarType.getType("sedan"));
        carRentalService.addCar(car4);
        assertTrue(carRentalService.getCarpoolSize(CarType.getType("sedan")) == 4);

    }

    @Test
    public void addMultipleCarForMultipleCarTypes() {
        Car car1 = new Car(CarType.getType("sedan"));
        carRentalService.addCar(car1);
        Car car2 = new Car(CarType.getType("suv"));
        carRentalService.addCar(car2);
        Car car3 = new Car(CarType.getType("sedan"));
        carRentalService.addCar(car3);
        Car car4 = new Car(CarType.getType("sedan"));
        carRentalService.addCar(car4);
        assertTrue(carRentalService.getCarpoolSize(CarType.getType("sedan")) == 3);
        assertTrue(carRentalService.getCarpoolSize(CarType.getType("suv")) == 1);
    }

    @Test
    public void isCarAvailableForInitialBooking() {
        Car car = new Car(CarType.getType("sedan"));
        assertNull(carRentalService.isCarAvailable(car.getCarType(), getIntervalInFuture(1, 4)));
        carRentalService.addCar(car);
        assertNotNull(carRentalService.isCarAvailable(car.getCarType(), getIntervalInFuture(1, 4)));
    }

    @Test(expected = BookingNotPossibleException.class)
    public void moreBookingThanStockThrowsException() {
        Car car = new Car(CarType.getType("sedan"));
        carRentalService.addCar(car);
        carRentalService.acknowledgeBooking(CarType.SEDAN, getIntervalInFuture(5, 5));
        carRentalService.acknowledgeBooking(CarType.SEDAN, getIntervalInFuture(5, 5));
    }

    @Test
    public void severalBookingsInDifferentTimes() {
        Car car = new Car(CarType.getType("sedan"));
        carRentalService.addCar(car);
        Car car1 = carRentalService.acknowledgeBooking(CarType.SEDAN, getIntervalInFuture(1, 4));
        Car car2 = carRentalService.acknowledgeBooking(CarType.SEDAN, getIntervalInFuture(5, 5));
        assertSame(car1, car2);
    }

    @Test
    public void severalBookingsForDifferentTypes() {
        Car sedan = new Car(CarType.getType("sedan"));
        carRentalService.addCar(sedan);
        Car suv = new Car(CarType.getType("suv"));
        carRentalService.addCar(suv);

        Car car1 = carRentalService.acknowledgeBooking(CarType.SEDAN, getIntervalInFuture(1, 4));
        Car car2 = carRentalService.acknowledgeBooking(CarType.SUV, getIntervalInFuture(1, 4));
        assertNotSame(car1, car2);
        assertEquals(car1.getCarType(), CarType.SEDAN);
        assertEquals(car2.getCarType(), CarType.SUV);
    }


    private Interval getIntervalInFuture(int daysAhead, int days) {
        DateTime tomorrow = DateTime.now().plusDays(daysAhead);
        DateTime endOfRental = new DateTime(tomorrow);
        endOfRental = endOfRental.plusDays(days);
        return new Interval(tomorrow, endOfRental);
    }
}