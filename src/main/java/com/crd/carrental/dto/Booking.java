package com.crd.carrental.dto;

import lombok.Data;

@Data
public class Booking {
    private boolean acknowledged;
    private String car;
    private String startDate;
    private String endDate;

}
