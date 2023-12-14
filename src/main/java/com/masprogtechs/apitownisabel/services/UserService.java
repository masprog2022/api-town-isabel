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

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return userRepository.findAll();
    }

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

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new EntityRuntimeException(String.format("Usuaário com identificador %s não encontrado", id));
        }

        userRepository.deleteById(id);

    }
    public User update(User user) {
        User userUpdate  = findById(user.getId());
        if (userUpdate == null) {
            throw new EntityRuntimeException(String.format("Usuário com identificador %s não encontrado", user.getId()));
        }

        user.setFullName(user.getFullName());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());

        return userRepository.save(user);
    }

}
