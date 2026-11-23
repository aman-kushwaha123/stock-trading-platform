package com.example.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Stock_Trading_Platform(AkTrade)")
                        .description("Rest apis for AKTrade : holdings,stocks,transaction(Buy and Sell),portfolio,etc")
                        .summary("Ultimately,user can buy and sell trade.")
                        .contact(new Contact()
                                        .name("Ak Technologies.")
                                        .email("ak@gmail.com")
                                        .url("https://aktechnologies.com")
                                )


                );
    }

}
