package com.example.controllers;

import com.example.DTOs.TransactionDTO;
import com.example.Enums.TransactionType;
import com.example.ResponseDTOs.TransactionResponseDTO;
import com.example.entities.Transaction;
import com.example.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;


    protected TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;

    }

    @PostMapping("/buy")
    ResponseEntity<TransactionResponseDTO> buyTrade(@RequestBody TransactionDTO transactionDTO){
           transactionDTO.setTransactionType(TransactionType.BUY);
             transactionService.buyTrade(transactionDTO);
             TransactionResponseDTO transactionResponseDTO=new TransactionResponseDTO();
             transactionResponseDTO.setMessage("Transaction added Successfully.");
             return ResponseEntity.ok(transactionResponseDTO);
    }

    @PostMapping("/sell")
    ResponseEntity<TransactionResponseDTO> sellTrade(@RequestBody TransactionDTO transactionDTO){
        transactionDTO.setTransactionType(TransactionType.SELL);

            transactionService.sellTrade(transactionDTO);
            TransactionResponseDTO transactionResponseDTO=new TransactionResponseDTO();
            transactionResponseDTO.setMessage("Transaction added Successfully.");
            return ResponseEntity.ok(transactionResponseDTO);


    }

    @GetMapping("/get/all")
    ResponseEntity<TransactionResponseDTO> getAllTransactionByUserId(@RequestParam(value = "userId") Long userId,
                                                    @RequestParam(value="page") int page,
                                                    @RequestParam(value="size") int size){

            Page<Transaction> allTransactions=transactionService.getAllTransactionById(page,size,userId);
            TransactionResponseDTO transactionResponseDTO=new TransactionResponseDTO();
            transactionResponseDTO.setTransactionsByUserId(allTransactions);
            transactionResponseDTO.setMessage("Successfully Fetched All Transaction.");
            return ResponseEntity.ok(transactionResponseDTO);

    }


}
