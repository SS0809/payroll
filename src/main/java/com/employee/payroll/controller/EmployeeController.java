package com.employee.payroll.controller;

import com.employee.payroll.dto.EmployeeDTO;
import com.employee.payroll.entity.EmployeeEntity;
import com.employee.payroll.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping()
    public ResponseEntity<List<EmployeeEntity>> getAll(){
        return ResponseEntity.ok(employeeService.getall());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<EmployeeEntity>> getEmployee(@RequestBody EmployeeDTO employeeDTO){
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        return ResponseEntity.ok(employeeService.getEmployee(employeeEntity));
    }
    @PostMapping()
    public ResponseEntity<EmployeeEntity> setEmployee(@RequestBody EmployeeDTO employeeDTO){
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        return ResponseEntity.ok(employeeService.createEmployee(employeeEntity));
    }
    @PutMapping()
    public ResponseEntity<EmployeeEntity> updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        return ResponseEntity.ok(employeeService.updateEmployee(employeeEntity.getId(),employeeEntity));
    }
    @DeleteMapping()
    public ResponseEntity<String> deleteEmployee(@RequestBody EmployeeDTO employeeDTO){
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        return ResponseEntity.ok(employeeService.deleteEmployee(employeeDTO.getId())?"Deleted the employee record":"unable to delete it");
    }
}
