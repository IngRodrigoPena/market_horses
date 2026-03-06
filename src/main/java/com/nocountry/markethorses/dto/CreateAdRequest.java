package com.nocountry.markethorses.dto;

public class CreateAdRequest {

    //private String title;
    //private String description;
    private Long horseId;
    private Long sellerId;

    public CreateAdRequest(){
    }

  /*  public CreateAdRequest(String title, String description, Long horseId,Long sellerId){
        this.title = title;
        this.description = description;
        this.horseId = horseId;
        this.sellerId = sellerId;
    }*/
    //getters
    /*public String getDescription() {
        return description;
    }

    /*public String getTitle() {
        return title;
    }*/


    public Long getHorseId() {
        return horseId;
    }

    public Long getSellerId() {
        return sellerId;
    }



}
