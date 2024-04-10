package services;


import com.hrbuddy.services.dto.JobDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface JobWebService {

    @WebMethod
    List<JobDTO> getAllJobs(@WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    JobDTO getJob(@WebParam(name = "id") int id, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    JobDTO createJob(@WebParam(name = "job") JobDTO job, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    JobDTO updateJob(@WebParam(name = "job") JobDTO job, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    String deleteJob(@WebParam(name = "id") int id, @WebParam(header = true, name = "jwtToken") String token);
}
