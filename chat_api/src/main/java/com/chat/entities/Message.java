package com.chat.entities;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Message {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(
            fetch= FetchType.EAGER,
            optional = false
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private String body;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


}
