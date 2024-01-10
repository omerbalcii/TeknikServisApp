package com.bilgeadam.teknikservis.service;

import com.bilgeadam.teknikservis.model.Sale;
import com.bilgeadam.teknikservis.model.SaleDTO;
import com.bilgeadam.teknikservis.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> getAll(){
        return saleRepository.getAll();
    }

    public boolean deleteById(long id){
        return saleRepository.deleteById(id);
    }

    public boolean save (Sale sale)
    {
        try {
            return saleRepository.save(sale);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

}