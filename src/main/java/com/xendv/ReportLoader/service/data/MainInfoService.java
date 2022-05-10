package com.xendv.ReportLoader.service.data;

import com.xendv.ReportLoader.model.MainInfo;
import io.micrometer.core.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface MainInfoService {

    @Nullable
    MainInfo create(@RequestBody MainInfo mainInfo);
    // сделать метод на добавление коллекции

    void update(@PathVariable BigDecimal okpo, MainInfo mainInfo);

    @Nullable
    MainInfo get(BigDecimal okpo);

    @NotNull Iterable<MainInfo> getAll();

    void delete(@PathVariable BigDecimal okpo);
}
