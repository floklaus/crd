package com.crd.carrental.model;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.Interval;

@Data
public class Car {
    CarType carType;
    RangeSet<DateTime> bookings;

    public Car(CarType carType) {
        this.carType = carType;
        bookings = TreeRangeSet.create();
    }

    public boolean addBooking(Interval interval) {
        if (availableForInterval(interval)) {
            bookings.add(Range.closed(interval.getStart(), interval.getEnd()));
            return true;
        } else {
            return false;
        }
    }


    public boolean availableForInterval(Interval interval) {
        return !bookings.intersects(Range.closed(interval.getStart(), interval.getEnd()));
    }


}
