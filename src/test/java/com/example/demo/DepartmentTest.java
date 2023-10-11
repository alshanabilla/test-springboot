package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Department;
import com.example.demo.model.Region;
import com.example.demo.repository.DepartmentRepository;

@SpringBootTest
public class DepartmentTest {
    @Autowired
    private DepartmentRepository departmentRepository;
    

    @Test
    public void insert(){
        Department department = new Department();
        Region region = new Region();
        region.setId(1);
        department.setName("IT");
        department.setRegion(region);

        Boolean expectedValue = true;

        departmentRepository.save(department);
        Boolean actualValue = departmentRepository.findById(department.getId()).isPresent();

        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void update(){
        Department department = new Department();
        department.setId(1);
        department.setName("Information Technology");

        Boolean expectedValue = true;

        departmentRepository.save(department);
        Boolean actualValue = departmentRepository.findById(department.getId()).isPresent();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void delete(){
        Department department = new Department();
        department.setId(2);

        Boolean expectedValue = true;

        departmentRepository.delete(department);
        Boolean actualValue = departmentRepository.findById(department.getId()).isEmpty();

        assertEquals(expectedValue, actualValue);

    }
    
}
