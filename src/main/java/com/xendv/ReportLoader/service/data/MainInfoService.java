package com.xendv.ReportLoader.service.data;

import com.xendv.ReportLoader.model.MainInfo;
import io.micrometer.core.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface MainInfoService {

    @Nullable
    MainInfo create(@NotNull MainInfo mainInfo);
    // сделать метод на добавление коллекции

    void update(@NotNull BigDecimal okpo, @NotNull MainInfo mainInfo);

    void updateValuesIfNotNull(@NotNull MainInfo mainInfo);

    @Nullable
    MainInfo get(BigDecimal okpo);
    //Optional<MainInfo> get(BigDecimal okpo);

    @NotNull Iterable<MainInfo> getAll();

    void delete(@PathVariable BigDecimal okpo);
}
