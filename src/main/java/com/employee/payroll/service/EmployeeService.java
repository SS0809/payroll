package com.employee.payroll.service;

import com.employee.payroll.dto.EmployeeDTO;
import com.employee.payroll.entity.EmployeeEntity;
import com.employee.payroll.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public List<EmployeeEntity> getall(){
        return (employeeRepository.findAll());
    }

    @Transactional
    public Optional<EmployeeEntity> getEmployee(EmployeeEntity employee){
        return employeeRepository.findById(employee.getId());
    }
    @Transactional
    public EmployeeEntity createEmployee(EmployeeEntity employee){
        return employeeRepository.save(employee);
    }
    public EmployeeEntity updateEmployee(Long id, EmployeeEntity newEmployee) {
        return employeeRepository.findById(id).map(existingEmployee -> {
            existingEmployee.setName(newEmployee.getName() != null ? newEmployee.getName() : existingEmployee.getName());
            existingEmployee.setSalary(newEmployee.getSalary() != null ? newEmployee.getSalary() : existingEmployee.getSalary());
            existingEmployee.setDepartment(newEmployee.getDepartment() != null ? newEmployee.getDepartment() : existingEmployee.getDepartment());
            return employeeRepository.save(existingEmployee);
        }).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    @Transactional
    public boolean deleteEmployee(Long id){
        try {
            employeeRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
