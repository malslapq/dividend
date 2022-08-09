package com.example.zerodividend.scraper;

import com.example.zerodividend.model.Company;
import com.example.zerodividend.model.ScrapedResult;

public interface Scraper {

    Company scrapCompanyByTicker(String ticker);

    ScrapedResult scrap(Company company);

}
