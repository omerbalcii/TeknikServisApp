package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Sale;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.teknikservis.model.Sale_log;
import com.bilgeadam.teknikservis.repository.Sale_logRepository;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController()
@RequestMapping("/buy")
@io.swagger.v3.oas.annotations.tags.Tag(description = "Sale Log Endpointleri", name = "Sale Log")
public class Sale_logController{
	private final Sale_logRepository saleLogRepository;
    private final MessageSource messageSource;

    @Autowired
    public Sale_logController(Sale_logRepository saleLogRepository, MessageSource messageSource) {
        this.saleLogRepository = saleLogRepository;
        this.messageSource = messageSource;
    }

    @PostMapping("/product")
    @Operation(description = "Başarılı olursa 200", summary ="Satın alımı gösterir")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Başarılı olursa"),@ApiResponse(responseCode = "500", description = "Başarılı olmaz ise")})

    public ResponseEntity<String> buyProduct(Locale locale, @RequestBody Sale_log saleLog) {
        boolean result = saleLogRepository.save(saleLog);
        if (result)
            return ResponseEntity.ok(messageSource.getMessage("saleLog.buyProduct.success", null , locale));
        else
            return ResponseEntity.internalServerError().build();
    }
    @PostMapping("/saveAll")
    public ResponseEntity<String> saveAllSales(@RequestBody List<Sale_log> sales) {
        try {
            for (Sale_log sale : sales) {
                saleLogRepository.save(sale);
            }
            return ResponseEntity.ok("Sales saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving sales: " + e.getMessage());
        }
    }
}
