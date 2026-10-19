package com.example.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="stocks")
public class Stock {

    @Id
    @Column(nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;


    @Column(nullable=false,unique = true)
    String symbol;

    @Column(name="company_name",nullable=false)
    String companyName;

    //We use BigDecimal for money instead of double to avoid common floating-point rounding errors in financial calculations.
    @Column(nullable=false,precision = 15,scale=2)
    BigDecimal price;

    //parent(user) and child(holding)
    @OneToMany(
            mappedBy = "stock",
            cascade=CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    List<Holding>  holdings=new ArrayList<>();

    @OneToMany(
            mappedBy="stock",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch=FetchType.LAZY
    )
    List<Transaction> transactions=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
