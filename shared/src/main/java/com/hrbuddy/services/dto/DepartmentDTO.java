package com.hrbuddy.services.dto;


import com.hrbuddy.persistence.entities.Department;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@XmlRootElement(name = "department")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepartmentDTO {

    private Integer id;

    private String name;

    private Integer managerId;

    private String location;


    public static List<DepartmentDTO> of(List<Department> departments){
        return departments.stream().map(DepartmentDTO::of).toList();
    }



    public static DepartmentDTO of(Department department){
        if(department == null)
            return null;
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .managerId(department.getDepartmentManager().getId())
                .location(department.getLocation())
                .build();
    }

    public static Department toDepartment(DepartmentDTO departmentDTO){
        return Department.builder()
                .id(departmentDTO.getId())
                .name(departmentDTO.getName())
                .location(departmentDTO.getLocation())
                .build();
    }

}
