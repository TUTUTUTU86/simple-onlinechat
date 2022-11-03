package com.chat.entities;


import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class AvatarStore {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String base64Code;

    @NotNull
    private boolean isUsed;

    @NotNull
    private boolean isDefault;

}
