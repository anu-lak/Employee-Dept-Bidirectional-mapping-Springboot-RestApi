package com.example.test1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test1.model.Employee;

@Repository
public interface EmpRepo extends JpaRepository<Employee,Integer>{

    List findAllByDeptId(int deptid);
    
}
