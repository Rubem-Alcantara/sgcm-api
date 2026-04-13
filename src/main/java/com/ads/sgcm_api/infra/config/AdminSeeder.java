package com.ads.sgcm_api.infra.config;

import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.model.UserRole;
import com.ads.sgcm_api.domain.model.UserStatus;
import com.ads.sgcm_api.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Puxando os valores do application.properties
    @Value("${api.security.admin.email}")
    private String adminEmail;

    @Value("${api.security.admin.senha}")
    private String adminSenha;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe o admin mestre usando o e-mail dinâmico
        if (userRepository.findByEmail(adminEmail).isEmpty()) {

            User admin = new User();
            admin.setNome("Administrador do Sistema");
            admin.setEmail(adminEmail); // Usa a variável
            admin.setSenha(passwordEncoder.encode(adminSenha)); // Usa a variável criptografada
            admin.setRole(UserRole.ADMIN);
            admin.setStatus(UserStatus.ATIVO);

            userRepository.save(admin);
            System.out.println("✅ Administrador Mestre criado com sucesso! Email: " + adminEmail);
        }
    }
}