package com.example.demo.model;

import com.example.demo.data.Address;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Person {
    private String id;
    private String firstName;
    private String lastName;
    private Address address;
    private List<String> aliases;
}
