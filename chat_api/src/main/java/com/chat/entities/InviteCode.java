package com.chat.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class InviteCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String code;

    @ManyToOne(
            fetch= FetchType.EAGER,
            optional = false
    )
    @JoinColumn(name = "invited_by_id")
    private User invitedBy;

}
