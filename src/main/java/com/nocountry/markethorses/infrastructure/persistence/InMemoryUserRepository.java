package com.nocountry.markethorses.infrastructure.persistence;

import com.nocountry.markethorses.domain.User;
import com.nocountry.markethorses.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUserRepository  implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User findById(Long id){
        User user = users.get(id);

        if(user == null){
            throw new IllegalStateException("User Not Found");
        }

        return user;
    }
}
