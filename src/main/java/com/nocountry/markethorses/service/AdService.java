package com.nocountry.markethorses.service;

import com.nocountry.markethorses.domain.*;
import com.nocountry.markethorses.domain.audit.AuditAction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class AdService {

    private final List<Ad>ads = new ArrayList<>();
    private Long nextId = 1L;
    private final AuditService auditService;

    public Ad createAd(User user, String horseName, String breed, Integer age){

        //Regla: solo SELLER puede crear anuncio
        if(user.getRole() != Role.SELLER){
            throw  new IllegalArgumentException("Solo un SELLER puede crear un anuncio.");
        }

        //Crear Horse
        Horse horse = new Horse();
        horse.setName(horseName);
        horse.setBreed(breed);
        horse.setAge(age);
        horse.setOwner(user);
        //create AD (nace en BORRADOR automaticamente)
        Ad ad = new Ad(nextId++,horse,user);
       //agrega a la lista
        ads.add(ad);
        //log
        auditService.register(user.getId(), AuditAction.AD_CREATED,Ad.class.getSimpleName());
        return(ad);
    }

    private Ad getAdOrThrow(Long id){
        return ads.stream()
                .filter(ad->ad.getId().equals(id))
                .findFirst()
                .orElseThrow(()->
                        new IllegalArgumentException("Anuncio NO encontrado"));
    }

    public Ad editAd(Long id, User actor, String name, String breed, Integer age){
         Ad ad = getAdOrThrow(id);
        ad.edit(actor,name,breed,age);
        //log
        auditService.register(actor.getId(),AuditAction.AD_UPDATED,Ad.class.getSimpleName());
        return ad;
    }

    public AdService(AuditService auditService){
        this.auditService = auditService;
    }
}


