package com.example.controllers;

import com.example.ResponseDTOs.HoldingResponse;
import com.example.services.HoldingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.entities.Holding;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/holding")
public class HoldingController {
    private final HoldingService holdingService;
    protected HoldingController(HoldingService holdingService){
        this.holdingService=holdingService;
    }

    /*@PostMapping("/add")
    ResponseEntity<HoldingResponse> createHolding(@RequestBody Holding holding){
        try{
           Holding result= holdingService.createHolding(holding);
           System.out.println(holding);
           HoldingResponse holdingResponse=HoldingResponse
                   .builder()
                   .holding(result)
                   .message("Holding Successfully Added.")
                   .build();
           return ResponseEntity.ok(holdingResponse);
        }
        catch (IllegalArgumentException e){
            System.out.println(holding);
            HoldingResponse holdingResponse=HoldingResponse.builder()
                    .message("Provided Holding is null").build();
            return ResponseEntity.ok(holdingResponse);
        }
        catch(Exception e){
            HoldingResponse holdingResponse=HoldingResponse.builder()
                    .message("Something went wrong."+ e).build();
              return ResponseEntity.ok(holdingResponse);
        }
    }*/





    @GetMapping("/get/{id}")
    ResponseEntity<HoldingResponse> getById(@PathVariable(value="id") Long id){
            Holding result=holdingService.getHoldingById(id);
            HoldingResponse holdingResponse=HoldingResponse.builder()
                    .holding(result)
                    .message("Successfully Retrieval.").build();
            return ResponseEntity.ok(holdingResponse);

    }

    @GetMapping("/get/all")
    ResponseEntity<HoldingResponse> getAllHolding(@RequestParam(value = "page") int page,
                                                  @RequestParam(value="size") int size,
                                                  @RequestParam(value="userId") Long userId){

            Page<Holding> holdingsPage=holdingService.getAllHoldingByUserId(page,size,userId);
            HoldingResponse holdingResponse=HoldingResponse.builder()
                    .holdingsPage(holdingsPage)
                    .message("Successfully Fetched ALL Holdings.").build();
            return ResponseEntity.ok(holdingResponse);

    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<HoldingResponse> deleteHoldingById(@PathVariable(value="id") Long id){
            Boolean result=holdingService.deleteHoldingById(id);
            HoldingResponse holdingResponse=HoldingResponse.builder()
                    .message("Successfully holding deleted.").build();
            return ResponseEntity.ok(holdingResponse);

    }

    
}
