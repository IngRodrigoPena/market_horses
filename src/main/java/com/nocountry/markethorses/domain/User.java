package com.nocountry.markethorses.domain;

public class User {
    private Long id;
    private String name;
    private Role role;

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof  User))
            return false;
        User user = (User)o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode(){
        return id != null ? id.hashCode() :0;
    }

}

