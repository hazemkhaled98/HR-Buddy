package com.hrbuddy.services.dto;

import com.hrbuddy.persistence.entities.Job;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;




@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobDTO {
    private Integer id;

    private String title;

    private BigDecimal minSalary;

    private BigDecimal maxSalary;



    public static List<JobDTO> of(List<Job> jobs){
        return jobs.stream().map(JobDTO::of).toList();
    }



    public static JobDTO of(Job job){
        if(job == null)
            return null;
        return JobDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .build();
    }

    public static Job toJob(JobDTO jobDTO){
        return Job.builder()
                .id(jobDTO.id)
                .title(jobDTO.title)
                .minSalary(jobDTO.minSalary)
                .maxSalary(jobDTO.maxSalary)
                .build();
    }
}
