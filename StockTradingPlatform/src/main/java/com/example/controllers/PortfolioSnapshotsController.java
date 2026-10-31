package com.example.controllers;

import com.example.entities.PortfolioSnapshot;
import com.example.services.PortfolioSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolioSnapshots")
public class PortfolioSnapshotsController {

    @Autowired
    private PortfolioSnapshotService portfolioSnapshotService;

    @GetMapping("")
    ResponseEntity<Page<PortfolioSnapshot>> getPortfolioSnapshotsByUserId(
            @RequestParam(value="userId") Long userId,
            @RequestParam(value="page") int page,
            @RequestParam(value="size") int size
    ){
        Page<PortfolioSnapshot> portfolioSnapshots=portfolioSnapshotService.getPortfolioSnapshotByUserId(page,size,userId);
        return ResponseEntity.ok(portfolioSnapshots);
    }
}
