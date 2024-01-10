package com.bilgeadam.teknikservis.repository;

import com.bilgeadam.teknikservis.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.*;

@Repository
public class BookingRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public BookingRepository(NamedParameterJdbcTemplate jdbcTemplate, UserRepository userRepository, ServiceRepository serviceRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    public long getCurrentId() {
        try {
            long numb = jdbcTemplate.queryForObject("Select Max(\"id\") + 1 From \"BOOKING\"", new MapSqlParameterSource(), Long.class);
            return numb;
        } catch (Exception e) {
            return 1;
        }
    }
    public long getLastId() {
        try {
            long numb = jdbcTemplate.queryForObject("Select Max(\"id\") From \"BOOKING\"", new MapSqlParameterSource(), Long.class);
            return numb;
        } catch (Exception e) {
            return 1;
        }
    }

    public List<Booking> getAll() {
        return jdbcTemplate.query("Select * From \"BOOKING\"", BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public Booking getById(long id) {
        String sql = "Select * From \"BOOKING\" Where \"id\" = :ID";
        Map<String, Long> param = new HashMap<>();
        param.put("ID", id);

        return jdbcTemplate.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public List<Booking> getAllForUser(long userId) {
        String sql = "Select * From \"BOOKING\" Where \"user_id\" = :USER_ID Order By \"booking_date\" ASC";
        Map<String,Long> param = new HashMap<>();
        param.put("USER_ID",userId);
        return jdbcTemplate.query(sql,param,BeanPropertyRowMapper.newInstance(Booking.class));
    }

    public boolean deleteById(long id) {
        long currentUserId = userRepository.getCurrentUserId();
        Booking booking = getById(id);
        if (booking.getUser_id() == currentUserId) {
            String sql = "Delete From \"BOOKING\" Where \"id\" = :ID";
            Map<String, Long> param = new HashMap<>();
            param.put("ID", id);

            return jdbcTemplate.update(sql, param) == 1;
        } else {
            return false;
        }
    }

    public boolean save(Booking booking) throws ParseException {

        Date date = getBookingDate(booking);

        String sql = "INSERT INTO \"BOOKING\"(\"id\", \"note\", \"booking_date\", \"status\", \"service_id\", \"user_id\") VALUES (:ID,:NOTE, :BOOKING_DATE, :STATUS, :SERVICE_ID, :USER_ID)";
        Map<String, Object> params = new HashMap<>();

        params.put("ID", booking.getId());
        params.put("NOTE", booking.getNote());
        params.put("BOOKING_DATE", date);
        params.put("STATUS", "Bekliyor"); //according to the doc record must be bekliyor at the beginning
        params.put("SERVICE_ID", booking.getService_id());
        params.put("USER_ID", userRepository.getCurrentUserId());

        return jdbcTemplate.update(sql, params) == 1;
    }

    public int sumOfHours(Date date) {
        String sql = "Select SUM(\"duration\") From \"SERVICE\" Inner Join \"BOOKING\" On \"BOOKING\".\"service_id\" = \"SERVICE\".\"id\" Where \"BOOKING\".\"booking_date\" = :SYSTEM_DATE";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("SYSTEM_DATE", date);

        Integer sum = jdbcTemplate.queryForObject(sql, parameters, Integer.class); // Use the parameters object
        if (sum == null) {
            return 0; // Use 0 hours if system has no record
        }
        return sum;
    }

    public boolean isAvailable(Booking booking, Date date) {
        int currentBookingDuration = serviceRepository.getServiceDurationById(booking.getService_id());
        int totalBookingDurationInQueue = sumOfHours(date);

        if (totalBookingDurationInQueue + currentBookingDuration > 10)
            return false;
        else
            return true;
    }

    public Date getBookingDate(Booking booking) {
        Date date = new Date(); // Start with the current date
        Calendar calendar = Calendar.getInstance();

        while (true) {
            if (isAvailable(booking, date)) {
                return date; // Return the date if it has enough available hours
            }
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Move to the next day
            date = calendar.getTime();
        }
    }
    
    public List<Booking> sortDescBooking()
    {
        String sql = "select * from \"public\".\"BOOKING\" order by \"booking_date\" desc";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Booking.class));
    }
    
    public List<Booking> sortAscBooking()
    {
        String sql = "select * from \"public\".\"BOOKING\" order by \"booking_date\" asc";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Booking.class));
    }
    
    
    
    public List<Booking> searchBookingsByUserName(String username)
    {
        String sql =
                "select \"BOOKING\".\"id\", \"BOOKING\".\"note\", \"BOOKING\".\"status\", \"BOOKING\".\"service_id\", \"BOOKING\".\"user_id\" from " +
                "\"public\".\"BOOKING\" " +
                " Inner Join \"USERS\" ON " +
                "\"USERS\".\"id\" = " +
                "\"BOOKING\".\"user_id\" WHERE " +
                "\"USERS\"" +
                ".\"username\" LIKE :UserName";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("UserName", "%" + username + "%");
        return jdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(Booking.class));
    }
    
    public boolean updateStatusToProcessing(long id)
    {
        String sql = "UPDATE public.\"BOOKING\" SET \"status\"= 'işleme alındı' WHERE \"id\" = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return jdbcTemplate.update(sql, paramMap) == 1;
    }
    
    public boolean updateStatusToCompleted(long id)
    {
        String sql = "UPDATE public.\"BOOKING\" SET \"status\"= 'tamamlandı' WHERE \"id\" = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return jdbcTemplate.update(sql, paramMap) == 1;
    }
}