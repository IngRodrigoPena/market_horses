package com.nocountry.markethorses.service;

import com.nocountry.markethorses.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class AdService {

    private final List<Ad>ads = new ArrayList<>();
    private Long nextId = 1L;

    public Ad createAd(User user, String horseName, String breed, Integer age){

        //Regla: solo SELLER puede crear anuncio
        if(user.getRole() != Role.SELLER){
            throw  new IllegalArgumentException("Solo un SELLER puede puede crear un anuncio.");
        }

        //Crear Horse
        Horse horse = new Horse();
        horse.setName(horseName);
        horse.setBreed(breed);
        horse.setAge(age);
        horse.setOwner(user);
        //create AD (nace en BORRADOR automaticamente)
        Ad ad = new Ad(horse,user);

        //simular ID autogenerado
        ad.setId(nextId++);
        /*Simulacion prueba 3
        ad.setStatus(AdStatus.PUBLICADO);*/
       //agrega a la lista
        ads.add(ad);
        return(ad);
    }

    private Ad findAdById(long id){
        return ads.stream()
                .filter(ad->ad.getId().equals(id))
                .findFirst()
                .orElseThrow(()->
                        new IllegalArgumentException("Anuncio NO encontrado"));
    }

    public Ad editAd(Long id, User actor, String name, String breed, Integer age){
         Ad ad = findAdById(id);
        ad.edit(actor,name,breed,age);
        return ad;
    }

}


