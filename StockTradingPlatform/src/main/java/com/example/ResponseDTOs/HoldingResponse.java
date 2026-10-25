package com.example.ResponseDTOs;

import com.example.entities.Holding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoldingResponse {
    private Holding holding=null;
    private Page<Holding> holdingsPage=null;
    private String message;
}
