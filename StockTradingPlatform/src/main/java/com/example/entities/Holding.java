package com.example.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

//Unique constraint on multiple columns otherwise unique=true
//Because stock_id is a foreign key,
// MySQL requires its own index to maintain database integrity—hence the random extra one.
@Entity
@Table(
        name="holdings",
        uniqueConstraints ={
                @UniqueConstraint(
                        name="uq_user_stock",
                        columnNames = {"user_id","stock_id"}
                )
        }


)
class Holding {
    //@Id knows that this is a primary key
    //sequence meaning while saving it will not ask db for permission to assigning id db give prebook of id to assign
    @Id
    @Column(nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //child side (owning side) || bidirectional
    //Bidirectional only on ManytoOne and OnetoMany
    //EAGER: Loads parent + all child rows together right at the start. (Bad for performance with transactional data).
    //• LAZY: Loads parent first. It waits, then loads
    // all child rows together only when you explicitly call the getter method. (Best practice for performance).
    //Optional:-It tells the ORM that a child entity cannot exist without its parent.

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="stock_id",nullable=false)
    private Stock stock;

    @Column(nullable = false)
    private Integer quantity=0;

    // the Average Buy Price (also called Avg Cost) is the average price you paid per share for a specific stock.
    @Column(name="average_buy_price",nullable = false,precision = 15,scale=2)
    private BigDecimal averageBuyPrice;

    @Column(name="created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at",nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate(){
        /*if(id==null){
            id= UUID.randomUUID().toString();
        }*/
        LocalDateTime now=LocalDateTime.now();
        this.createdAt=now;
        this.updatedAt=now;
    }



    @PreUpdate
    protected void onUpdate(){
        LocalDateTime now=LocalDateTime.now();
        this.updatedAt=now;
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
        this.quantity = quantity;
    }

    public BigDecimal getAverageBuyPrice() {
        return averageBuyPrice;
    }

    public void setAverageBuyPrice(BigDecimal averageBuyPrice) {
        this.averageBuyPrice = averageBuyPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }





}
