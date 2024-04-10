package services.implementations;

import com.hrbuddy.services.VacationService;
import com.hrbuddy.services.dto.VacationDTO;
import jakarta.jws.WebService;
import security.SecurityManager;
import services.VacationWebService;

import java.util.List;

@WebService(endpointInterface = "services.VacationWebService")
public class VacationWebServiceImpl implements VacationWebService {
    @Override
    public List<VacationDTO> getAllVacations(String token, int employeeId) {
        SecurityManager.authorizeUser(token);
        return VacationService.getAllVacations(employeeId);
    }

    @Override
    public VacationDTO getVacation(int id, String token) {
        SecurityManager.authorizeUser(token);
        return VacationService.getVacation(id);
    }

    @Override
    public VacationDTO createVacation(VacationDTO vacation, String token) {
        SecurityManager.authorizeAdmin(token);
        return VacationService.createVacation(vacation);
    }

    @Override
    public VacationDTO updateVacation(VacationDTO vacation, String token) {
        SecurityManager.authorizeAdmin(token);
        return VacationService.updateVacation(vacation);
    }

    @Override
    public String deleteVacation(int id, String token) {
        SecurityManager.authorizeAdmin(token);
        VacationService.deleteVacation(id);
        return "Vacation with id: " + id + " was deleted successfully";
    }

    @Override
    public String deleteVacationsByEmployeeId(int employeeId, String token) {
        SecurityManager.authorizeAdmin(token);
        VacationService.deleteVacationsByEmployeeId(employeeId);
        return "All vacations for employee with id: " + employeeId + " were deleted successfully";
    }
}
