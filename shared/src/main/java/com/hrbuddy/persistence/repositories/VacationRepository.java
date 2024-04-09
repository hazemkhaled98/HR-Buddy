package com.hrbuddy.persistence.repositories;

import com.hrbuddy.persistence.entities.Vacation;
import jakarta.persistence.EntityManager;

import java.util.List;

public class VacationRepository extends Repository<Vacation> {


    public VacationRepository(EntityManager entityManager) {
        super(entityManager);
        setType(Vacation.class);
    }

    public List<Vacation> getAllByEmployeeId(int employeeId) {
        return entityManager.createQuery("select v from Vacation v where v.employee.id = :employeeId", Vacation.class)
                .setParameter("employeeId", employeeId)
                .getResultList();
    }

    public void deleteByEmployeeId(int employeeId) {
        entityManager.createQuery("delete from Vacation v where v.employee.id = :employeeId")
                .setParameter("employeeId", employeeId)
                .executeUpdate();
    }
}
