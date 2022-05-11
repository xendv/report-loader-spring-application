package com.xendv.ReportLoader.controllers;

import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.service.data.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/rest/api/company-info")
@CrossOrigin(origins = "*")
public class CompanyInfoController {

    @Autowired
    private CompanyInfoService companyInfoService;

    @GetMapping("/{okpo}")
    public Iterable<CompanyInfo> get(@PathVariable BigDecimal okpo) {
        System.out.println("Was called /rest/api/company-info/" + okpo);
        return companyInfoService.get(okpo);
    }

    @PostMapping("/save")
    public CompanyInfo save(@RequestBody CompanyInfo companyInfo) {
        return companyInfoService.create(companyInfo);
    }

    @GetMapping("/all")
    public Iterable<CompanyInfo> getAll() {
        return companyInfoService.getAll();
    }

    @DeleteMapping("/{okpo}")
    public void delete(@PathVariable BigDecimal okpo) {
        companyInfoService.delete(okpo);
    }

    @PutMapping("/{okpo}")
    public void update(@PathVariable BigDecimal okpo,
                       CompanyInfo companyInfo) {
        companyInfoService.update(okpo, companyInfo);
    }
}
