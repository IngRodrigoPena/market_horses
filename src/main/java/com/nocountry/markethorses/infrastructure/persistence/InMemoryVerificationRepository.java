package com.nocountry.markethorses.infrastructure.persistence;

import com.nocountry.markethorses.domain.Verification;
import com.nocountry.markethorses.domain.repository.VerificationRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryVerificationRepository implements VerificationRepository {

    private final Map<Long, Verification> verifications = new HashMap<>();

    @Override
    public void save(Verification verification){

        verifications.put(verification.getId(),verification);
    }

    @Override
    public Verification findById(Long id) {
        Verification verification = verifications.get(id);

        if(verification == null){
            throw new IllegalStateException("Verificacion NO encontrada");
        }

        return verification;
    }
}
