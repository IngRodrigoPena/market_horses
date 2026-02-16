package com.nocountry.markethorses.domain;

public class Ad {

    private final Long id;
    private final Horse horse;
    private final User seller;
    private AdStatus status;

    public Ad(Long id,Horse horse, User seller) {
        this.id = id;
        this.horse = horse;
        this.seller = seller;
        this.status = AdStatus.BORRADOR;
    }

    //getters and setters
    public Long getId() {
        return id;
    }

    public Horse getHorse() {
        return horse;
    }

    public User getSeller() {
        return seller;
    }

    public AdStatus getStatus() {
        return status;
    }


    public void edit(User actor, String newName, String newBreed, Integer newAge){

        //Regla 1: solo BORRADOR
        if(this.status != AdStatus.BORRADOR){
            throw new IllegalStateException("Solo se puede editar un anuncio en estado BORRADOR");
        }

        //Regla 2: solo el SELLER dueño
        //if(!this.seller.equals(actor)){
        if(!this.seller.getId().equals(actor.getId())){
                throw new IllegalStateException("Solo el dueño del anuncio puede editarlo");
            }

        //actualizar datos del caballo
        this.horse.updateData(newName, newBreed, newAge);
    }


}


