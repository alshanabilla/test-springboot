package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Region;
import com.example.demo.repository.RegionRepository;

@SpringBootTest
public class RegionTest {
    @Autowired
    private RegionRepository regionRepository;

    @Test
    public void insert() {
        //ARRANGE -> menuliskan data yang diperlukan untuk melakukan testing
        Region region = new Region();
        region.setName("Jawa Barat");

        Boolean expectedValue = true;

        //ACT -> mengeksekusi testing
        regionRepository.save(region); // menyimpan data
        Boolean actualValue = regionRepository.findById(region.getId()).isPresent(); //mengecek data

        //ASSERT -> melakukan compare antara expected value dengan actual value
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void update(){
        //ARRANGE
        Region region = new Region();
        region.setId(1);
        region.setName("WIB");

        Boolean expectedValue = true;
        
        //ACT
        regionRepository.save(region);
        Boolean actualValue = regionRepository.findById(region.getId()).isPresent();

        //ASSERT
        assertEquals(expectedValue, actualValue);

    }

    // @Test
    // public void delete(){
    //     Region region = new Region();
    //     region.setId(2);

    //     Boolean expectedValue = true;

    //     regionRepository.delete(region);
    //     Boolean actualValue = regionRepository.findById(region.getId()).isEmpty();

    //     assertEquals(expectedValue, actualValue);
    // }

    @Test
    public void delete(){
        Integer id = 1;

        Boolean expectedValue = true;

        regionRepository.deleteById(id);
        Boolean actualValue = !regionRepository.findById(id).isPresent();

        assertEquals(expectedValue, actualValue);
    }



}
