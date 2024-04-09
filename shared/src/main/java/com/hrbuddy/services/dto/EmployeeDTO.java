package com.hrbuddy.services.dto;




import com.hrbuddy.persistence.entities.Employee;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeDTO {


    private Integer id;


    private String firstName;


    private String lastName;

    private String email;

    private String phoneNumber;


    private BigDecimal salary;

    private String hireDate;

    private Integer jobId;

    private Integer managerId;


    private Integer departmentId;

    private BigDecimal bonus;

    private BigDecimal deduction;


    public static List<EmployeeDTO> of(List<Employee> employees){
        return employees.stream().map(EmployeeDTO::of).toList();
    }



    public static EmployeeDTO of(Employee employee){
        if(employee == null)
            return null;
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .salary(employee.getSalary())
                .hireDate(employee.getHireDate().toString())
                .jobId(employee.getJob().getId())
                .managerId(employee.getManager() != null ? employee.getManager().getId() : null)
                .departmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null)
                .bonus(employee.getBonus())
                .deduction(employee.getDeduction())
                .build();
    }

    public static Employee toEmployee(EmployeeDTO employeeDTO){
        return Employee.builder()
                .id(employeeDTO.getId())
                .firstName(employeeDTO.firstName)
                .lastName(employeeDTO.lastName)
                .phoneNumber(employeeDTO.phoneNumber)
                .salary(employeeDTO.salary)
                .email(employeeDTO.email)
                .bonus(employeeDTO.bonus)
                .deduction(employeeDTO.deduction)
                .hireDate(LocalDate.parse(employeeDTO.hireDate))
                .build();
    }




}
