package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.RegionRepository;

@Controller
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RegionRepository regionRepository;

    //index -> http://localhost:8088/department/
    @GetMapping
    public String index(Model model) {
        model.addAttribute("departments", departmentRepository.findAll()); //penampung data plural
        return "department/index";
    }

    @GetMapping(value= {"form", "form/{id}"})
    public String form(Model model, @PathVariable(required = false) Integer id) {
        if(id != null){ //edit
            model.addAttribute("department", departmentRepository.findById(id));
            model.addAttribute("regions", regionRepository.findAll()); // untuk mengambil data region dari repositori region
        } else { //create
            model.addAttribute("department", new Department()); 
            model.addAttribute("regions", regionRepository.findAll());
        }
        return "department/form";
    }

    @PostMapping("save")
    public String save(Department department) {
        departmentRepository.save(department);
        Boolean result = departmentRepository.findById(department.getId()).isPresent();
        if(result){
            return "redirect:/department";
        }
        return "department/form";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable(required = true)  Integer id) {
        departmentRepository.deleteById(id);
        boolean result = departmentRepository.findById(id).isEmpty();
        if(result){
            return"redirect:/department";
        }
        return"redirect:/department";
    }
    



}
