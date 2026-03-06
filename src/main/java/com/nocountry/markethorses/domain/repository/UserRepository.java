package com.nocountry.markethorses.domain.repository;

import com.nocountry.markethorses.domain.User;

public interface UserRepository {
    User findById(Long id);
}
