package com.hrbuddy.rest.beans;


import jakarta.ws.rs.QueryParam;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class EmployeesFilters {

    @QueryParam("departmentId")
    private int departmentId;
    @QueryParam("jobId")
    private int jobId;
    @QueryParam("managerId")
    private int managerId;
    @QueryParam("offset")
    private int offset;
    @QueryParam("limit")
    private int limit;
    @QueryParam("fields")
    private String fieldsParam;
}
