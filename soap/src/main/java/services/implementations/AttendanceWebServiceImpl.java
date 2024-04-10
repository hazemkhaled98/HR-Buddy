package services.implementations;

import com.hrbuddy.services.AttendanceService;
import com.hrbuddy.services.dto.AttendanceDTO;
import jakarta.jws.WebService;
import security.SecurityManager;
import services.AttendanceWebService;

import java.util.List;


@WebService(endpointInterface = "services.AttendanceWebService")
public class AttendanceWebServiceImpl implements AttendanceWebService {
    @Override
    public List<AttendanceDTO> getAllAttendanceRecords(int employeeId, String token) {
        SecurityManager.authorizeUser(token);
        return AttendanceService.getAllAttendanceRecords(employeeId);
    }

    @Override
    public AttendanceDTO getAttendanceRecord(int id, String token) {
        SecurityManager.authorizeUser(token);
        return AttendanceService.getAttendance(id);
    }

    @Override
    public AttendanceDTO createAttendanceRecord(AttendanceDTO attendance, String token) {
        SecurityManager.authorizeAdmin(token);
        return AttendanceService.createAttendance(attendance);
    }

    @Override
    public AttendanceDTO updateAttendanceRecord(AttendanceDTO attendance, String token) {
        SecurityManager.authorizeAdmin(token);
        return AttendanceService.updateAttendance(attendance);
    }

    @Override
    public String deleteAttendanceRecord(int id, String token) {
        SecurityManager.authorizeAdmin(token);
        AttendanceService.deleteAttendance(id);
        return "Attendance with id: " + id + " was deleted successfully";
    }

    @Override
    public String deleteAllAttendanceRecords(int employeeId, String token) {
        SecurityManager.authorizeAdmin(token);
        AttendanceService.deleteAllAttendanceByEmployeeId(employeeId);
        return "All attendance records for employee with id: " + employeeId + " were deleted successfully";
    }
}
