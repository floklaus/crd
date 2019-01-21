package com.crd.carrental.facade;

import com.crd.carrental.exception.ParameterInvalidException;
import com.crd.carrental.service.CarRentalService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CarRentalFacadeImplTest {

    @InjectMocks
    CarRentalFacadeImpl carRentalFacade;

    @Mock
    CarRentalService carRentalService;

    @Before
    public void setup() {
        carRentalFacade = new CarRentalFacadeImpl();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void whenParameterAreCorrectCarrentalServiceGetCalled() {
        int days = 5;
        String date = getTomorrow();
        String type = "sedan";

        carRentalFacade.acknowledgeBooking(type, date, days);
        verify(carRentalService, times(1)).acknowledgeBooking(any(), any());
    }

    @Test(expected = ParameterInvalidException.class)
    public void whenTypeWrongThenThrowParameterInvalidException() {
        int days = 1;
        String date = getTomorrow();
        String type = "jeep";

        carRentalFacade.acknowledgeBooking(type, date, days);

    }

    private String getTomorrow() {
        DateTime now = DateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy");
        return formatter.print(now);
    }

    private String getYesterday() {
        DateTime now = DateTime.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy");
        return formatter.print(now);
    }
}