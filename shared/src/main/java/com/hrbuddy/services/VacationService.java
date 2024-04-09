package com.hrbuddy.services;


import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.Employee;
import com.hrbuddy.persistence.entities.Vacation;
import com.hrbuddy.persistence.repositories.EmployeeRepository;
import com.hrbuddy.persistence.repositories.VacationRepository;
import com.hrbuddy.services.dto.VacationDTO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
public class VacationService {

    private VacationService() {
    }

    public static VacationDTO createVacation(VacationDTO dto) {
        return Database.doInTransaction(entityManager -> {
            VacationRepository vacationRepository = new VacationRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Optional<Employee> employee = employeeRepository.get(dto.getEmployeeId());
            if(employee.isEmpty())
                throw new IllegalArgumentException("There is not employee with id: " + dto.getEmployeeId());
            Vacation vacation = VacationDTO.toVacation(dto);
            vacation.setEmployee(employee.get());
            return VacationDTO.of(vacationRepository.create(vacation));
        });
    }

    public static List<VacationDTO> getAllVacations(int employeeId) {
        return Database.doInTransaction(entityManager -> {
            VacationRepository vacationRepository = new VacationRepository(entityManager);
            return VacationDTO.of(employeeId  != 0 ? vacationRepository.getAllByEmployeeId(employeeId) : vacationRepository.getAll());
        });
    }

    public static List<VacationDTO> getAllVacationByEmployeeId(int employeeId) {
        return Database.doInTransaction(entityManager -> {
            VacationRepository vacationRepository = new VacationRepository(entityManager);
            return VacationDTO.of(vacationRepository.getAllByEmployeeId(employeeId));
        });
    }

    public static VacationDTO getVacation(int id) {
        return Database.doInTransaction(entityManager -> {
            VacationRepository vacationRepository = new VacationRepository(entityManager);
            Vacation vacation = vacationRepository.get(id)
                    .orElseThrow(() -> new NoSuchElementException("No vacation found for id: " + id));
            return VacationDTO.of(vacation);
        });
    }

    public static VacationDTO updateVacation(VacationDTO dto){
        return Database.doInTransaction(entityManager -> {
            VacationRepository vacationRepository = new VacationRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Vacation vacation = vacationRepository.get(dto.getId())
                    .orElseThrow(() ->  new IllegalArgumentException("There is no vacation with id" + dto.getId()));

            Employee employee = employeeRepository.get(dto.getEmployeeId())
                    .orElseThrow(() ->  new IllegalArgumentException("There is no employee with id" + dto.getEmployeeId()));

            Vacation updatedVacation = VacationDTO.toVacation(dto);
            updatedVacation.setEmployee(employee);
            updatedVacation = vacationRepository.update(updatedVacation);
            return VacationDTO.of(updatedVacation);
        });
    }

    public static void deleteVacation(int id){
        Database.doInTransactionWithoutResult(entityManager -> {
            VacationRepository vacationRepository = new VacationRepository(entityManager);
            vacationRepository.deleteById(id);
        });
    }

    public static void deleteVacationsByEmployeeId(int employeeId) {
        Database.doInTransactionWithoutResult(entityManager -> {
            if (employeeId == 0)
                throw new IllegalArgumentException("Employee id is required");
            VacationRepository vacationRepository = new VacationRepository(entityManager);
            vacationRepository.deleteByEmployeeId(employeeId);
        });
    }
}

