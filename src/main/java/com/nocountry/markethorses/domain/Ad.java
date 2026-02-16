package com.nocountry.markethorses.domain;

public class Ad {

    private Long id;
    private Horse horse;
    private User seller;
    private AdStatus status;

    public Ad(Horse horse, User seller) {
        this.horse = horse;
        this.seller = seller;
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

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public AdStatus getStatus() {
        return status;
    }

    /*temporal prueba 3
    public void setStatus(AdStatus status) {
        this.status = status;
    }*/

    public void edit(User actor, String newName, String newBreed, Integer newAge){

        //Regla 1: solo BORRADOR
        if(this.status != AdStatus.BORRADOR){
            throw new IllegalStateException("Solo se puede editar un anuncio en estado BORRADOR");
        }

        //Regla 2: solo el SELLER dueño
        if(!this.seller.equals(actor)){
            throw new IllegalStateException("Solo el dueño del anuncio puede editarlo");
        }

        //actualizar datos del caballo
        this.horse.updateData(newName, newBreed, newAge);
    }


}


