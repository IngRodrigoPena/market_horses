package com.nocountry.markethorses.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ad {

    private final Long id;
    private final Horse horse;
    private final User seller;
    private AdStatus status;

    private final List<Evidence> evidences = new ArrayList<>();

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

        //Regla 2: solo el SELLER dueño
        if(!this.seller.getId().equals(actor.getId())){
            throw new IllegalStateException("Solo el dueño del anuncio puede editarlo");
        }

        //Regla 1: solo BORRADOR
        if(this.status != AdStatus.BORRADOR){
            throw new IllegalStateException("Solo se puede editar un anuncio en estado BORRADOR");
        }

        //actualizar datos del caballo
        this.horse.updateData(newName, newBreed, newAge);
    }

    public List<Evidence> getEvidences(){
        return Collections.unmodifiableList(evidences);
    }

    public void addEvidence(Evidence evidence){
        if(this.status != AdStatus.BORRADOR){
            //throw new IllegalStateException("Cannot add evidence unless BORRADOR");
            throw new IllegalStateException("Solo se puede agregar evidencia en el estado BORRADOR");
        }

        boolean duplicated = evidences.stream()
                .anyMatch(e-> e.getType() == evidence.getType());

        if(duplicated){
            //throw new IllegalStateException("Evidence type already exists");
            throw new IllegalStateException("Ese tipo de evidencia ya existe");
        }

        evidences.add(evidence);
    }

    public void submitForVerification(User actor){
        if(!seller.getId().equals(actor.getId())){
            //throw new IllegalStateException("Only seller can submit");
            throw new IllegalStateException("Unicamente el VENDEDOR puede enviar a verificacion");
        }

        if(this.status != AdStatus.BORRADOR){
            //throw new IllegalStateException("Ad must be Borrador");
            throw new IllegalStateException("Ad debe estar en modo BORRADOR");
        }

        if(evidences.isEmpty()){
            //throw new IllegalStateException("Cannot submit without evidence");
            throw new IllegalStateException("No se puede enviar sin Evidencia");
        }

        this.status = AdStatus.EN_VERIFICACION;

        evidences.forEach(Evidence :: markAsPendingVerification);
    }

    private boolean isSeller(User actor){
        return this.seller.getId().equals(actor.getId());
    }

    public void approve(){

        if(status != AdStatus.EN_VERIFICACION){
            //throw new IllegalStateException("Ad must be in verification");
            throw new IllegalStateException("Ad debe esta en VERIFICACION");
        }

        //this.status = AdStatus.PUBLICADO;
        status = AdStatus.PUBLICADO;
    }

    public void reject(){

        if(status != AdStatus.EN_VERIFICACION){
            //throw new IllegalStateException("Ad must be in verification");
            throw new IllegalStateException("Ad debe estar en VERIFICACION");
        }

        status = AdStatus.RECHAZADO;
    }

}


