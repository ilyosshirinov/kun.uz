package com.example.Mapper;

import java.time.LocalDate;

public interface CategoryMapper {
    Integer getId();

    Integer getOrderNumber();

    LocalDate getCreatedDate();


    String getName();
}
