package services.implementations;

import com.hrbuddy.services.DepartmentService;
import com.hrbuddy.services.dto.DepartmentDTO;
import jakarta.jws.WebService;
import security.SecurityManager;
import services.DepartmentWebService;

import java.util.List;

@WebService(endpointInterface = "services.DepartmentWebService")
public class DepartmentWebServiceImpl implements DepartmentWebService {
    @Override
    public List<DepartmentDTO> getAllDepartments(String token) {
        SecurityManager.authorizeUser(token);
        return DepartmentService.getAllDepartments();
    }

    @Override
    public DepartmentDTO getDepartment(int id, String token) {
        SecurityManager.authorizeUser(token);
        return DepartmentService.getDepartment(id);
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO department, String token) {
        SecurityManager.authorizeAdmin(token);
        return DepartmentService.createDepartment(department);
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO department, String token) {
        SecurityManager.authorizeAdmin(token);
        return DepartmentService.updateDepartment(department);
    }

    @Override
    public String deleteDepartment(int id, String token) {
        SecurityManager.authorizeAdmin(token);
        DepartmentService.deleteDepartment(id);
        return "Department with id: " + id + " was deleted successfully";
    }

}
