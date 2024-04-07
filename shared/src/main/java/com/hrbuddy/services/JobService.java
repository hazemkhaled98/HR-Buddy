package com.hrbuddy.services;

import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.Job;
import com.hrbuddy.persistence.repositories.JobRepository;
import com.hrbuddy.services.dto.JobDTO;

import java.util.List;
import java.util.Optional;
public class JobService {

    private JobService() {
    }

    public static JobDTO createJob(JobDTO dto) {
        return Database.doInTransaction(entityManager -> {
            JobRepository jobRepository = new JobRepository(entityManager);
            return JobDTO.of(jobRepository.create(JobDTO.toJob(dto)));
        });
    }

    public static List<JobDTO> getAllJobs() {
        return Database.doInTransaction(entityManager -> {
            JobRepository jobRepository = new JobRepository(entityManager);
            return JobDTO.of(jobRepository.getAll());
        });
    }

    public static Optional<JobDTO> getJob(int id) {
        return Database.doInTransaction(entityManager -> {
           JobRepository jobRepository = new JobRepository(entityManager);
            Optional<Job> job = jobRepository.get(id);
            return job.map(JobDTO::of);
        });
    }

    public static JobDTO updateJob(JobDTO dto){
        return Database.doInTransaction(entityManager -> {
            JobRepository jobRepository = new JobRepository(entityManager);
            Optional<Job> job = jobRepository.get(dto.getId());
            if(job.isEmpty())
                throw new IllegalArgumentException("Job was not found");
            return JobDTO.of(jobRepository.update(JobDTO.toJob(dto)));
        });
    }

    public static void deleteJob(int id){
        Database.doInTransactionWithoutResult(entityManager -> {
            JobRepository jobRepository = new JobRepository(entityManager);
            jobRepository.deleteById(id);
        });
    }
}
