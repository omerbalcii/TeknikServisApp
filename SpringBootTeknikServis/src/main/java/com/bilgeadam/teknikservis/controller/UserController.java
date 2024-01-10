package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.SystemUser;
import com.bilgeadam.teknikservis.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/user")
@io.swagger.v3.oas.annotations.tags.Tag(description = "User Endpointleri", name = "User")
public class UserController {
    private UserRepository userRepository;
    private final MessageSource messageSource;

    public UserController(UserRepository userRepository , MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    /*@Operation()
    @PostMapping("/login")
    public void fakeLogin(@RequestParam String username, @RequestParam String password) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }*/

    @PostMapping(path = "save" , consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Başarılı olursa 200 olmazssa 505", summary = "Body ile post et")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Başarılı olursa"), @ApiResponse(responseCode = "500", description = "Başarılı olmazssa")  })

    public ResponseEntity<String> save (Locale locale, @RequestBody SystemUser systemUser){
        try {
            boolean result = userRepository.save(systemUser);

            if(result){
                return ResponseEntity.ok(messageSource.getMessage("user.save.success" , null, locale));
            }
            else {
                return ResponseEntity.internalServerError().body(messageSource.getMessage("user.save.error" , null, locale));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(messageSource.getMessage("user.save.error" , null, locale));
        }

    }
    @GetMapping(path ="getall" , produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Başarılı olursa 200", summary ="Sistemdeki kullanıcıları gösterir")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Başarılı olursa")})

    public ResponseEntity<List<SystemUser>> getall(){
        return ResponseEntity.ok(userRepository.getall());
    }

}

