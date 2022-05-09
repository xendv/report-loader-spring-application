package com.xendv.ReportLoader.controllers;

import com.xendv.ReportLoader.model.MainInfo;
import com.xendv.ReportLoader.repository.MainInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/api")
@CrossOrigin(origins = "*")
public class MainInfoController {
    @Autowired
    private MainInfoRepository mainInfoRepository;

    @PostMapping("/main-info")
    public MainInfo create(@RequestBody MainInfo mainInfo) {
        return mainInfoRepository.save(mainInfo);
    }
    // сделать метод на добавление коллекции

    @PutMapping("/main-info/{okpo}")
    public void update(@PathVariable BigDecimal okpo,
                       MainInfo mainInfo) {
        if (mainInfoRepository.existsById(okpo)) {
            mainInfoRepository.save(mainInfo);
        } else throw new NoSuchElementException("No info about company with okpo " + okpo);
    }

    @GetMapping("/main-info/{okpo}")
    public MainInfo get(@PathVariable BigDecimal okpo) {
        return mainInfoRepository.findById(okpo).orElse(null);
    }

    @GetMapping("/main-info")
    public Iterable<MainInfo> getAll() {
        return mainInfoRepository.findAll();
    }

    @DeleteMapping("/main-info/{okpo}")
    public void delete(@PathVariable BigDecimal okpo) {
        mainInfoRepository.deleteById(okpo);
    }
}
