package com.bilgeadam.teknikservis.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.teknikservis.model.Sale;
import com.bilgeadam.teknikservis.model.Sale_log;

@Repository
public class Sale_logRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public Sale_logRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate, UserRepository userRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.userRepository = userRepository;
	}
    
    public boolean save(Sale_log saleLog) {
        String sql = "INSERT INTO \"SALE_LOG\"(\"sale_log_date\", \"credit_card\", \"sale_id\", \"user_id\") Values(:SALE_LOG_DATE, :CREDIT_CARD, :SALE_ID, :USER_ID)";
        Map<String, Object> params = new HashMap<>();
        params.put("SALE_LOG_DATE", new Date());
        params.put("CREDIT_CARD", saleLog.getCredit_card());
        params.put("SALE_ID", saleLog.getSale_id());
        params.put("USER_ID", userRepository.getCurrentUserId());
        long userid=userRepository.getCurrentUserId();
        boolean isUpdate = updateExistSale(saleLog.getSale_id());
        if (isUpdate)
            return namedParameterJdbcTemplate.update(sql, params) == 1;
        return false;
    }
	public boolean updateExistSale(long id) {
        String sqlSale = "UPDATE \"SALE\" SET  \"is_sold\"=:IS_SOLD WHERE \"id\"=:ID";
        Map<String, Object> param = new HashMap<>();
        param.put("ID", id);
        param.put("IS_SOLD", true);

        return namedParameterJdbcTemplate.update(sqlSale, param) == 1;
    }
}
