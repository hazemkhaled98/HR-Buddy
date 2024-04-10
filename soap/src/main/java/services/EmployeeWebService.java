package services;

import com.hrbuddy.services.dto.EmployeeDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface EmployeeWebService {

    @WebMethod
    List<EmployeeDTO> getAllEmployees(@WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    EmployeeDTO getEmployee(@WebParam(name = "id") int id, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    EmployeeDTO createEmployee(@WebParam(name = "employee") EmployeeDTO employee, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    EmployeeDTO updateEmployee(@WebParam(name = "employee") EmployeeDTO employee, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    String deleteEmployee(@WebParam(name = "id") int id, @WebParam(header = true, name = "jwtToken") String token);

}
