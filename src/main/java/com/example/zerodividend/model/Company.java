package com.example.zerodividend.model;

import com.example.zerodividend.persist.entity.CompanyEntity;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    private String ticker;
    private String name;

    public Company (CompanyEntity companyEntity){
        this.ticker = companyEntity.getTicker();
        this.name = companyEntity.getName();
    }

}
