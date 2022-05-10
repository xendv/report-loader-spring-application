package com.xendv.ReportLoader.controllers;

import com.xendv.ReportLoader.model.MainInfo;
import com.xendv.ReportLoader.service.data.MainInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/rest/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MainInfoController {

    @Autowired
    private final MainInfoService mainInfoService;

    @PostMapping("/main-info")
    public MainInfo create(@RequestBody MainInfo mainInfo) {
        return mainInfoService.create(mainInfo);
    }
    // сделать метод на добавление коллекции

    @PutMapping("/main-info/{okpo}")
    public void update(@PathVariable BigDecimal okpo,
                       MainInfo mainInfo) {
        mainInfoService.update(okpo, mainInfo);
    }

    @GetMapping("/main-info/{okpo}")
    public MainInfo get(@PathVariable BigDecimal okpo) {
        return mainInfoService.get(okpo);
    }

    @GetMapping("/main-info")
    public Iterable<MainInfo> getAll() {
        return mainInfoService.getAll();
    }

    @DeleteMapping("/main-info/{okpo}")
    public void delete(@PathVariable BigDecimal okpo) {
        mainInfoService.delete(okpo);
    }
}
