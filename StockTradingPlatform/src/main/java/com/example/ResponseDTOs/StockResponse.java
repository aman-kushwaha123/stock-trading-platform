package com.example.ResponseDTOs;

import com.example.entities.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {
    private Stock stock=null;
    private Page<Stock> stockPage=null;
    private String message;


}
