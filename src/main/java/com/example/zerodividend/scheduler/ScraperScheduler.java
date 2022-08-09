package com.example.zerodividend.scheduler;

import com.example.zerodividend.model.Company;
import com.example.zerodividend.model.ScrapedResult;
import com.example.zerodividend.model.constants.CacheKey;
import com.example.zerodividend.persist.CompanyRepository;
import com.example.zerodividend.persist.DividendRepository;
import com.example.zerodividend.persist.entity.CompanyEntity;
import com.example.zerodividend.persist.entity.DividendEntity;
import com.example.zerodividend.scraper.Scraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
@EnableCaching
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Scraper scraper;

    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {

        log.info("scraping scheduler is started");

        List<CompanyEntity> companyEntities = companyRepository.findAll();
        for (CompanyEntity companyEntity : companyEntities) {
            log.info("scraping scheduler is started -> " + companyEntity.getName());
            ScrapedResult scrapedResult =
                    this.scraper.scrap(new Company(companyEntity.getTicker(), companyEntity.getName()));
            scrapedResult.getDividends().stream()
                    .map(e -> new DividendEntity(companyEntity.getId(), e))
                    .forEach(e -> {
                        boolean exists = this.dividendRepository.existsByCompanyIdAndDate(
                                e.getCompanyId(),
                                e.getDate());
                        if (!exists) {
                            this.dividendRepository.save(e);
                            log.info("insert new dividend -> " + e.toString());
                        }
                    });
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
