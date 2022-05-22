package com.xendv.ReportLoader.service.data;

import com.xendv.ReportLoader.model.FullInfo;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public interface FullInfoService {

    void saveAll(@NotNull List<FullInfo> fullInfos);

    Map<String, String> getStates(@NotNull List<FullInfo> fullInfos);
}
