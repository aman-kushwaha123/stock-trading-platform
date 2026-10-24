package com.example.services;

import com.example.DTOs.StockUpdateDTO;
import com.example.entities.Stock;
import org.springframework.data.domain.Page;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface StockService {

    Stock createStock(Stock stock);

    Stock getStockById(Long id);

    Page<Stock> getAllStock(int page,int size);

    Stock updateStockById(Long id, StockUpdateDTO stockUpdateDTO);

    Boolean deleteStockById(Long id);
}
