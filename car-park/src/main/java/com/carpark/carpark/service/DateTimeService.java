package com.carpark.carpark.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateTimeService {
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
