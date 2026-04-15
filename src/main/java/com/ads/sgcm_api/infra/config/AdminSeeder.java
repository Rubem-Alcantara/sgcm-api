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

    @Value("${api.security.admin.email}")
    private String adminEmail;

    @Value("${api.security.admin.senha}")
    private String adminSenha;

    @Override
    public void run(String... args) throws Exception {
        // Busca o admin ou cria um novo se não existir
        User admin = userRepository.findByEmail(adminEmail).orElse(new User());

        admin.setNome("Administrador do Sistema");
        admin.setEmail(adminEmail);

        // Só gera o hash da senha se a conta for totalmente nova
        if (admin.getId() == null) {
            admin.setSenha(passwordEncoder.encode(adminSenha));
        }
        admin.setRole(UserRole.ADMIN);
        admin.setStatus(UserStatus.ATIVO);
        admin.setSenhaProvisoria(false);

        userRepository.save(admin);

        System.out.println("✅ Administrador Mestre verificado e restaurado com sucesso! Email: " + adminEmail);
    }
}