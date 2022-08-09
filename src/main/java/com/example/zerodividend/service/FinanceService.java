package com.example.zerodividend.service;

import com.example.zerodividend.exception.impl.NoCompanyException;
import com.example.zerodividend.model.Company;
import com.example.zerodividend.model.Dividend;
import com.example.zerodividend.model.ScrapedResult;
import com.example.zerodividend.model.constants.CacheKey;
import com.example.zerodividend.persist.CompanyRepository;
import com.example.zerodividend.persist.DividendRepository;
import com.example.zerodividend.persist.entity.CompanyEntity;
import com.example.zerodividend.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
    public ScrapedResult getDividendByCompanyName(String companyName) {

        CompanyEntity companyEntity =
                companyRepository.findByName(companyName).orElseThrow(NoCompanyException::new);

        List<DividendEntity> dividendEntities =
                this.dividendRepository.findAllByCompanyId(companyEntity.getId());

        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> new Dividend(
                        e.getDate(),
                        e.getDividend()
                )).collect(Collectors.toList());


        return new ScrapedResult(
                new Company(
                        companyEntity.getTicker(),
                        companyEntity.getName()),
                dividends);
    }

}
