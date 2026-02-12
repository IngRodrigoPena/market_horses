package com.nocountry.markethorses.domain;

public class Ad {

    private Long id;
    private Horse horse;
    private AdStatus status;

    public Ad(Horse horse) {
        this.horse = horse;
        this.status = AdStatus.BORRADOR;
    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public AdStatus getStatus() {
        return status;
    }

   /* public void setStatus(AdStatus status) {
        this.status = status;
    }*/
}


