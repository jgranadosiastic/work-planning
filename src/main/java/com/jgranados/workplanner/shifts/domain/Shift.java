package com.jgranados.workplanner.shifts.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jgranados.workplanner.workers.domain.Worker;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "shift")
@Data
@NoArgsConstructor
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idShift;

    @NotNull
    @Column
    private LocalDateTime startDate;

    @NotNull
    @Column
    private LocalDateTime endDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "worker")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Worker worker;
}
