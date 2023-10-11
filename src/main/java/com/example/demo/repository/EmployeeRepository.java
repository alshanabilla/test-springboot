package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    // @Query(value= "select e.employee_id from tb_m_employee e where e.phone = ?1", nativeQuery = true)
    // public Integer findIdByPhoneNumber(String phone);
}
