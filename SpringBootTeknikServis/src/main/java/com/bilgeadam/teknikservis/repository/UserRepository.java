package com.bilgeadam.teknikservis.repository;

import com.bilgeadam.teknikservis.model.Role;
import com.bilgeadam.teknikservis.model.SystemUser;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    private PasswordEncoder passwordEncoder;


    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }


    public List<GrantedAuthority> getUserRoles(String username) {
        String sql = "SELECT \"authority\" FROM \"public\".\"AUTHORITIES\" where \"username\" = :USERNAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("USERNAME", username);
        // beanpropertymapper kullanıyorduk ama burada gerek yok çünkü tek sütun select
        // yapıyorum
        List<String> liste = namedParameterJdbcTemplate.queryForList(sql, paramMap, String.class);
        List<GrantedAuthority> roles = new ArrayList<>();
        for (String role : liste) {
            roles.add(new Role(role));
        }
        return roles;
    }


    public SystemUser getByUsername(String username) {
        String sql = "select * from \"public\".\"USERS\" where \"username\" = :username";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(SystemUser.class));

    }

    public boolean save(SystemUser systemUser) {


        String insertUsers = "insert into \"public\".\"USERS\" (\"username\",\"email\",\"password\") values (:username, :email, :password)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", systemUser.getUsername());
        paramMap.put("email", systemUser.getEmail());
        systemUser.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        paramMap.put("password", systemUser.getPassword());
        boolean result = namedParameterJdbcTemplate.update(insertUsers, paramMap) == 1;

        //İnsert user Roles
        String insertRoles = "insert into \"public\".\"AUTHORITIES\" (\"username\",\"authority\") values (:username, :authority)";
        Map<String, Object> paramMap2 = new HashMap<>();
        paramMap2.put("authority", "ROLE_USER");
        paramMap2.put("username", systemUser.getUsername());


        if (result == true) {
            return namedParameterJdbcTemplate.update(insertRoles, paramMap2) == 1;
        }
        return false;
    }

    public List<SystemUser> getall() {
        return jdbcTemplate.query("select * from \"public\".\"USERS\" order by \"id\" asc", BeanPropertyRowMapper.newInstance(SystemUser.class));
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            return null;

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public long getCurrentUserId() {
        String username = getCurrentUsername();
        String sql = "Select \"id\" From \"USERS\" Where \"username\" = :USERNAME";
        Map<String, String> param = new HashMap<>();
        param.put("USERNAME", username);

        return namedParameterJdbcTemplate.queryForObject(sql, param, Long.class);
    }
}
