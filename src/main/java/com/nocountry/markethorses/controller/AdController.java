package com.nocountry.markethorses.controller;

import com.nocountry.markethorses.domain.Ad;
import com.nocountry.markethorses.domain.Role;
import com.nocountry.markethorses.domain.User;
import com.nocountry.markethorses.service.AdService;
import com.nocountry.markethorses.service.VerificationService;
import org.springframework.web.bind.annotation.*;

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
        User user = buildUser(request.getUserId());
        return adService.createAd(
                user,
                request.getHorseName(),
                request.getBreed(),
                request.getAge()
        );
    }

    @PutMapping("/ads/{id}")
    public Ad editAd(@PathVariable Long id,
                     @RequestBody EditAdRequest request){
        User actor = buildUser(request.getUserId());

        return adService.editAd(
                id,
                actor,
                request.getHorseName(),
                request.getBreed(),
                request.getAge());
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
    public void uploadEvidence(@PathVariable long id,
                               @RequestParam Long userId,
                               @RequestParam String url){

        User actor = buildUser(userId);

        adService.uploadEvidence(id,url,actor);
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
    public void approvedAd(@PathVariable Long id,
                           @RequestParam Long userId){
        User actor = buildAdmin(userId);
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
    public void rejectAd(@PathVariable Long id,
                       @RequestParam Long userId){
        User actor = buildAdmin(userId);
        verificationService.rejectAd(id,actor);
    }
}
