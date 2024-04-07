package com.hrbuddy.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Size(max = 45)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Size(max = 45)
    @NotNull
    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Size(max = 45)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 45)
    private String phoneNumber;

    @NotNull
    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @NotNull
    @Column(name = "hire_date", nullable = false)
    private Instant hireDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    @ToString.Exclude
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @ToString.Exclude
    private Employee manager;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @ToString.Exclude
    private Department department;

    @NotNull
    @Column(name = "bonus", nullable = false, precision = 10, scale = 2)
    private BigDecimal bonus;

    @NotNull
    @Column(name = "deduction", nullable = false, precision = 10, scale = 2)
    private BigDecimal deduction;

    @OneToMany(mappedBy = "employee")
    @ToString.Exclude
    private Set<Attendance> attendances = new LinkedHashSet<>();

    @OneToOne(mappedBy = "departmentManager")
    private Department managedDepartment;

    @OneToMany(mappedBy = "manager")
    @ToString.Exclude
    private Set<Employee> managedEmployees = new LinkedHashSet<>();

}