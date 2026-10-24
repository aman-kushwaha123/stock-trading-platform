package com.example.controllers;

import com.example.DTOs.StockUpdateDTO;
import com.example.ResponseDTOs.StockResponse;
import com.example.entities.Stock;
import com.example.services.StockService;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    protected StockController(StockService stockService){
        this.stockService=stockService;
    }

    @PostMapping("/add")
    ResponseEntity<Map<String,String>> createStock(@RequestBody Stock stock) {
         try{
             Stock result=stockService.createStock(stock);
             return ResponseEntity.ok(Map.of("message","Stock "+stock.getSymbol()+" Successfully added."));
         }
         catch (IllegalArgumentException e){
             return ResponseEntity.ok(Map.of("message","Given stock is null."));
         }
         catch (Exception e){
            return ResponseEntity.ok(Map.of("message","Something went wrong. "));
         }
    }


    @PutMapping("/update/{id}")
    ResponseEntity<Map<String,String>> updateStock(@PathVariable(value="id") Long id, @RequestBody StockUpdateDTO stock){
        try{
            Stock result=stockService.updateStockById(id,stock);
            return ResponseEntity.ok(Map.of("message","Stock Successfully Updated."));
        }
        catch(Exception e){
            return ResponseEntity.ok(Map.of("message","Something went wrong."));
        }
    }

    @GetMapping("/get/{id}")
    ResponseEntity<StockResponse> getStockById(@PathVariable(value="id") Long id){
        try{
            Stock result=stockService.getStockById(id);
            StockResponse stockResponse=new StockResponse();
            stockResponse.setStock(result);
            stockResponse.setMessage("Stock Successfully retrieved");
            return ResponseEntity.ok(stockResponse);
        }
        catch (NullPointerException e){
            StockResponse stockResponse=new StockResponse();
            stockResponse.setStock(null);
            stockResponse.setMessage("Stock not found : "+e.toString());
            return  ResponseEntity.ok(stockResponse);
        }
        catch(Exception e){
            StockResponse stockResponse=new StockResponse();
            stockResponse.setStock(null);
            stockResponse.setMessage("Something went wrong : "+e.toString());
            return  ResponseEntity.ok(stockResponse);
        }
    }

    @GetMapping("/get/all")
    ResponseEntity<StockResponse> getAllStock(@RequestParam(value = "page") int page,
                                            @RequestParam(value="size") int size){
        try{
            Page<Stock> result=stockService.getAllStock(page,size);
            StockResponse stockResponse=new StockResponse();
            stockResponse.setStockPage(result);
            stockResponse.setMessage("Successfully Retrieved All stocks.");
            return ResponseEntity.ok(stockResponse);
        }
        catch (Exception e){
            StockResponse stockResponse=new StockResponse();
            stockResponse.setMessage("Something went wrong.");
            return ResponseEntity.ok(stockResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<StockResponse> deleteStock(@PathVariable(value="id") Long id) {

        try {
            Boolean result = stockService.deleteStockById(id);
            StockResponse stockResponse = new StockResponse();
            if (result == true) {

                stockResponse.setMessage("Successfully Stock Deleted.");
                return ResponseEntity.ok(stockResponse);
            } else {
                stockResponse.setMessage("Provided Id is null.");
                return ResponseEntity.ok(stockResponse);
            }
        }
        catch(NullPointerException e){
            StockResponse stockResponse = new StockResponse();
            stockResponse.setMessage("Stock not found.");
            return ResponseEntity.ok(stockResponse);
        }
    }



}
