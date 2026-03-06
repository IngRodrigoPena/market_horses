package com.nocountry.markethorses.domain.repository;

import com.nocountry.markethorses.domain.Horse;

public interface HorseRepository {

    Horse findById(Long id);
}
