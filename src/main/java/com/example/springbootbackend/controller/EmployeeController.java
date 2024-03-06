package com.example.springbootbackend.controller;

import com.example.springbootbackend.exception.ResourceNotFoundException;
import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployess(){
        return employeeRepository.findAll();
    }
     //create Employee Rest Api
    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee){
        return  employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getElementById(@PathVariable("id") long id){
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No employee found with id: " + id));
    return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee>updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails){
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No employee found with id: " + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        Employee save = employeeRepository.save(employee);
        return ResponseEntity.ok(save);
    }



    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>>deletedEmployee(@PathVariable("id") long id){
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No employee found with id: " + id));
        employeeRepository.deleteById(employee.getId());
        HashMap<String, Boolean> response =new HashMap<>();
        response.put("deleted",Boolean.TRUE);
         return ResponseEntity.ok(response);
    }
}
