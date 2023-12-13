package com.masprogtechs.apitownisabel.services;

import com.masprogtechs.apitownisabel.enums.Role;
import com.masprogtechs.apitownisabel.exception.EntityRuntimeException;
import com.masprogtechs.apitownisabel.exception.UsernameUniqueViolationException;
import com.masprogtechs.apitownisabel.models.User;
import com.masprogtechs.apitownisabel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new EntityRuntimeException(String.format("Usuário com o id %s não encontrado.", id)));
    }

    public User save(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Transactional(readOnly = true)
    public Role findRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }

}
