package com.nocountry.markethorses.service;

import com.nocountry.markethorses.domain.Ad;
import com.nocountry.markethorses.domain.User;
import com.nocountry.markethorses.domain.Verification;
import com.nocountry.markethorses.domain.audit.AuditAction;
import com.nocountry.markethorses.domain.repository.AdRepository;
import com.nocountry.markethorses.domain.repository.VerificationRepository;

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
        Ad ad = adRepository.findById(adId);
        //2.- Ejecutar dominio
        ad.submitForVerification(actor);
        //3.- Crear Verificacion
        Verification verification = new Verification(
                nextId++,
                ad.getId()
        );

        verificationRepository.save(verification);

        //4.- Registrar auditoria
        auditService.register(
                actor.getId(),
                AuditAction.AD_SUBMITTED_FOR_VERIFICATION,
                "Ad:" + ad.getId()
        );

        auditService.register(
                actor.getId(),
                AuditAction.VERIFICATION_STARTED,
                "Ad:" + ad.getId()
         );
    }
}
