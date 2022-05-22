package com.xendv.ReportLoader.service.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xendv.ReportLoader.exception.ServerStateException;
import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.model.FullInfo;
import com.xendv.ReportLoader.model.MainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, String> getStates(@NotNull List<FullInfo> fullInfos) {
        Map<String, String> states = new LinkedHashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        fullInfos.forEach(fullInfo -> {
            //var main = objectMapper.convertValue(fullInfo, MainInfo.class);
            var company = objectMapper.convertValue(fullInfo, CompanyInfo.class);
            try {
                var jsonString = objectMapper.writeValueAsString(fullInfo);
                if (!company.checkNull()) {
                    var state = companyInfoService.getState(company);
                    states.put(jsonString, state);
                } else {
                    states.put(jsonString, "Не изменено");
                }
            } catch (IllegalAccessException | JsonProcessingException e) {
                throw new ServerStateException(e.getLocalizedMessage());
            }
        });
        return states;
    }
}
