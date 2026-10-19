package com.example.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(nullable = false,unique = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(name="username",nullable = false)
    private String username;

    @Column(name = "cash_balance",nullable = false)
    private BigDecimal cashBalance;

    //parent(user) or inverse side and child(holdings)
    //orphanRemoval:-automatically deletes child entities when they are removed from their parent's collection
    // merge or update,remove,refresh,persist operation can cascade from parent to child
    //cascade:-automatically deletes child entities when they are removed from their parent's collection
    //mappedBy --> exact name assigned in the child class(Holdings) or owning side
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch=FetchType.LAZY
    )
    List<Holding> holdings=new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade=CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.LAZY
    )
    List<Transaction> transactions=new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval=true,
            fetch=FetchType.LAZY
    )
    List<PortfolioSnapshot> portfolioSnapshots=new ArrayList<>();

/*@PrePersist and @PreUpdate are lifecycle callback annotations.
 They allow you to execute custom code automatically right before an entity is inserted into the database or updated.
 */
    /*@PrePersist
    public void generateId(){
        if(userId==null){
            userId= UUID.randomUUID().toString();
        }
    }*/

    public Long getId() {
        return id;
    }


    public void setUsername(String username){
        this.username=username;
    }

    public String getUsername(){
        return username;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }
}
