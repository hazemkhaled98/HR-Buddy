package com.hrbuddy.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "job")
public class Job {
    @Id
    @Column(name = "job_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @NotNull
    @Column(name = "min_salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal minSalary;

    @NotNull
    @Column(name = "max_salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal maxSalary;

    @OneToMany(mappedBy = "job")
    @ToString.Exclude
    private Set<Employee> employees = new LinkedHashSet<>();

}