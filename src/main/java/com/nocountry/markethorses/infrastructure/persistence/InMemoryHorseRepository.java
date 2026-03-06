package com.nocountry.markethorses.infrastructure.persistence;

import com.nocountry.markethorses.domain.Horse;
import com.nocountry.markethorses.domain.repository.HorseRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryHorseRepository implements HorseRepository {

    private final Map<Long, Horse> horses = new HashMap<>();

    @Override
    public Horse findById(Long id){
        Horse horse = horses.get(id);

        if(horse == null){
            throw new IllegalStateException("Horse not found");
        }
        return horse;
    }
}
