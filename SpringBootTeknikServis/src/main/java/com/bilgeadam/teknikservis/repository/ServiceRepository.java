package com.bilgeadam.teknikservis.repository;

import com.bilgeadam.teknikservis.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ServiceRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ServiceRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getServiceDurationById(long id) {
        String sql = "SELECT \"duration\" FROM \"SERVICE\" WHERE \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);

        return jdbcTemplate.queryForObject(sql, param, Integer.class);
    }

    public List<Service> getAll(){
        return jdbcTemplate.query("Select * From \"SERVICE\"", BeanPropertyRowMapper.newInstance(Service.class));
    }
    public String getServiceNameById(long id){
        String sql = "Select \"name\" From \"SERVICE\" where \"id\" = :ID";
        Map<String,Long> param = new HashMap<>();
        param.put("ID", id);

        return jdbcTemplate.queryForObject(sql,param, String.class);

    }
}