package services;

import com.hrbuddy.services.dto.VacationDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface VacationWebService {

    @WebMethod
    List<VacationDTO> getAllVacations(@WebParam(header = true, name = "jwtToken") String token, @WebParam(name = "employeeId") int employeeId);

    @WebMethod
    VacationDTO getVacation(@WebParam(name = "id") int id, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    VacationDTO createVacation(@WebParam(name = "vacation") VacationDTO vacation, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    VacationDTO updateVacation(@WebParam(name = "vacation") VacationDTO vacation, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    String deleteVacation(@WebParam(name = "id") int id, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    String deleteVacationsByEmployeeId(@WebParam(name = "employeeId") int employeeId, @WebParam(header = true, name = "jwtToken") String token);
}
