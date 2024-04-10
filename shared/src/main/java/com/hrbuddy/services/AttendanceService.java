package com.hrbuddy.services;

import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.Attendance;
import com.hrbuddy.persistence.entities.Employee;
import com.hrbuddy.persistence.repositories.AttendanceRepository;
import com.hrbuddy.persistence.repositories.EmployeeRepository;
import com.hrbuddy.services.dto.AttendanceDTO;

import java.util.List;
import java.util.NoSuchElementException;

public class AttendanceService {

    private AttendanceService() {
    }

    public static AttendanceDTO createAttendance(AttendanceDTO dto) {
        validateDTO(dto);
        return Database.doInTransaction(entityManager -> {
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Employee employee = employeeRepository.get(dto.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("There is not employee with id: " + dto.getEmployeeId()));
            Attendance attendance = AttendanceDTO.toAttendance(dto);
            attendance.setEmployee(employee);
            return AttendanceDTO.of(attendanceRepository.create(attendance));
        });
    }


    public static List<AttendanceDTO> getAllAttendanceRecords(int employeeId) {
        return Database.doInTransaction(entityManager -> {
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            return AttendanceDTO.of(employeeId != 0 ? attendanceRepository.getAllByEmployeeId(employeeId) : attendanceRepository.getAll());
        });
    }

    public static AttendanceDTO getAttendance(int id) {
        return Database.doInTransaction(entityManager -> {
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            Attendance attendance = attendanceRepository.get(id)
                    .orElseThrow(() -> new NoSuchElementException("Attendance with id: " + id + " record was not found"));
            return AttendanceDTO.of(attendance);
        });
    }

    public static AttendanceDTO updateAttendance(AttendanceDTO dto){
        validateDTO(dto);
        return Database.doInTransaction(entityManager -> {
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            if(dto.getId() == null)
                throw new IllegalArgumentException("Id is required");
            Attendance attendance = attendanceRepository.get(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("There is not Attendance record with id: " + dto.getId()));
            Employee employee = employeeRepository.get(dto.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("There is not employee with id: " + dto.getEmployeeId()));
            Attendance updatedAttendance = AttendanceDTO.toAttendance(dto);
            updatedAttendance.setEmployee(employee);
            updatedAttendance = attendanceRepository.update(updatedAttendance);
            return AttendanceDTO.of(updatedAttendance);
        });
    }

    public static void deleteAttendance(int id){
        Database.doInTransactionWithoutResult(entityManager -> {
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            attendanceRepository.deleteById(id);
        });
    }

    public static void deleteAllAttendanceByEmployeeId(int employeeId) {
        Database.doInTransactionWithoutResult(entityManager -> {
            if(employeeId == 0){
                throw new IllegalArgumentException("Employee id is required");
            }
            AttendanceRepository attendanceRepository = new AttendanceRepository(entityManager);
            attendanceRepository.deleteByEmployeeId(employeeId);
        });
    }

    private static void validateDTO(AttendanceDTO dto) {
        if(dto.getEmployeeId() == null || dto.getDate() == null || dto.getStatus() == null)
            throw new IllegalArgumentException("Employee Id, Date and Status are required");
    }
}
