package com.nocountry.markethorses.domain;

public class Horse {

    private Long id;
    private String name;
    private String breed;
    private Integer age;
    private User owner;

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    /*public void setId(Long id) {
        this.id = id;
    }*/

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void updateData(String name, String breed, Integer age){
        this.name = name;
        this.breed = breed;
        this.age = age;
    }
}
