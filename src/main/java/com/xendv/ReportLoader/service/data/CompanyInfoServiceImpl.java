package com.xendv.ReportLoader.service.data;

import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.repository.CompanyInfoExtendedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service("companyInfoService")
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    private CompanyInfoExtendedRepository companyInfoRepository;

    @Override
    public CompanyInfo create(@NotNull CompanyInfo companyInfo) {
        return companyInfoRepository.save(companyInfo);
    }

    @Override
    public void updateValuesIfNotNull(CompanyInfo companyInfo) {
        //if (companyInfo.okpo != null && companyInfo.year != null)
        if (companyInfo.year == null)
            return;
        final var entry = companyInfoRepository.findByOkpoAndYear(companyInfo.okpo, companyInfo.year);
        if (entry.isPresent()) {
            companyInfoRepository
                    .updateValues(
                            companyInfo.okpo,
                            companyInfo.year,
                            companyInfo.people,
                            companyInfo.revenue,
                            companyInfo.profit,
                            companyInfo.salary
                    );
        } else {
            create(companyInfo);
        }
    }

    public Iterable<CompanyInfo> get(@PathVariable BigDecimal okpo) {
        return companyInfoRepository.findAllByOkpo(okpo);
    }

    public Iterable<CompanyInfo> getAll() {
        return companyInfoRepository.findAll();
    }

    public void delete(@PathVariable BigDecimal okpo) {
        companyInfoRepository.deleteById(okpo);
    }

    public void update(@PathVariable BigDecimal okpo,
                       CompanyInfo companyInfo) {
        if (companyInfoRepository.existsById(okpo)) {
            companyInfoRepository.save(companyInfo);
        } else throw new NoSuchElementException("No info about company with okpo " + okpo);
    }
}
