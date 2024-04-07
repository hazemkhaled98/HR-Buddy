package com.hrbuddy.services;

import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.Attendance;
import com.hrbuddy.persistence.entities.Employee;
import com.hrbuddy.persistence.repositories.AttendanceRepository;
import com.hrbuddy.persistence.repositories.EmployeeRepository;
import com.hrbuddy.services.dto.AttendanceDTO;

import java.util.List;
import java.util.Optional;
public class AttendanceService {

    private AttendanceService() {
    }

    public static AttendanceDTO createAttendance(AttendanceDTO dto) {
        return Database.doInTransaction(entityManager -> {
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Optional<Employee> employee = employeeRepository.get(dto.getEmployeeId());
            if(employee.isEmpty())
                throw new IllegalArgumentException("There is not employee with id: " + dto.getEmployeeId());
            Attendance attendance = AttendanceDTO.toAttendance(dto);
            attendance.setEmployee(employee.get());
            return AttendanceDTO.of(attendanceRepository.create(attendance));
        });
    }

    public static List<AttendanceDTO> getAllAttendanceRecords() {
        return Database.doInTransaction(entityManager -> {
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            return AttendanceDTO.of(attendanceRepository.getAll());
        });
    }

    public static Optional<AttendanceDTO> getAttendanceRecord(int id) {
        return Database.doInTransaction(entityManager -> {
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            Optional<Attendance> attendance = attendanceRepository.get(id);
            return attendance.map(AttendanceDTO::of);
        });
    }

    public static AttendanceDTO updateAttendanceRecord(AttendanceDTO dto){
        return Database.doInTransaction(entityManager -> {
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Optional<Attendance> attendance = attendanceRepository.get(dto.getId());
            Optional<Employee> employee = employeeRepository.get(dto.getEmployeeId());
            if(attendance.isEmpty())
                throw new IllegalArgumentException("Attendance record was not found");
            if(employee.isEmpty())
                throw new IllegalArgumentException("There is not employee with id: " + dto.getEmployeeId());
            Attendance updatedAttendance = attendanceRepository.update(AttendanceDTO.toAttendance(dto));
            updatedAttendance.setEmployee(employee.get());
            return AttendanceDTO.of(updatedAttendance);
        });
    }

    public static void deleteAttendanceRecord(int id){
        Database.doInTransactionWithoutResult(entityManager -> {
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            attendanceRepository.deleteById(id);
        });
    }
}
