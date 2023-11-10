package com.carpark.carpark.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Authority {
    @Id
    @GeneratedValue
    private long id;

    private AuthorityEnum authority;

    public Authority() {

    }

    public Authority(AuthorityEnum authority) {
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AuthorityEnum  getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityEnum  authority) {
        this.authority = authority;
    }
}
