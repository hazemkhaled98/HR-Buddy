package services;

import com.hrbuddy.services.dto.AttendanceDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface AttendanceWebService {

    @WebMethod
    List<AttendanceDTO> getAllAttendanceRecords(@WebParam(name = "employeeId") int employeeId, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    AttendanceDTO getAttendanceRecord(@WebParam(name = "id") int id, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    AttendanceDTO createAttendanceRecord(@WebParam(name = "attendance") AttendanceDTO attendance, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    AttendanceDTO updateAttendanceRecord(@WebParam(name = "attendance") AttendanceDTO attendance, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    String deleteAttendanceRecord(@WebParam(name = "id") int id, @WebParam(header = true, name = "jwtToken") String token);

    @WebMethod
    String deleteAllAttendanceRecords(@WebParam(name = "employeeId") int employeeId, @WebParam(header = true, name = "jwtToken") String token);

}
