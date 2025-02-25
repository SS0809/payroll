package com.employee.payroll.service;

import com.employee.payroll.dto.EmployeeDTO;
import com.employee.payroll.entity.EmployeeEntity;
import com.employee.payroll.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional  // Either it will completely run or i wouldn't run at all
    public List<EmployeeEntity> getall(){
        return (employeeRepository.findAll());
    }

    @Transactional
    public Optional<EmployeeEntity> getEmployee(EmployeeDTO employeeDTO){
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        return employeeRepository.findById(employeeEntity.getId());
    }
    @Transactional
    public EmployeeEntity createEmployee(EmployeeDTO employeeDTO){
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        return employeeRepository.save(employeeEntity);
    }
    public EmployeeEntity updateEmployee(Long id,EmployeeDTO employeeDTO){
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setName(employeeEntity.getName() != null ?
                            employeeEntity.getName() : existingEmployee.getName());
                    existingEmployee.setSalary(employeeEntity.getSalary() != null ?
                            employeeEntity.getSalary() : existingEmployee.getSalary());
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
