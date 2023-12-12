package com.masprogtechs.apitownisabel.services;

import com.masprogtechs.apitownisabel.exception.EntityRuntimeException;
import com.masprogtechs.apitownisabel.exception.UsernameUniqueViolationException;
import com.masprogtechs.apitownisabel.models.User;
import com.masprogtechs.apitownisabel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User save(User user){
        try{
          return userRepository.save(user);
        }catch (DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username %s já registado no sistema", user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityRuntimeException(String.format("Usuário com username %s não encontrado.", username)));
    }


}