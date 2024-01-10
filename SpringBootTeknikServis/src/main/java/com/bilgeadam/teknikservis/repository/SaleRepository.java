package com.bilgeadam.teknikservis.repository;

import com.bilgeadam.teknikservis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class SaleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public SaleRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
		this.userRepository = userRepository;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Sale> getAll()
    {
        String sql = "SELECT * FROM public.\"SALE\" WHERE \"is_sold\"= false order by \"id\" asc";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sale.class));
    }
//    public List<SaleDTO> getAllDTO() {
//        String sql = "Select \"SALE\".id,\"SALE\".note,\"SALE\".price,\"PRODUCT\".\"name\" From \"SALE\" inner join \"PRODUCT\" on \"SALE\".product_id = \"PRODUCT\".id Where \"SALE\".is_sold=false";
//
//        RowMapper<SaleDTO> rowMapper = new RowMapper<ProposalAdminDto>() {
//            @Override
//            public SaleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
//                SaleDTO saleDTO = new SaleDTO();
//                saleDTO.setId(rs.getLong("id"));
//                saleDTO.setNote(rs.getString("note"));
//                saleDTO.setPrice(rs.getLong("price"));
//                saleDTO.setProduct(rs.getString("name"));
//                return saleDTO;
//            }
//        };
//        return jdbcTemplate.query(sql, rowMapper);
//    }

    public boolean deleteById(long id)
    {
        String sql = "DELETE FROM public.\"SALE\" where \"id\" = :ID";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(Sale sale)
    {
        String sql = "INSERT INTO public.\"SALE\"(note, price, product_id) VALUES (:NOTE, :PRICE, :PRODUCT_ID)";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("NOTE", sale.getNote());
        paramMap.put("PRICE", sale.getPrice());
        paramMap.put("PRODUCT_ID", sale.getProduct_id());
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }
    public List<Product> getAllProducts()
    {
        String sql = "SELECT * FROM public.\"PRODUCT\" order by \"id\" asc";
        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
    }
    public List<Sale> getSaleByProductName(String product_name) {
        String sql = "Select * From \"PRODUCT\" Inner Join \"SALE\" On \"SALE\".\"product_id\" = \"PRODUCT\".\"id\" Where \"is_sold\"= false and \"PRODUCT\".\"name\" = :PRODUCT_NAME";
        Map<String, String> param = new HashMap<>();
        param.put("PRODUCT_NAME", product_name);

        return namedParameterJdbcTemplate.query(sql, param,BeanPropertyRowMapper.newInstance(Sale.class));
    }
    public boolean Sale_Logsave(Sale sale) {
        String sql = "Insert Into \"SALE\"(\"note\", \"price\", \"product_id\") Values(:NOTE, :PRICE, :PRODUCT_ID)";
        Map<String, Object> params = new HashMap<>();
        params.put("NOTE", sale.getNote());
        params.put("PRICE", sale.getPrice());
        params.put("PRODUCT_ID", sale.getProduct_id());

        return jdbcTemplate.update(sql, params) == 1;
    }
    public boolean Sale_LogdeleteById(long id) {
        String sql = "Delete From \"SALE\" Where \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);

        return jdbcTemplate.update(sql, param) == 1;
    }
    public List<SaleDTO> getalldto()
    {
        String sql = "Select \"SALE\".id,\"SALE\".product_id,\"SALE\".note,\"SALE\".price,\"PRODUCT\".\"name\" From \"SALE\" inner join \"PRODUCT\" on \"SALE\".product_id = \"PRODUCT\".id Where \"SALE\".is_sold=false";
        RowMapper<SaleDTO> rowMapper = new RowMapper<SaleDTO>() {
            @Override
            public SaleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                SaleDTO saleDTO = new SaleDTO();
                saleDTO.setId(rs.getLong("id"));
                saleDTO.setNote(rs.getString("note"));
                saleDTO.setPrice(rs.getLong("price"));
                saleDTO.setProduct(rs.getString("name"));
                saleDTO.setProduct_id(rs.getLong("product_id"));
                return saleDTO;
            }
        };
        return jdbcTemplate.query(sql, rowMapper);
    }
}