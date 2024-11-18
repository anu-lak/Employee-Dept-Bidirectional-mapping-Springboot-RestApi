package com.example.test1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test1.model.Dept;

@Repository
public interface DeptRepo extends JpaRepository<Dept,Integer>{
    
}
