package com.scaler.userservicemwfeve.repositories;

import com.scaler.userservicemwfeve.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    public Optional<Token> findTokenByValue(String value);

    public void deleteById(Long id);

    public Optional<Token> findTokenByValueAndDeletedAndExpiryAtGreaterThanEqual(
                                                    String value,
                                                    Boolean deleted,
                                                    Date date);
}
