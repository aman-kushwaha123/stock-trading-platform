package com.example.controllers;

import com.example.ResponseDTOs.TransactionResponseDTO;
import com.example.entities.Transaction;
import com.example.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;
    private Logger logger;

    protected TransactionController(TransactionService transactionService,
    Logger logger){
        this.transactionService=transactionService;
        this.logger=logger;
    }

    @PostMapping("/buy")
    ResponseEntity<TransactionResponseDTO> buyTrade(@RequestBody Transaction transaction){
        try{
             transactionService.buyTrade(transaction);
             TransactionResponseDTO transactionResponseDTO=new TransactionResponseDTO();
             transactionResponseDTO.setMessage("Transaction added Successfully.");
             return ResponseEntity.ok(transactionResponseDTO);
        }
        catch(Exception e){

            // ALWAYS log the actual exception stack trace so engineers can debug it
            log.error("Unexpected error occurred", e);
            TransactionResponseDTO transactionResponseDTO=new TransactionResponseDTO();
            transactionResponseDTO.setMessage("Something went wrong.");
            return ResponseEntity.ok(transactionResponseDTO);
        }
    }
}
