package com.example.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name="portfolio_snapshots",
        indexes= {
                @Index(
                     name =" idx_snapshot_user_date",
                        columnList ="user_id,recorded_at"
        )
        }
)
public class PortfolioSnapshot {

    @Id
    @Column(nullable = false,unique = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column(name="cash_balance",nullable = false,precision = 15,scale=2)
    private BigDecimal cashBalance;

    //• Stock Value means the total current market value
    // of all stocks the user owns right now (Total Shares \(\times \) Current Share Price).
    //otal Stock Value = (Company A Shares multiplied by Price A) + (Company B Shares multiplied by Price B) +
    // ... and so on for every company owned.
    @Column(name="stock_value",nullable = false,precision = 15,scale=2)
    private BigDecimal stockValue;

    //• Total Portfolio Value is NOT just the money used to buy stocks.
    // It represents the entire net worth of the user's trading account at that exact second.
    //cashBalance + total stock purchased or total investment of all owned stocks
    @Column(name="total_portfolio_value",nullable = false,precision = 15,scale=2)
    private BigDecimal totalPortFolioValue;

    @Column(name="recorded_at",nullable = false,updatable = false)
    private LocalDateTime recordedAt;

    @PrePersist
    protected  void onCreate(){
        LocalDateTime now=LocalDateTime.now();
        this.recordedAt=now;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public BigDecimal getStockValue() {
        return stockValue;
    }

    public void setStockValue(BigDecimal stockValue) {
        this.stockValue = stockValue;
    }

    public BigDecimal getTotalPortFolioValue() {
        return totalPortFolioValue;
    }

    public void setTotalPortFolioValue(BigDecimal totalPortFolioValue) {
        this.totalPortFolioValue = totalPortFolioValue;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}
