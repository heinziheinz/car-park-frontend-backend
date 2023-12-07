package com.carpark.carpark.model;


import java.util.Set;

public record LogInUserJWT( long userId, String username, Set<String> authorities) {
}
