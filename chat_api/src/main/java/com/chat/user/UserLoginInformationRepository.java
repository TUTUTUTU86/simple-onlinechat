package com.chat.user;

import org.springframework.data.repository.CrudRepository;

public interface UserLoginInformationRepository
        extends CrudRepository<UserLoginInformation, Long> {
    public UserLoginInformation findByUsername(String username);

}
