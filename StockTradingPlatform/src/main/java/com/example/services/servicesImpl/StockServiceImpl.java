package com.example.services.servicesImpl;

import com.example.DTOs.StockUpdateDTO;
import com.example.entities.Stock;
import com.example.repositories.Stock_Repo;
import com.example.services.StockService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private final Stock_Repo stockRepo;

    protected StockServiceImpl(Stock_Repo stockRepo){
        this.stockRepo=stockRepo;
    }

    @Override
    public Stock createStock(Stock stock) {
        if(stock==null){
            throw new IllegalArgumentException("Given stock is null");
        }
        Stock result=stockRepo.save(stock);
        return stock;
    }

    @Override
    public Stock getStockById(Long id) {
        Stock stock=stockRepo.findById(id).orElseThrow(()->new NullPointerException("Stock not Found"));
        return stock;
    }

    @Override
    public Page<Stock> getAllStock(int page, int size) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("price").ascending());
        return stockRepo.findAll(pageable);
    }

    @Override
    public Stock updateStockById(Long id, StockUpdateDTO stockUpdateDTO) {
        Stock stock1=stockRepo.findById(id).orElseThrow(()->new NullPointerException("Stock not found"));
        if(stockUpdateDTO.getCompanyName()!=null) {
            stock1.setCompanyName(stockUpdateDTO.getCompanyName());
        }
        if(stockUpdateDTO.getPrice()!=null) {
            stock1.setPrice((stockUpdateDTO.getPrice()));
        }
        if(stockUpdateDTO.getSymbol()!=null) {
            stock1.setSymbol(stockUpdateDTO.getSymbol());
        }
        Stock result = stockRepo.save(stock1);

        return result;
    }

    @Override
    public Boolean deleteStockById(Long id) {
        Stock stock=stockRepo.findById(id).orElseThrow(()->new NullPointerException("Stock not found"));
        if(id!=null && stock!=null){
            stockRepo.deleteById(id);
            return true;
        }
        else{
            return false;
        }

    }
}
