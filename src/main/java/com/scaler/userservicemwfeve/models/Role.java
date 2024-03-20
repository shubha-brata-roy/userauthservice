package com.scaler.userservicemwfeve.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "roles")
public class Role extends BaseModel {
    private String name;
}
