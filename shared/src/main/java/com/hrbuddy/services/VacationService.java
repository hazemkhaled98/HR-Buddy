package com.hrbuddy.services;


import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.Employee;
import com.hrbuddy.persistence.entities.Vacation;
import com.hrbuddy.persistence.repositories.EmployeeRepository;
import com.hrbuddy.persistence.repositories.VacationRepository;
import com.hrbuddy.services.dto.VacationDTO;

import java.util.List;
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

    public static Optional<VacationDTO> getVacation(int id) {
        return Database.doInTransaction(entityManager -> {
            VacationRepository vacationRepository = new VacationRepository(entityManager);
            Optional<Vacation> vacation = vacationRepository.get(id);
            return vacation.map(VacationDTO::of);
        });
    }

    public static VacationDTO updateVacation(VacationDTO dto){
        return Database.doInTransaction(entityManager -> {
            VacationRepository vacationRepository = new VacationRepository(entityManager);
            EmployeeRepository employeeRepository = new EmployeeRepository(entityManager);
            Optional<Vacation> vacation = vacationRepository.get(dto.getId());
            Optional<Employee> employee = employeeRepository.get(dto.getEmployeeId());
            if(vacation.isEmpty())
                throw new IllegalArgumentException("Vacation record was not found");
            if(employee.isEmpty())
                throw new IllegalArgumentException("There is not employee with id: " + dto.getEmployeeId());
            Vacation updatedVacation = VacationDTO.toVacation(dto);
            updatedVacation.setEmployee(employee.get());
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
            VacationRepository vacationRepository = new VacationRepository(entityManager);
            vacationRepository.deleteByEmployeeId(employeeId);
        });
    }
}

