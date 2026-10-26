package com.example.entities;

import com.example.Enums.TransactionStatus;
import com.example.Enums.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
//automatically foreign key like user_id or stock_id indexes will be get created if we don't mention in the @Table
@Entity
@Table(
        name="Transactions",
        indexes = {
                @Index(
                        name="idx_transaction_user_date",
                        columnList = "user_id, created_at"
                )
        }

)
public class Transaction {
    //Search by name → useful.
    //
    //Search by name and email → useful.
    //
    //Search by email alone → may not benefit much from this index.

    @Id
    @Column(nullable = false,unique = true)
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="stock_id",nullable = false)
    private Stock stock;

    @Column(name="quantity",nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type",nullable=false,length = 10)
    private TransactionType transactionType;

    @Column(name="price_per_share",nullable = false,precision = 15,scale=2)
    private BigDecimal pricePerShare;

    @Column(name="total_price",nullable = false,precision = 15,scale=2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false,length = 15)
    private TransactionStatus status=TransactionStatus.PENDING;

    @Column(name="created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        LocalDateTime now=LocalDateTime.now();
        this.createdAt=now;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        if(quantity<0){
            throw new IllegalArgumentException("Quantity cannot be less than 0");
        }
        this.quantity = quantity;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(BigDecimal pricePerShare) {
        if(pricePerShare.compareTo(BigDecimal.valueOf(0))<0){
            throw new IllegalArgumentException("PricePerShare cannot be less than 0.");
        }

        this.pricePerShare = pricePerShare;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        if(totalPrice.compareTo(BigDecimal.valueOf(0))<0){
            throw new IllegalArgumentException("totalPrice cannot be less than 0.");
        }
        this.totalPrice = totalPrice;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
