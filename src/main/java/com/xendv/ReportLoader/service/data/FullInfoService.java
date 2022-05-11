package com.xendv.ReportLoader.service.data;

import com.xendv.ReportLoader.model.FullInfo;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface FullInfoService {

    void saveAll(@NotNull List<FullInfo> fullInfos);

}
