package com.xendv.ReportLoader.controllers;

import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.repository.CompanyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/api/company-info")
@CrossOrigin(origins = "*")
public class CompanyInfoController {
    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @GetMapping("/{okpo}")
    public Iterable<CompanyInfo> get(@PathVariable BigDecimal okpo) {
        System.out.println("Was clalled /rest/api/company-info/" + okpo);
        return companyInfoRepository.findAllByOkpo(okpo);
    }

    @GetMapping("/all")
    public Iterable<CompanyInfo> getAll() {
        return companyInfoRepository.findAll();
    }

    @DeleteMapping("/{okpo}")
    public void delete(@PathVariable BigDecimal okpo) {
        companyInfoRepository.deleteById(okpo);
    }

    @PutMapping("/{okpo}")
    public void update(@PathVariable BigDecimal okpo,
                       CompanyInfo companyInfo) {
        if (companyInfoRepository.existsById(okpo)) {
            companyInfoRepository.save(companyInfo);
        } else throw new NoSuchElementException("No info about company with okpo " + okpo);
    }
}
