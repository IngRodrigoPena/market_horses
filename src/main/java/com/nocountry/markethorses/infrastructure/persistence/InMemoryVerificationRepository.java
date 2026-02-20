package com.nocountry.markethorses.infrastructure.persistence;

import com.nocountry.markethorses.domain.Verification;
import com.nocountry.markethorses.domain.repository.VerificationRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryVerificationRepository implements VerificationRepository {

    private final Map<Long, Verification> verifications = new HashMap<>();

    public void save(Verification verification){
        verifications.put(verification.getId(),verification);
    }
}
