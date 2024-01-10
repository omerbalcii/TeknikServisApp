package com.bilgeadam.teknikservis.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.teknikservis.model.Product;
import com.bilgeadam.teknikservis.model.Sale;
@Repository
public class ProductRepository {
	private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public ProductRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.userRepository = userRepository;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	 public List<Product> getAll()
	    {
	        String sql = "SELECT * FROM public.\"PRODUCT\" order by \"id\" asc";
	        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
	    }

	public Long getProductIdByName(String product_name) {
		String sql = "Select \"id\" From \"PRODUCT\" Where \"name\" = :PRODUCT_NAME";
		Map<String, String> param = new HashMap<>();

		param.put("PRODUCT_NAME", product_name);

		return namedParameterJdbcTemplate.queryForObject(sql, param, Long.class);
	}

	//THIS METHOD FOR PROPOSAL_DTO
    /*
    public String getProductNameById(Long id) {
        String sql = "Select \"name\" From \"PRODUCT\" Where \"id\" = :PRODUCT_ID";
        Map<String, Long> param = new HashMap<>();

        param.put("PRODUCT_ID", id);

        return namedParameterJdbcTemplate.queryForObject(sql, param, String.class);
    }
     */
}
