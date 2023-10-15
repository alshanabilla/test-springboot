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
import com.example.demo.model.Region;
import com.example.demo.repository.RegionRepository;

@RestController
@RequestMapping("api")
public class RegionRestController {
    @Autowired
    private RegionRepository regionRepository;

    // @GetMapping("region") //localhost:8080/api/region
    // public List<Region> get() {
    //     return regionRepository.findAll();
    // }

    // @PostMapping(value = {"region", "region/{id}"})
    // public Boolean save(@RequestBody Region region, @PathVariable(required = false) Integer id) {
    //     if(id != null) {
    //         Region newRegion = regionRepository.findById(id).orElse(null);
    //         newRegion.setName(region.getName());
    //         regionRepository.save(newRegion);
    //         return regionRepository.findById(region.getId()).isPresent();
    //     } else {
    //         regionRepository.save(region);
    //         return regionRepository.findById(region.getId()).isPresent();
    //     }
    // }

    // @DeleteMapping("delete/{id}")
    // public Boolean delete(@PathVariable(required = true) Integer id) {
    //     regionRepository.deleteById(id);
    //     return !regionRepository.findById(id).isPresent();
    // }

    @GetMapping("region")
    public ResponseEntity<Object> get() {
        List<Region> data = regionRepository.findAll();
        if(data.isEmpty()) {
            return CustomeResponse.generate(HttpStatus.OK, "data tidak ditemukan");
        }
        return CustomeResponse.generate(HttpStatus.OK, "data ditemukan", data);
    }

    @PostMapping("region")
    public ResponseEntity<Object> save(@RequestBody Region region) {
        regionRepository.save(region);
        Boolean result = regionRepository.findById(region.getId()).isPresent();
        if(result) {
            return CustomeResponse.generate(HttpStatus.OK, "data berhasil disimpan");
        }
        return CustomeResponse.generate(HttpStatus.BAD_REQUEST, "data tidak berhasil disimpan");
    }

    // @PostMapping(value= {"region", "region/{id}"})
    // public ResponseEntity<Object> save(@RequestBody Region region, @PathVariable(required = true) Integer id) {
    //     if(id != null) {
    //         Region newRegion =regionRepository.findById(id).orElse(null);
    //         newRegion.setName(region.getName());
    //         regionRepository.save(newRegion);
    //         Boolean result = regionRepository.findById(region.getId()).isPresent();
    //         if(result) {
    //             return CustomeResponse.generate(HttpStatus.OK, "data berhasil diupdate");
    //         }
    //         return CustomeResponse.generate(HttpStatus.BAD_REQUEST, "data tidak berhasil diupdate");
    //     } else {
    //         regionRepository.save(region);
    //         Boolean result = regionRepository.findById(region.getId()).isPresent();
    //         if(result) {
    //             return CustomeResponse.generate(HttpStatus.OK, "data berhasil disimpan");
    //         }
    //         return CustomeResponse.generate(HttpStatus.BAD_REQUEST, "data tidak berhasil disimpan");
    //     }
    // }

    //getbyid
    @GetMapping("region/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) Integer id) {
        Boolean result = regionRepository.findById(id).isPresent();
        if(result) {
            Region newRegion = regionRepository.findById(id).orElse(null);
            return CustomeResponse.generate(HttpStatus.OK, "data ditemukan", newRegion);
        }
        return CustomeResponse.generate(HttpStatus.BAD_REQUEST, "data tidak ditemukan");
    }

    //delete
    @DeleteMapping("region/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id) {
        Boolean result = regionRepository.findById(id).isPresent();
        if(result) {
            regionRepository.deleteById(id);
            return CustomeResponse.generate(HttpStatus.OK, "data berhasil dihapus");
        }
        return CustomeResponse.generate(HttpStatus.BAD_REQUEST, "data tidak berhasil dihapus");
    }

}
