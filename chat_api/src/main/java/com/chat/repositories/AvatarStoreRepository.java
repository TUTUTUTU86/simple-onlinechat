package com.chat.repositories;

import com.chat.entities.AvatarStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AvatarStoreRepository extends CrudRepository<AvatarStore, Long> {
    List<AvatarStore> findByIsUsed(boolean IsUsed);
    List<AvatarStore> findByIsDefault(boolean isDefault);
    AvatarStore findByName(String name);
}
