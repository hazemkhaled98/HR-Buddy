package com.hrbuddy.persistence.repositories;

import com.hrbuddy.persistence.entities.Attendance;
import jakarta.persistence.EntityManager;

public class AttendanceRepository extends Repository<Attendance> {


    public AttendanceRepository(EntityManager entityManager){
        super(entityManager);
        setType(Attendance.class);
    }

    public void deleteByEmployeeId(int employeeId) {
        String sql = "DELETE FROM attendance WHERE employee_id = ?";
        entityManager.createNativeQuery(sql).setParameter(1, employeeId).executeUpdate();
    }
}
