package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Booking;
import com.bilgeadam.teknikservis.repository.BookingRepository;
import com.bilgeadam.teknikservis.repository.UserRepository;
import com.bilgeadam.teknikservis.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("/booking")
@io.swagger.v3.oas.annotations.tags.Tag(description = "Booking Endpointleri", name = "Booking")
public class BookingController
{
    
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    
    @Autowired
    public BookingController(BookingRepository repository, UserRepository userRepository, BookingService bookingService, UserRepository userRepository1, MessageSource messageSource)
    {
        this.bookingRepository = repository;
        this.bookingService = bookingService;
        this.userRepository = userRepository1;
        this.messageSource = messageSource;
    }
    
    @GetMapping("/user/getById/{id}")
    @Operation(parameters = @Parameter(name = "id", description = "istenen id"), description = "Bulunursa 200 bulunamazsa 404", summary = "ID ile getir")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Bulunursa"), @ApiResponse(responseCode = "404", description = "Bulunamazsa") ,@ApiResponse(responseCode = "403", description = "Rol yanlışsa") })

    public ResponseEntity<Booking> getById(@PathVariable long id)
    {
        return ResponseEntity.ok(bookingRepository.getById(id));
    }
    @GetMapping("/user/getAll")
    public ResponseEntity<List<Booking>> getAllForUser()
    {
        return ResponseEntity.ok(bookingRepository.getAllForUser(userRepository.getCurrentUserId()));
    }
    
    @PostMapping("/user/save")
    @Operation(description = "Başarılı olursa 200", summary ="Sistemdeki kullanıcıları gösterir")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Başarılı olursa")})
    public ResponseEntity<Booking> appointment(@RequestBody Booking booking)
    {
        try
        {
            long max = bookingRepository.getCurrentId();
            booking.setId(max);
            boolean result = bookingRepository.save(booking);
            Booking currentBooking = bookingRepository.getById(max);
            if (result)
            {
                return ResponseEntity.ok(currentBooking);
            }
            else
            {
                return ResponseEntity.internalServerError().build();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/user/deleteById/{id}")
    @Operation(parameters = @Parameter(name = "id", description = "istenen id"), description = "Bulunursa 200 bulunamazsa 400", summary = "ID ile sil")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Silinirse"), @ApiResponse(responseCode = "400", description = "Silinmez ise")})

    public ResponseEntity<String> deleteById(Locale locale, @PathVariable long id)
    {
        Object[] params = new Object[1];
        params[0] = id;
        boolean result = bookingRepository.deleteById(id);
        if (result)
        {
            return ResponseEntity.ok(messageSource.getMessage("booking.delete.success" , params, locale));
        }
        else
        {
            return ResponseEntity.badRequest().body(messageSource.getMessage("booking.delete.error" , params, locale));
        }
    }
    
    // Randevuları tarihine göre artan ve azalan header alıp sıralayarak döndürür.
    @GetMapping(path = "/admin/sortbooking", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Başarılı olursa 200 olmazssa 400", summary ="Sıralama")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Başarılı olursa"),@ApiResponse(responseCode = "400", description = "Başarılı olmazssa")})
    public ResponseEntity<List<Booking>> sortBooking(
            @RequestHeader(name = "sortType") String sortType)
    {

        // localhost:8080/booking/sortbooking
        try
        {
            if (sortType.equalsIgnoreCase("ascending") || sortType.equalsIgnoreCase("asc"))
            {
                return ResponseEntity.ok(bookingService.sortAscBooking());
            }
            else if (sortType.equalsIgnoreCase("descending") || sortType.equalsIgnoreCase("desc"))
            {
                return ResponseEntity.ok(bookingService.sortDescBooking());
            }
            else
            {
                // Geçersiz sortType değeri için hata mesajı ile 400 Bad Request durumu döndürme
                String errorMessage = "Hata: Geçersiz sortType değeri. sortType, 'ascending', " +
                                      "'asc' " +
                                      "veya 'descending' 'desc' olmalıdır.";
                System.err.println(errorMessage);
                return ResponseEntity.badRequest().build();
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
            
        }
    }
    
    // Randevuları tarihine göre artan sırayla sıralayarak döndürür.
    @GetMapping(path = "/admin/sortascbooking", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Başarılı olursa 200 olmazssa 500", summary ="Sıralama")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Başarılı olursa"),@ApiResponse(responseCode = "500", description = "Başarılı olmazssa")})

    public ResponseEntity<List<Booking>> sortAscBooking()
    {
        
        // localhost:8080/booking/sortascbooking
        try
        {
            return ResponseEntity.ok(bookingService.sortAscBooking());
            
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
            
        }
    }
    
    // Randevuları tarihine göre azalan sırayla sıralayarak döndürür.
    @GetMapping(path = "/admin/sortdescbooking", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Başarılı olursa 200 olmazssa 500", summary ="Sıralama")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Başarılı olursa"),@ApiResponse(responseCode = "500", description = "Başarılı olmazssa")})

    public ResponseEntity<List<Booking>> sortDescBooking()
    {
        
        // localhost:8080/booking/sortdescbooking
        try
        {
            return ResponseEntity.ok(bookingService.sortDescBooking());
            
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
            
        }
    }
    
    // Belirli bir kullanıcının adına sahip randevuları döndürür
    @GetMapping(path = "/admin/searchbyusername", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(parameters = @Parameter(name = "username", description = "istenen username"), description = "Bulunursa 200 bulunamazsa 400", summary = "ID ver")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Bulunursa"), @ApiResponse(responseCode = "400", description = "Bulunamazsa ise")})

    public ResponseEntity<List<Booking>> searchBookingsByUserName(@RequestHeader(name = "username") String username)
    {
        
        // localhost:8080/booking/searchbyusername
        try
        {
            List<Booking> bookings = bookingService.searchBookingsByUserName(username.toLowerCase());
            if (bookings.isEmpty())
            {
                String errorMessage = "Hata: " + username + " Kullanıcı adına sahip randevu " + "bulunamadı" + ". ";
                System.out.println(errorMessage);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(bookings);
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Belirli bir randevunun durumunu "işleme alındı" olarak günceller.
    @PutMapping(path = "/admin/bookingprocessheader")
    @Operation(parameters = @Parameter(name = "id", description = "istenen id"), description = "Güncelllenirse 201 güncellenmesse 400", summary = "ID ver")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "201", description = "Güncelllenirse"), @ApiResponse(responseCode = "400", description = "Güncellenmez ise")})

    public ResponseEntity<String> updateStatusToProcessingHeader(Locale locale,@RequestHeader(name = "id") long id)
    {
        Object[] params = new Object[1];
        params[0] = id;
        // localhost:8080/booking/bookingprocessheader
        try
        {
            boolean result = bookingService.updateStatusToProcessingHeader(id);
            if (result)
            {
                return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage("booking.updateStatus.header.success",params,locale));
            }
            else
            {
                return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.updateStatus.header.error", null, locale));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.updateStatus.header.error", null, locale));
        }
    }
    
    @PutMapping(path = "/admin/bookingprocess/{id}")
    @Operation(parameters = @Parameter(name = "id", description = "istenen id"), description = "Güncelllenirse 201 güncellenmesse 400", summary = "ID ver")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "201", description = "Güncelllenirse"), @ApiResponse(responseCode = "400", description = "Güncellenmez ise")})

    public ResponseEntity<String> updateStatusToProcessing(Locale locale,@PathVariable(name = "id") long id)
    {
        Object[] params = new Object[1];
        params[0] = id;
        // localhost:8080/booking/bookingprocessheader
        try
        {
            boolean result = bookingService.updateStatusToProcessingHeader(id);
            if (result)
            {
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .body(messageSource.getMessage("booking.updateStatus.process.success",params,locale));
            }
            else
            {
                return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.updateStatus.process.error",null,locale));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.updateStatus.process.error",null,locale));
        }
    }
    
    // Belirli bir randevunun durumunu "tamamlandı" olarak günceller.
    @PutMapping(path = "/admin/bookingcomplete")
    @Operation(parameters = @Parameter(name = "id", description = "istenen id"), description = "Güncelllenirse 201 güncellenmesse 400", summary = "ID ver")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "201", description = "Güncelllenirse"), @ApiResponse(responseCode = "400", description = "Güncellenmez ise")})

    public ResponseEntity<String> updateStatusToCompletedHeader(Locale locale,@RequestHeader(name = "id") long id)
    {
        Object[] params = new Object[1];
        params[0] = id;
        // localhost:8080/booking/bookingcomplete
        try
        {
            boolean result = bookingService.updateStatusToCompletedHeader(id);
            if (result)
            {
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .body(messageSource.getMessage("booking.updateStatus.completedHeader.success" , params, locale));
            }
            else
            {
                return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.updateStatus.completedHeader.error",null,locale));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.updateStatus.completedHeader.error",null,locale));
        }
    }
    
    @PutMapping(path = "/admin/bookingcomplete/{id}")
    @Operation(parameters = @Parameter(name = "id", description = "istenen id"), description = "Güncelllenirse 201 güncellenmesse 400", summary = "ID ver")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "201", description = "Güncelllenirse"), @ApiResponse(responseCode = "400", description = "Güncellenmez ise")})

    public ResponseEntity<String> updateStatusToCompleted(Locale locale,@PathVariable(name = "id") long id)
    {
        Object[] params = new Object[1];
        params[0] = id;
        // localhost:8080/booking/bookingcomplete
        try
        {
            boolean result = bookingService.updateStatusToCompletedHeader(id);
            if (result)
            {
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .body(messageSource.getMessage("booking.updateStatus.completed.success" , params, locale));
            }
            else
            {
                return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.updateStatus.completed.error" , null, locale));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.updateStatus.completed.error" , null, locale));
        }
    }
    
    
}
