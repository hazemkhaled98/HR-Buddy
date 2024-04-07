package com.hrbuddy.persistence.repositories;

import com.hrbuddy.persistence.entities.Job;
import jakarta.persistence.EntityManager;

public class JobRepository extends Repository<Job>{

    public JobRepository(EntityManager entityManager){
        super(entityManager);
        setType(Job.class);
    }
}
