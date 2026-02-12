package com.nocountry.markethorses.controller;

import com.nocountry.markethorses.domain.Ad;
import com.nocountry.markethorses.domain.Role;
import com.nocountry.markethorses.domain.User;
import com.nocountry.markethorses.service.AdService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdController {

    private final AdService adService;

    public AdController(AdService adService){
        this.adService = adService;
    }

    @PostMapping("/ads")
    public Ad createAd(@RequestBody CreateAdRequest request){

        //Usuario temporal (simulacion)
        User user = new User();
        user.setId(request.getUserId());
        user.setName("Seller Demo");
        user.setRole(Role.SELLER);

        return adService.createAd(
                user,
                request.getHorseName(),
                request.getBreed(),
                request.getAge()
        );
    }
}
