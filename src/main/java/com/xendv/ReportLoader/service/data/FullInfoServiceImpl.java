package com.xendv.ReportLoader.service.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.model.FullInfo;
import com.xendv.ReportLoader.model.MainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service("fullInfoService")
public class FullInfoServiceImpl implements FullInfoService {

    @Autowired
    private @NotNull MainInfoService mainInfoService;
    @Autowired
    private @NotNull CompanyInfoService companyInfoService;

    @Override
    public void saveAll(@NotNull List<FullInfo> fullInfos) {
        ObjectMapper objectMapper = new ObjectMapper();
        fullInfos.forEach(fullInfo -> {
            var main = objectMapper.convertValue(fullInfo, MainInfo.class);
            var company = objectMapper.convertValue(fullInfo, CompanyInfo.class);
            mainInfoService.updateValuesIfNotNull(main);
            companyInfoService.updateValuesIfNotNull(company);
        });
    }
}
