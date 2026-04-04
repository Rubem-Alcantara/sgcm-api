package com.ads.sgcm_api.domain.service;

import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User salvar(User user){
        //Criptografando a senha antes de salvar no banco
        String senhaCriptografada = passwordEncoder.encode(user.getSenha());
        user.setSenha(senhaCriptografada);

        return userRepository.save(user);
    }

    public User autenticar(String email, String senhaPura){
        //Buscando usuário pelo e-mail
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválida"));

        // Comparando a senha pura enviada com a hash para o db
        if (!passwordEncoder.matches(senhaPura, user.getSenha())){
            throw new RuntimeException("E-mail ou senha inválidos");
        }
        // Se der certo, retorna o usuario
        return user;
    }
}
