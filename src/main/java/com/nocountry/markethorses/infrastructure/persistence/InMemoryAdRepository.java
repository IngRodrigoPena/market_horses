package com.nocountry.markethorses.infrastructure.persistence;

import com.nocountry.markethorses.domain.Ad;
import com.nocountry.markethorses.domain.repository.AdRepository;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryAdRepository implements AdRepository {

    private final Map<Long,Ad> ads = new HashMap<>();

    @Override
    public Ad findById(Long id){
        Ad ad = ads.get(id);
        if(ad == null){
            throw new IllegalStateException("Ad NOT Found");
        }
        return ad;
    }

    @Override
    public void save(Ad ad){
        ads.put(ad.getId(),ad);
    }
}
