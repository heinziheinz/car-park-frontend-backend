package com.carpark.carpark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: löschen
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RescourceNotFoundException extends Exception {
}


