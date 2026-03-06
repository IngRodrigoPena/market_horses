package com.nocountry.markethorses.domain;

import com.nocountry.markethorses.exception.BusinessException;
import com.nocountry.markethorses.exception.UnauthorizedException;

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
       validateOwner(actor);
        //Regla 1: solo BORRADOR
        validateDraft();
        //actualizar datos del caballo
        this.horse.updateData(newName, newBreed, newAge);
    }

    public List<Evidence> getEvidences(){
        return Collections.unmodifiableList(evidences);
    }

    public void addEvidence(User actor, Evidence evidence){

        validateDraft();

        if(!seller.getId().equals(actor.getId())){
            throw new UnauthorizedException("Only owner can upload evidence");
        }

        boolean duplicated = evidences.stream()
                .anyMatch(e -> e.getType() == evidence.getType());

        if(duplicated){
            throw new BusinessException("Evidence type already exists");
        }

        evidences.add(evidence);
    }


    public void submitForVerification(User actor){

        validateOwner(actor);
        validateDraft();

        if(evidences.isEmpty()){
            throw new BusinessException("Debe tener evidencia");
        }

        this.status = AdStatus.EN_VERIFICACION;

        evidences.forEach(Evidence :: markAsPendingVerification);
    }

    public void approve(User actor){
        //valida  si el actor puede aprobar el Ad
        if(actor.getRole() != Role.ADMIN){
            throw new UnauthorizedException("Solo ADMIN puede aprobar");
        }

        validatePending();

        this.status = AdStatus.APROBADO;
    }

    public void reject(User actor ){

        if(actor.getRole() != Role.ADMIN){
            throw new UnauthorizedException("Solo ADMIN puede rechazar");
        }

        validatePending();

        status = AdStatus.RECHAZADO;
    }

    //Eliminar duplicaciones y mejorar legibilidad

    private void validateOwner(User actor){
        //if(!actor.equals(seller)){
        if(!seller.getId().equals(actor.getId())){
            //throw new IllegalStateException("Solo el SELLER puede editar el anuncio");
            throw new UnauthorizedException("Only SELLER can edit Ad");
        }
    }

    private void validateDraft(){
        if(status != AdStatus.BORRADOR){
            //throw new IllegalStateException("Solo se puede editar/enviar un anuncio en estado BORRADOR");
            throw new BusinessException("Solo se puede editar/enviar un anuncio en estado BORRADOR");
        }
    }

    private void validatePending(){
        if(status != AdStatus.EN_VERIFICACION){
            throw new BusinessException("Solo anuncios EN_VERIFICACION");
        }
    }

    public void backToDraft(User actor){

        validateOwner(actor);

        if(this.status != AdStatus.RECHAZADO){
            throw new BusinessException("Solo los Ads RECHAZADOS pueden volver a BORRADOR");
        }

        this.status = AdStatus.BORRADOR;
    }

    public void publish(User actor){

        if(actor.getRole() != Role.ADMIN){
            throw new UnauthorizedException("Solo ADMIN puede publicar");
        }

        if(status != AdStatus.APROBADO){
            throw new BusinessException("Para que el Ad sea PUBLICADO debe estar APROBADO");
        }

        status = AdStatus.PUBLICADO;
    }

}


