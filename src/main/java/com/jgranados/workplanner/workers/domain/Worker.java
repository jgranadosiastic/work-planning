package com.jgranados.workplanner.workers.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "worker")
@Data
@NoArgsConstructor
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWorker;

    @NotBlank
    @Size(max = 35)
    @Column(length = 35)
    private String firstName;

    @NotBlank
    @Size(max = 35)
    @Column(length = 35)
    private String lastName;
}
