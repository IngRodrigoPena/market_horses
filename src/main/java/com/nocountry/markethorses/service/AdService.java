package com.nocountry.markethorses.service;

import com.nocountry.markethorses.domain.*;
import com.nocountry.markethorses.domain.audit.AuditAction;
import com.nocountry.markethorses.domain.repository.AdRepository;
import com.nocountry.markethorses.domain.repository.HorseRepository;
import com.nocountry.markethorses.domain.repository.UserRepository;
import com.nocountry.markethorses.dto.CreateAdRequest;
import com.nocountry.markethorses.dto.UpdateAdRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final HorseRepository horseRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;
    private Long evidenceId = 1L;

    public AdService(AdRepository adRepository,
                     HorseRepository horseRepository,
                     UserRepository userRepository,
                     AuditService auditService){
        this.adRepository = adRepository;
        this.horseRepository = horseRepository;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    public Ad createAd(CreateAdRequest request){

        Horse horse = horseRepository.findById(request.getHorseId());
        User seller = userRepository.findById(request.getSellerId());
        Long id = adRepository.nextId();

        Ad ad = new Ad(id,horse,seller);

        Ad savedAd = adRepository.save(ad);

        //auditoria
        auditService.register(
                seller.getId(),
                AuditAction.AD_CREATED,
                "Ad:" + savedAd.getId()
        );

        //return adRepository.save(ad);
        return savedAd;

    }

    private Ad getAdOrThrow(Long id){
        return adRepository.findById(id);
    }

     public Ad editAd(Long id, User actor, UpdateAdRequest request){

         Ad ad = getAdOrThrow(id);

        ad.edit(actor,
                request.getHorseName(),
                request.getBreed(),
                request.getAge()
        );

        adRepository.save(ad);
        //auditoria
        auditService.register(actor.getId(),
                              AuditAction.AD_UPDATED,
                              "Ad:" + ad.getId());
        return ad;
    }

    public void uploadEvidence(Long adId, User actor, String fileUrl, String type) {

        Ad ad = getAdOrThrow(adId);

        EvidenceType evidenceType = EvidenceType.valueOf(type);

        Evidence evidence = new Evidence(
                evidenceId++,
                //evidenceType,
                EvidenceType.valueOf(type),
                fileUrl
        );

        ad.addEvidence(actor, evidence);

        adRepository.save(ad);

        auditService.register(
                actor.getId(),
                AuditAction.EVIDENCE_UPLOADED,
                "Ad:" + ad.getId()
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

}


