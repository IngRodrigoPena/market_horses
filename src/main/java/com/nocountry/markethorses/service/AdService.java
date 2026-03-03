package com.nocountry.markethorses.service;

import com.nocountry.markethorses.domain.*;
import com.nocountry.markethorses.domain.audit.AuditAction;
import com.nocountry.markethorses.domain.repository.AdRepository;
import org.springframework.stereotype.Service;

import javax.imageio.IIOException;
import java.util.List;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final AuditService auditService;
    private Long nextId = 1L;
    private Long evidenceId = 1L;

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
                              "Ad:" + ad.getId());
        return(ad);
    }

    private Ad getAdOrThrow(Long id){
        return adRepository.findById(id);
    }

    public Ad editAd(Long id, User actor, String name, String breed, Integer age){
         Ad ad = getAdOrThrow(id);
        ad.edit(actor,name,breed,age);
        adRepository.save(ad);
        //log
        auditService.register(actor.getId(),
                              AuditAction.AD_UPDATED,
                              "Ad:" + ad.getId());
        return ad;
    }

    public void uploadEvidence(Long adId,String url,User actor){

        //Ad ad = adRepository.findById(adId);
        Ad ad = getAdOrThrow(adId);

        Evidence evidence = new Evidence( evidenceId++,
                                          EvidenceType.PHOTO,
                                          url
        );

        ad.addEvidence(evidence);
        adRepository.save(ad);

        auditService.register(
                actor.getId(),
                AuditAction.EVIDENCE_UPLOADED,
                "Ad:" +ad.getId()
        );
    }

    public void submitForVerification(Long adId,User  actor){

        Ad ad = getAdOrThrow(adId);

        ad.submitForVerification(actor);
        adRepository.save(ad);

        auditService.register(
                actor.getId(),
                AuditAction.AD_SUBMITTED_FOR_VERIFICATION,
                "Ad:" + ad.getId()
        );
    }

    public Ad getAdById(Long id){
        return adRepository.findById(id);
    }

    public List<Ad> getAllAds(){
        return adRepository.findAll();
    }

    /*public Ad rejectAd(User admin, Long adId){

        if(admin.getRole() != Role.ADMIN){
            throw  new IllegalStateException("Solo ADMIN puede rechazar");
        }

        Ad ad = adRepository.findById(adId);

        if(ad.getStatus() != AdStatus.EN_VERIFICACION){
            throw  new IllegalStateException("Solo ads EN_VERIFICACION pueden rechazarse");
        }

        ad.setStatus(AdStatus.RECHAZADO);

        adRepository.save(ad);

        return ad;
    }*/
}


