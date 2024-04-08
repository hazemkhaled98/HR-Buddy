package com.hrbuddy.persistence.repositories;

import com.hrbuddy.persistence.entities.Employee;
import jakarta.persistence.EntityManager;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository extends Repository<Employee> {

    public EmployeeRepository(EntityManager entityManager){
        super(entityManager);
        setType(Employee.class);
    }


    public List<Employee> getAll(int departmentId, int jobId, int managerId, int offset, int limit){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (departmentId != 0) {
            predicates.add(criteriaBuilder.equal(root.get("department").get("id"), departmentId));
        }
        if (jobId != 0) {
            predicates.add(criteriaBuilder.equal(root.get("job").get("id"), jobId));
        }
        if (managerId != 0) {
            predicates.add(criteriaBuilder.equal(root.get("manager").get("id"), managerId));
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        offset = Math.max(offset - 1, 0);
        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(offset);

        if(limit > 0){
            query.setMaxResults(limit);
        }
        return query.getResultList();
    }
}
