package com.chat.entities;


import com.chat.user.UserLoginInformation;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String nickname;

    @Lob
    private String avatarURL;

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.ALL
    )
    @JoinColumn(unique = true)
    private UserLoginInformation userLoginInformation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSeen;

}
