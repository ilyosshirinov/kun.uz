package com.example.service;

import com.example.entity.TokenEntity;
import com.example.exp.AppBadException;
import com.example.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Boolean checkSmsToken(String token) {
        Optional<TokenEntity> entity = tokenRepository.findTopByTokenOrderByExpireDateDesc(token);
        if (entity.isEmpty()) {
            throw new AppBadException("Token topilmadi");
        }
        TokenEntity tokenEntity = entity.get();
        if (tokenEntity.getExpireDate().plusDays(30).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Token muddati tugagan, avval tokenni yangilang");
        }
        return true;
    }

    public void saveToken(String tokens) {
        TokenEntity token = new TokenEntity();
        token.setToken(tokens);
    }
}
