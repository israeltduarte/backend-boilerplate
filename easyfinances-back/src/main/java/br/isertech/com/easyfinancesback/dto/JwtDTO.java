package br.isertech.com.easyfinancesback.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtDTO {

    private String type = "Bearer";
    @NonNull
    private String token;

}

