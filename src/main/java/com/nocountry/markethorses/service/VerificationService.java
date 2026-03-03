package com.nocountry.markethorses.service;

import com.nocountry.markethorses.domain.Ad;
import com.nocountry.markethorses.domain.AdStatus;
import com.nocountry.markethorses.domain.User;
import com.nocountry.markethorses.domain.Verification;
import com.nocountry.markethorses.domain.audit.AuditAction;
import com.nocountry.markethorses.domain.repository.AdRepository;
import com.nocountry.markethorses.domain.repository.VerificationRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    private final AdRepository adRepository;
    private final VerificationRepository verificationRepository;
    private final AuditService auditService;

    private Long nextId=1L;

    public VerificationService(
            AdRepository adRepository,
            VerificationRepository verificationRepository,
            AuditService auditService    ){

        this.adRepository = adRepository;
        this.verificationRepository = verificationRepository;
        this.auditService = auditService;
    }

    public void startVerification(Long adId, User actor){

        //1.- Obtener Ad
        Ad ad = getAdOrThrow(adId);

        if(ad.getStatus() != AdStatus.EN_VERIFICACION){
            throw new IllegalStateException("Ad debe estar EN_VERIFICACION");
        }

        Verification verification = new Verification(
                nextId++,
                ad.getId()
        );

        verificationRepository.save(verification);

        auditService.register(
                actor.getId(),
                AuditAction.VERIFICATION_STARTED,
                "Ad:" + ad.getId() + "Horse:" +ad.getHorse().getName()
         );
    }

    private Ad getAdOrThrow(Long id){
        return adRepository.findById(id);
    }

    public void approveAd(Long adId, User actor){

        Ad ad = getAdOrThrow(adId);

        ad.approve(actor);

        adRepository.save(ad);

        auditService.register(
                actor.getId(),
                AuditAction.AD_APPROVED,
                "Ad:" + ad.getId() + "Horse:" +ad.getHorse().getName()
        );
    }

    public void rejectAd(Long adId, User actor){

        Ad ad = getAdOrThrow(adId);

        ad.reject(actor);

        adRepository.save(ad);

        auditService.register(
                actor.getId(),
                AuditAction.AD_REJECTED,
                "Ad:" + ad.getId() + "Horse:" +ad.getHorse().getName()
        );
    }
}
