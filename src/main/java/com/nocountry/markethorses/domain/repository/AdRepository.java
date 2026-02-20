package com.nocountry.markethorses.domain.repository;

import com.nocountry.markethorses.domain.Ad;

public interface AdRepository {
    Ad findById(Long id);
    void save(Ad ad);
}
