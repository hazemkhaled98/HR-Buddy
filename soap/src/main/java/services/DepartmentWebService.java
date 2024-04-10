package services;

import com.hrbuddy.services.dto.DepartmentDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface DepartmentWebService {
    @WebMethod
    List<DepartmentDTO> getAllDepartments(@WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    DepartmentDTO getDepartment(@WebParam(name = "id") int id, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    DepartmentDTO createDepartment(@WebParam(name = "department") DepartmentDTO department, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    DepartmentDTO updateDepartment(@WebParam(name = "department") DepartmentDTO department, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    String deleteDepartment(@WebParam(name = "id") int id, @WebParam(header = true, name = "jwtToken") String token);

}
