package com.example.test1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test1.model.Dept;
import com.example.test1.model.Employee;
import com.example.test1.repository.DeptRepo;
import com.example.test1.repository.EmpRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private EmpRepo empRepo;

    @Autowired
    private DeptRepo deptRepo;

    //add new employee
    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = new Employee();
        savedEmployee.setName(employee.getName());
        savedEmployee.setDeptId(employee.getDeptId());
        Optional<Dept> optionalDept = deptRepo.findById(employee.getDeptId());
        if(!optionalDept.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept not found");
        }
        savedEmployee.setDept(optionalDept.get());
        empRepo.save(savedEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    //add new department
    @PostMapping("/addDepartment")
    public ResponseEntity<?> addDepartment(@RequestBody Dept dept) {
        Dept savedDept = new Dept();
        savedDept.setName(dept.getName());
        // ArrayList<Employee> empList = new ArrayList<>();
        // savedDept.setEmpList(new ArrayList<>());
        deptRepo.save(savedDept);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedDept);
    }

    //update an existing department
    @PutMapping("/updateDepartment/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable int id,@RequestBody Dept dept){
        Optional<Dept> optionalDept = deptRepo.findById(id);
        if(!optionalDept.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept not found");
        }
        Dept savedDepartment = optionalDept.get();
        savedDepartment.setName(dept.getName());
        return ResponseEntity.status(HttpStatus.OK).body(savedDepartment);
    }

    // Delete a department.
    @DeleteMapping("/deleteDepartment")
    public ResponseEntity<?> deleteDepartment(@RequestParam int id){
        if(!deptRepo.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept not found");
        }
        deptRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Fetch all departments.
    @GetMapping("/allDepartments")
    public ResponseEntity<?> getAllDepartments() {
        List<Dept> allDepts = deptRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allDepts);
    }
    
    // Fetch a specific department by ID.
    @GetMapping("/specificDepartment")
    public ResponseEntity<?> getSpecificDepartment(@RequestParam int id) {
        if(!deptRepo.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept not found");
        }
        Optional<Dept> fetchedDept = deptRepo.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fetchedDept);
    }

    // Update an existing employee.
    @PutMapping("/changeEmployeeDept")
    public ResponseEntity<?> changeEmployeeDept(@RequestBody Employee employee){
        if(!deptRepo.existsById(employee.getDeptId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept not found");
        }
        Optional<Employee> optionalEmployee = empRepo.findById(employee.getEmpId());
        if(!optionalEmployee.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
        Employee updatedEmployee = optionalEmployee.get();
        updatedEmployee.setDeptId(employee.getDeptId());
        empRepo.save(updatedEmployee);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }
    
    // Delete an employee.
    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<?> deleteEmployee(@RequestParam int id){
        if(!empRepo.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
        empRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Fetch all employees.
    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees(){
        List<Employee> empList = empRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(empList);
    }
    
    
    // Fetch a specific employee by ID.
    @GetMapping("/getEmployee")
    public ResponseEntity<?> getEmployee(@RequestParam int id){
        if(!empRepo.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
        Optional<Employee> fetchedEmployee = empRepo.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fetchedEmployee);
    }
    
    
    // List all employees within a specific department.
    @GetMapping("/employeesInADepartment")
    public ResponseEntity<?> getEmployeesInADepartment(@RequestParam int deptId){
        if(!deptRepo.existsById(deptId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dept not found");
        }
        List empList = empRepo.findAllByDeptId(deptId);
        return ResponseEntity.status(HttpStatus.OK).body(empList);
    }
    
    
}
