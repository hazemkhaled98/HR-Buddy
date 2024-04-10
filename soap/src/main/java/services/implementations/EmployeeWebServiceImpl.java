package services.implementations;

import com.hrbuddy.services.EmployeeService;
import com.hrbuddy.services.dto.EmployeeDTO;
import jakarta.jws.WebService;
import security.SecurityManager;
import services.EmployeeWebService;

import java.util.List;

@WebService(endpointInterface = "services.EmployeeWebService")
public class EmployeeWebServiceImpl implements EmployeeWebService {

    @Override
    public List<EmployeeDTO> getAllEmployees(String token) {
        SecurityManager.authorizeUser(token);
        return EmployeeService.getAllEmployees();
    }

    @Override
    public EmployeeDTO getEmployee(int id, String token) {
        SecurityManager.authorizeUser(token);
        return EmployeeService.getEmployee(id);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employee, String token) {
        SecurityManager.authorizeAdmin(token);
        return EmployeeService.createEmployee(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employee, String token) {
        SecurityManager.authorizeAdmin(token);
        return EmployeeService.updateEmployee(employee);
    }

    @Override
    public String deleteEmployee(int id, String token) {
        SecurityManager.authorizeAdmin(token);
        EmployeeService.deleteEmployee(id);
        return "Employee with id: " + id + " was deleted successfully";
    }
}
