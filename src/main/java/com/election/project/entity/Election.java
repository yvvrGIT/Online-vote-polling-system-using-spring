package com.election.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "elections")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    private String winParty;
    private String winCandidate;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ElectionStatus status;

    public enum ElectionStatus {
        ACTIVE, ENDED
    }
}
