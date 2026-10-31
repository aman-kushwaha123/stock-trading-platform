package com.example.Config;

import com.example.entities.User;
import com.example.repositories.User_Repo;
import com.example.services.PortfolioSnapshotService;
import com.example.services.servicesImpl.PortfolioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PortfolioScheduling {

    private PortfolioSnapshotService portfolioSnapshotService;

    PortfolioScheduling(PortfolioSnapshotService portfolioSnapshotService){
        this.portfolioSnapshotService=portfolioSnapshotService;
    }

     @Autowired
     private User_Repo userRepo;



     //This example runs at 4 PM IST, Monday to Friday.
    //second minute hour day of month month day of week
     @Scheduled(cron = "0 0 16 * * MON-FRI",zone = "Asia/Kolkata")
     public void generatePortfolio(){
         List<User> users=userRepo.findAll();
         for(User user:users){
             portfolioSnapshotService.createPortfolioSnapshot(user.getId());
         }
     }

}
