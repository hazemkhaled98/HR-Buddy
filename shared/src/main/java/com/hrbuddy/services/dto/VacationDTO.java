package com.hrbuddy.services.dto;

import com.hrbuddy.persistence.entities.Attendance;
import com.hrbuddy.persistence.entities.Vacation;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@XmlRootElement(name = "vacation")
@XmlAccessorType(XmlAccessType.FIELD)
public class VacationDTO {

    private Integer id;

    private String from;

    private String to;

    private Integer employeeId;


    public static List<VacationDTO> of(List<Vacation> vacations){
        return vacations.stream().map(VacationDTO::of).toList();
    }


    public static VacationDTO of(Vacation vacation){

        if(vacation == null)
            return null;

        return VacationDTO.builder()
                .id(vacation.getId())
                .from(vacation.getFrom().toString())
                .to(vacation.getTo().toString())
                .employeeId(vacation.getEmployee().getId())
                .build();
    }

    public static Vacation toVacation(VacationDTO vacationDTO){
        return Vacation.builder()
                .id(vacationDTO.id)
                .from(LocalDate.parse(vacationDTO.from))
                .to(LocalDate.parse(vacationDTO.to))
                .build();
    }
}
