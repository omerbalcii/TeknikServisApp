package com.bilgeadam.teknikservis.service;

import com.bilgeadam.teknikservis.model.Booking;
import com.bilgeadam.teknikservis.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService
{

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    

    public List<Booking> sortAscBooking() {
        try {
            return bookingRepository.sortAscBooking();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            return null;
        }
    }

    public List<Booking> sortDescBooking() {
        try {
            return bookingRepository.sortDescBooking();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            return null;
        }
    }

    public List<Booking> searchBookingsByUserName(String username) {
        try {
            return bookingRepository.searchBookingsByUserName(username);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateStatusToProcessingHeader(Long id) {
        try {
            return bookingRepository.updateStatusToProcessing(id);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatusToCompletedHeader(Long id) {
        
        try {
            
            return bookingRepository.updateStatusToCompleted(id);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            return false;
        }
    }
}
