package com.nocountry.markethorses.service;

import com.nocountry.markethorses.domain.Ad;
import com.nocountry.markethorses.domain.Horse;
import com.nocountry.markethorses.domain.Role;
import com.nocountry.markethorses.domain.User;
import org.springframework.stereotype.Service;

@Service
public class AdService {

    public Ad createAd(User user, String horseName, String breed, Integer age){

        //Regla: solo SELLER puede crear anuncio
        if(user.getRole() != Role.SELLER){
            throw  new IllegalArgumentException("Solo un SELER puede puede crear un anuncio.");
        }

        //Crear Horse
        Horse horse = new Horse();
        horse.setName(horseName);
        horse.setBreed(breed);
        horse.setAge(age);
        horse.setOwner(user);

        //create AD (nace en BORRADOR automaticamente)
        return new Ad(horse);

    }

}


