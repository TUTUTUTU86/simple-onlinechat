package com.chat.repositories;

import com.chat.entities.InviteCode;
import com.chat.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface InviteCodeRepository extends CrudRepository<InviteCode, Long> {
    InviteCode findByInvitedBy(User user);
    InviteCode findByCode(String code);
}
