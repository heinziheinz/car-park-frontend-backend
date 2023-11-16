package com.carpark.carpark.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DateTimeService {
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
