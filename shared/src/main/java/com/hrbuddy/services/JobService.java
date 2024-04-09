package com.hrbuddy.services;

import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.Job;
import com.hrbuddy.persistence.repositories.JobRepository;
import com.hrbuddy.services.dto.JobDTO;

import java.util.List;
import java.util.NoSuchElementException;
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

    public static JobDTO getJob(int id) {
        return Database.doInTransaction(entityManager -> {
           JobRepository jobRepository = new JobRepository(entityManager);
            Job job = jobRepository.get(id)
                    .orElseThrow(() -> new NoSuchElementException("There is no job with id: " + id));
            return JobDTO.of(job);
        });
    }

    public static JobDTO updateJob(JobDTO dto){
        return Database.doInTransaction(entityManager -> {
            JobRepository jobRepository = new JobRepository(entityManager);
            Job job = jobRepository.get(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("There is no job with id: " + dto.getId()));
            return JobDTO.of(jobRepository.update(JobDTO.toJob(dto)));
        });
    }

    public static void deleteJob(int id){
        try{
            Database.doInTransactionWithoutResult(entityManager -> {
                JobRepository jobRepository = new JobRepository(entityManager);
                jobRepository.deleteById(id);
            });
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't delete job with id: " + id + ". You need to update all the employees record associated with this job first.");
        }
    }
}
