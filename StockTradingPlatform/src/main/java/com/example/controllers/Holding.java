package com.example.controllers;

import com.example.services.HoldingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/holding")
public class Holding {
    private final HoldingService holdingService;
    protected Holding(HoldingService holdingService){
        this.holdingService=holdingService;
    }
    
}
