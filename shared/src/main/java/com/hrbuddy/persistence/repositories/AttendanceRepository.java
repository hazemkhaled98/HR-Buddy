package com.hrbuddy.persistence.repositories;

import com.hrbuddy.persistence.entities.Attendance;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AttendanceRepository extends Repository<Attendance> {


    public AttendanceRepository(EntityManager entityManager){
        super(entityManager);
        setType(Attendance.class);
    }


    public List<Attendance> getAllByEmployeeId(int employeeId) {
        return entityManager.createQuery("SELECT a FROM Attendance a WHERE a.employee.id = :employeeId", Attendance.class)
                .setParameter("employeeId", employeeId)
                .getResultList();
    }

    public void deleteByEmployeeId(int employeeId) {
        String sql = "DELETE FROM attendance WHERE employee_id = ?";
        entityManager.createNativeQuery(sql).setParameter(1, employeeId).executeUpdate();
    }

}
