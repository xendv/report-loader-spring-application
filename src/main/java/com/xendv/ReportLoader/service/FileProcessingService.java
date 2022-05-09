package com.xendv.ReportLoader.service;

import com.xendv.ReportLoader.model.FullInfo;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface FileProcessingService {
    public List<FullInfo> extract(@NotNull String filePath);
}
