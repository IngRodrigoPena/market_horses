package com.nocountry.markethorses.dto;

public class UpdateAdRequest {

    private Long userId;
    private String horseName;
    private String breed;
    private Integer age;

    public Long getUserId() {
        return userId;
    }

    public String getHorseName() {
        return horseName;
    }

    public String getBreed() {
        return breed;
    }

    public Integer getAge() {
        return age;
    }
}
