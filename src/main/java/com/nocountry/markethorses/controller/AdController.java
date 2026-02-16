package com.nocountry.markethorses.controller;

import com.nocountry.markethorses.domain.Ad;
import com.nocountry.markethorses.domain.Role;
import com.nocountry.markethorses.domain.User;
import com.nocountry.markethorses.service.AdService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdController {

    private final AdService adService;

    public AdController(AdService adService){
        this.adService = adService;
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
        actor.setName("SellerDemo");
        actor.setRole(Role.SELLER);

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
}
