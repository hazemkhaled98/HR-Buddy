package com.hrbuddy.services.dto;

import com.hrbuddy.persistence.entities.Attendance;

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
@XmlRootElement(name = "attendance")
@XmlAccessorType(XmlAccessType.FIELD)
public class AttendanceDTO {

    private Integer id;

    private String date;

    private String status;

    private Integer employeeId;



    public static List<AttendanceDTO> of(List<Attendance> jobs){
        return jobs.stream().map(AttendanceDTO::of).toList();
    }



    public static AttendanceDTO of(Attendance attendance){
        if(attendance == null)
            return null;
        return AttendanceDTO.builder()
                .id(attendance.getId())
                .date(attendance.getDate().toString())
                .status(attendance.getStatus())
                .employeeId(attendance.getEmployee().getId())
                .build();
    }

    public static Attendance toAttendance(AttendanceDTO attendanceDTO){
        return Attendance.builder()
                .id(attendanceDTO.getId())
                .date(LocalDate.parse(attendanceDTO.getDate()))
                .status(attendanceDTO.getStatus())
                .build();
    }

}
