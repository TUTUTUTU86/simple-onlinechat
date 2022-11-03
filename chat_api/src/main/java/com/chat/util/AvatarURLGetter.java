package com.chat.util;

import com.chat.entities.AvatarStore;
import com.chat.repositories.AvatarStoreRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Component
public class AvatarURLGetter {

    @Autowired
    private AvatarStoreRepository avatarStoreRepository;


    @PostConstruct
    private void init(){
        try {
            addIfNotExists("avatar1", true);
            addIfNotExists("avatar2", false);
            addIfNotExists("avatar3", false);
            addIfNotExists("avatar0", true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception in avatar loading");
        }
    }

    public String getRandomAvatarURL() throws IOException {


        var availableAvatars = avatarStoreRepository.findByIsUsed(false);
        if (availableAvatars.size() != 0) {
            int randomIndex = (int) (Math.random() * availableAvatars.size());
            var avatar = availableAvatars.get(randomIndex);
            avatar.setUsed(true);
            avatarStoreRepository.save(avatar);
            return avatar.getBase64Code();
        }
        else {
            var defaultAvatars = avatarStoreRepository.findByIsDefault(true);
            int randomIndex = (int) (Math.random() * defaultAvatars.size());
            return defaultAvatars.get(randomIndex).getBase64Code();
        }
    }

    private void addIfNotExists(String name, boolean isDefault) throws IOException {
        if(avatarStoreRepository.findByName(name) == null){
            AvatarStore avatarStore = new AvatarStore();
            avatarStore.setName(name);
            avatarStore.setUsed(false);
            avatarStore.setDefault(isDefault);

            File file = new File("src/main/resources/avatars/"+name+".png");
            byte[] fileContent =
                    FileUtils.readFileToByteArray(file);
            avatarStore.setBase64Code(Base64.getEncoder().encodeToString(fileContent));

            avatarStoreRepository.save(avatarStore);
        }
    }
}













