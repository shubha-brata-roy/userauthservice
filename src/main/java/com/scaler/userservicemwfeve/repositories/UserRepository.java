package com.scaler.userservicemwfeve.repositories;

import com.scaler.userservicemwfeve.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User save(User user);

    public Optional<User> findUserByEmailAndHashPassword(
                            String email, String hashPassword);

    public Optional<User> findUserByEmail(String email);
}
