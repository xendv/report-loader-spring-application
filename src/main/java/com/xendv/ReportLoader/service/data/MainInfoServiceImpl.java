package com.xendv.ReportLoader.service.data;

import com.xendv.ReportLoader.model.MainInfo;
import com.xendv.ReportLoader.repository.MainInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service("mainInfoService")
public class MainInfoServiceImpl implements MainInfoService {

    @Autowired
    private MainInfoRepository mainInfoRepository;

    @Override
    public MainInfo create(MainInfo mainInfo) {
        return mainInfoRepository.save(mainInfo);
    }
    // сделать метод на добавление коллекции

    @Override
    public void update(BigDecimal okpo,
                       MainInfo mainInfo) {
        if (mainInfoRepository.existsById(okpo)) {
            mainInfoRepository.save(mainInfo);
        } else throw new NoSuchElementException("No info about company with okpo " + okpo);
    }

    @Override
    public void updateValuesIfNotNull(MainInfo mainInfo) {
        final var entry = mainInfoRepository.findById(mainInfo.getOkpo());
        if (entry.isPresent()) {
            mainInfoRepository.updateValues(mainInfo.okpo, mainInfo.name);
        } else {
            create(mainInfo);
        }
    }

    @Override
    public @Nullable
    MainInfo get(@PathVariable BigDecimal okpo) {
        return mainInfoRepository.findById(okpo).orElse(null);
    }

/*    @Override
    public Optional<MainInfo> get(@PathVariable BigDecimal okpo) {
        return mainInfoRepository.findById(okpo);
    }*/

    @Override
    public Iterable<MainInfo> getAll() {
        return mainInfoRepository.findAll();
    }

    @Override
    public void delete(@PathVariable BigDecimal okpo) {
        mainInfoRepository.deleteById(okpo);
    }

}
