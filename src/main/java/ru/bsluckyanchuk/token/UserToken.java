package ru.bsluckyanchuk.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserToken {
    private String token;


    public void setToken(String sessionToken) {
        this.token = sessionToken;
    }
}
