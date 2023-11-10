package com.carpark.carpark.repository;

import com.carpark.carpark.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository  extends JpaRepository<Authority, Long> {
}
