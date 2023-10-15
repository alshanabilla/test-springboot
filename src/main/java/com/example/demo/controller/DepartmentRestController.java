package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.handler.CustomeResponse;
import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;

@RestController
@RequestMapping("api")
public class DepartmentRestController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("department")
    public ResponseEntity<Object> get() {
        List<Department> data = departmentRepository.findAll();
        if(data.isEmpty()) {
            return CustomeResponse.generate(HttpStatus.OK, "data tidak ditemukan");
        }
        return CustomeResponse.generate(HttpStatus.OK, "data ditemukan", data);
    }

    @PostMapping("department")
    public ResponseEntity<Object> save(@RequestBody Department department) {
        departmentRepository.save(department);
        Boolean result = departmentRepository.findById(department.getId()).isPresent();
        if(result) {
            return CustomeResponse.generate(HttpStatus.OK, "data berhasil disimpan");
        }
        return CustomeResponse.generate(HttpStatus.BAD_REQUEST, "data tidak berhasil disimpan");
    }

    //getbyid
    @GetMapping("department/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) Integer id) {
        Boolean result = departmentRepository.findById(id).isPresent();
        if(result) {
            Department newDepartment = departmentRepository.findById(id).orElse(null);
            return CustomeResponse.generate(HttpStatus.OK, "data ditemukan", newDepartment);
        }
        return CustomeResponse.generate(HttpStatus.BAD_REQUEST, "data tidak ditemukan");
    }

    //delete
    @DeleteMapping("department/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id) {
        Boolean result = departmentRepository.findById(id).isPresent();
        if(result) {
            departmentRepository.deleteById(id);
            return CustomeResponse.generate(HttpStatus.OK, "data berhasil dihapus");
        }
        return CustomeResponse.generate(HttpStatus.BAD_REQUEST, "data tidak berhasil dihapus");
    }
}
