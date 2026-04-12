package com.ads.sgcm_api.domain.service;

import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.model.UserRole;
import com.ads.sgcm_api.domain.model.UserStatus;
import com.ads.sgcm_api.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User salvar(User user){
        user.setRole(UserRole.VOLUNTARIO);
        user.setStatus(UserStatus.PENDENTE);

        String senhaCriptografada = passwordEncoder.encode(user.getSenha());
        user.setSenha(senhaCriptografada);

        return userRepository.save(user);
    }

    public User autenticar(String email, String senhaPura){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos"));

        // Bloqueia login se não estiver aprovado
        if (user.getStatus() == UserStatus.PENDENTE) {
            throw new RuntimeException("Sua conta ainda aguarda aprovação de um administrador.");
        }
        if (user.getStatus() == UserStatus.REJEITADO) {
            throw new RuntimeException("Seu pedido de acesso foi rejeitado.");
        }

        if (!passwordEncoder.matches(senhaPura, user.getSenha())){
            throw new RuntimeException("E-mail ou senha inválidos");
        }
        return user;
    }

    // MÉTODOS DO ADMINISTRADOR
    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    @Transactional
    public void alterarStatus(Long id, UserStatus novoStatus, User adminLogado) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (user.getId().equals(adminLogado.getId())) {
            throw new RuntimeException("Segurança: Você não pode alterar o próprio status.");
        }

        user.setStatus(novoStatus);
        userRepository.save(user);
    }

    @Transactional
    public void alterarRole(Long id, UserRole novaRole, User adminLogado) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (user.getId().equals(adminLogado.getId())) {
            throw new RuntimeException("Segurança: Você não pode alterar sua própria hierarquia.");
        }

        user.setRole(novaRole);
        userRepository.save(user);
    }
}