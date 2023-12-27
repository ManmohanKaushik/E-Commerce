package com.bikkadit.electronicstore;

import com.bikkadit.electronicstore.entity.Role;
import com.bikkadit.electronicstore.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class ElectronicstoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicstoreApplication.class, args);

    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository repository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(passwordEncoder.encode("Nare@1990"));
        try {
            String role_admin_id="mynameismanmohankaushik";
            String role_normal_id="mynameismanmohansharma";
            Role role_admin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
            Role role_normal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();
            repository.save(role_admin);
            repository.save(role_normal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
