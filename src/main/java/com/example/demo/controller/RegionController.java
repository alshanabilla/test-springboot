package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Region;
import com.example.demo.repository.RegionRepository;

@Controller
@RequestMapping("region")
public class RegionController {
    @Autowired
    private RegionRepository regionRepository;

    //index -> http://localhost:8088/region/
    @GetMapping
    public String index(Model model) {
        model.addAttribute("regions", regionRepository.findAll()); //penampung data plural
        return "region/index";
    }

    //form insert -> region/form
    @GetMapping(value= {"form", "form/{id}"})
    public String form(Model model, @PathVariable(required = false) Integer id) { //diisi melalui url
        if(id != null){
            model.addAttribute("region", regionRepository.findById(id));
        } else {
            model.addAttribute("region", new Region());
        }
        return "region/form";
    }

    //form save -> region/save
    @PostMapping("save")
    public String save(Region region) { //diisi melalui body
        regionRepository.save(region);
        Boolean result = regionRepository.findById(region.getId()).isPresent();
        if(result){
            return "redirect:/region";
        }
        return "region/form";
    }

    //form update -> region/form/1

    //delete -> region/delete/1
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(required = true)  Integer id) {
        regionRepository.deleteById(id);
        boolean result = regionRepository.findById(id).isEmpty();
        if(result){
            return"redirect:/region";
        }
        return"redirect:/region";
    }
}
