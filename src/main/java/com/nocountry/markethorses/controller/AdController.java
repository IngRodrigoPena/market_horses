package com.nocountry.markethorses.controller;

import com.nocountry.markethorses.domain.Ad;
import com.nocountry.markethorses.domain.Role;
import com.nocountry.markethorses.domain.User;
import com.nocountry.markethorses.dto.ApproveRequest;
import com.nocountry.markethorses.dto.EvidenceRequest;
import com.nocountry.markethorses.dto.UpdateAdRequest;
import com.nocountry.markethorses.service.AdService;
import com.nocountry.markethorses.service.VerificationService;
import org.springframework.web.bind.annotation.*;
import com.nocountry.markethorses.dto.CreateAdRequest;

import java.util.List;

@RestController
public class AdController {

    private final AdService adService;
    private final VerificationService verificationService;

    public AdController(AdService adService,
                        VerificationService verificationService){
        this.adService = adService;
        this.verificationService = verificationService;
    }

    @PostMapping("/ads")
    public Ad createAd(@RequestBody CreateAdRequest request){
        return adService.createAd(request);
    }

    @PutMapping("/ads/{id}")
    public Ad editAd(@PathVariable Long id,
                     @RequestBody UpdateAdRequest request){

        User actor = buildUser(request.getUserId());

        return adService.editAd(id,actor,request);
    }

    private User buildUser(Long userId){
        User  user = new User();
        user.setId(userId);
        user.setName("Seller Demo");
        user.setRole(Role.SELLER);
        return user;
    }

    @PostMapping("/ads/{id}/submit")
    public void submit(@PathVariable Long id,
                   @RequestParam Long userId){

                User actor = buildUser(userId);

                adService.submitForVerification(id,actor);
    }

    @PostMapping("/ads/{id}/evidence")
    public void uploadEvidence(
            @PathVariable Long id,
            @RequestBody EvidenceRequest request
    ) {

        User actor = buildUser(request.getUserId());

        adService.uploadEvidence(
                id,
                actor,
                request.getFileUrl(),
                request.getType()
        );
    }


    @GetMapping("/ads/{id}")
    public Ad getId(@PathVariable Long id){
      return adService.getAdById(id);
    }

    @GetMapping("/ads")
    public List<Ad> getAllAds(){
        return adService.getAllAds();
    }

   @PostMapping("/ads/{id}/approve")
   //public void approveAd(@PathVariable Long id,
    public void approve(@PathVariable Long id,
                        @RequestBody ApproveRequest request){
        User actor = buildAdmin(request.getUserId());
       verificationService.approveAd(id, actor);
    }

    private User buildAdmin(Long userId){
        User user = new User();
        user.setId(userId);
        user.setName("Admin Demo");
        user.setRole(Role.ADMIN);
        return user;
    }

    @PostMapping("/ads/{id}/reject")
    public void reject(@PathVariable Long id,
                       @RequestBody ApproveRequest request){
        User actor = buildAdmin(request.getUserId());
        verificationService.rejectAd(id,actor);
    }
}
