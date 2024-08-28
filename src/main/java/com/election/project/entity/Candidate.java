package com.election.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String gender;
    private Integer age;

    @ManyToOne // This establishes the relationship
    @JoinColumn(name = "party_id") // This specifies the foreign key column
    private Party party;

    // implement photo
}
