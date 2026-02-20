package com.nocountry.markethorses.service;

import com.nocountry.markethorses.domain.*;
import com.nocountry.markethorses.domain.audit.AuditAction;
import com.nocountry.markethorses.domain.repository.AdRepository;
import org.springframework.stereotype.Service;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final AuditService auditService;
    private Long nextId = 1L;

    public AdService(AdRepository adRepository,
                     AuditService auditService){
        this.adRepository = adRepository;
        this.auditService = auditService;
    }

    public Ad createAd(User user,String horseName,String breed,Integer age){

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
        adRepository.save(ad);
        //log
        auditService.register(user.getId(),
                              AuditAction.AD_CREATED,
                              Ad.class.getSimpleName());
        return(ad);
    }

    private Ad getAdOrThrow(long id){
        return adRepository.findById(id);
    }

    public Ad editAd(Long id, User actor, String name, String breed, Integer age){
         Ad ad = getAdOrThrow(id);
        ad.edit(actor,name,breed,age);
        //log
        auditService.register(actor.getId(),
                              AuditAction.AD_UPDATED,
                              Ad.class.getSimpleName());
        return ad;
    }

    public void uploadEvidence(Long adId, Evidence evidence,User actor){
        Ad ad = adRepository.findById(adId);
        ad.addEvidence(evidence);

        auditService.register( actor.getId(),
                               AuditAction.EVIDENCE_UPLOADED,
                               "Ad:" + ad.getId()
                );
    }
}


