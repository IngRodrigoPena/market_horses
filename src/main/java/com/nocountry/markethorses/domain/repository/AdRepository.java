package com.nocountry.markethorses.domain.repository;

import com.nocountry.markethorses.domain.Ad;

import java.util.List;

public interface AdRepository {
    Ad findById(Long id);
    Ad save(Ad ad);
    List<Ad> findAll();
    Long nextId();
}
