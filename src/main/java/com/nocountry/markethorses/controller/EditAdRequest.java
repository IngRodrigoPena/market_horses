package com.nocountry.markethorses.controller;

public class EditAdRequest {

    private Long userId;
    private String horseName;
    private String breed;
    private Integer age;

    public Long getUserId() { return userId; }
    public String getHorseName() { return horseName; }
    public String getBreed() { return breed; }
    public Integer getAge() { return age; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setHorseName(String horseName) { this.horseName = horseName; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setAge(Integer age) { this.age = age; }

}
