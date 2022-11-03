package com.chat.user;


import com.chat.entities.User;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name="users_login_information")
@ToString
public class UserLoginInformation implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String authority;

    @NotNull
    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt;

    @OneToOne(
            mappedBy = "userLoginInformation",
            cascade = CascadeType.ALL
    )
    private User user;
}
