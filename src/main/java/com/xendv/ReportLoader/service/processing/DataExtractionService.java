package com.xendv.ReportLoader.service.processing;

import com.xendv.ReportLoader.model.FullInfo;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DataExtractionService {

    List<FullInfo> extract(@NotNull String filePath);

}
