package services.implementations;

import com.hrbuddy.services.JobService;
import com.hrbuddy.services.dto.JobDTO;
import jakarta.jws.WebService;
import security.SecurityManager;
import services.JobWebService;

import java.util.List;

@WebService(endpointInterface = "services.JobWebService")
public class JobWebServiceImpl implements JobWebService {

    @Override
    public List<JobDTO> getAllJobs(String token) {
        SecurityManager.authorizeUser(token);
        return JobService.getAllJobs();
    }

    @Override
    public JobDTO getJob(int id, String token) {
        SecurityManager.authorizeUser(token);
        return JobService.getJob(id);
    }

    @Override
    public JobDTO createJob(JobDTO job, String token) {
        SecurityManager.authorizeAdmin(token);
        return JobService.createJob(job);
    }

    @Override
    public JobDTO updateJob(JobDTO job, String token) {
        SecurityManager.authorizeAdmin(token);
        return JobService.updateJob(job);
    }

    @Override
    public String deleteJob(int id, String token) {
        SecurityManager.authorizeAdmin(token);
        JobService.deleteJob(id);
        return "Job with id: " + id + " was deleted successfully";
    }

}
