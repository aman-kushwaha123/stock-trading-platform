package com.example.ResponseDTOs;

import com.example.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponseDTO {
    @NotNull
    private String token=null;
    @NotNull
    private User user=null;

}
