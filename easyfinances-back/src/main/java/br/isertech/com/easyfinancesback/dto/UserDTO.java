package br.isertech.com.easyfinancesback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String name;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private List<String> roles;
    private LocalDateTime created;
    private LocalDateTime updated;

}
