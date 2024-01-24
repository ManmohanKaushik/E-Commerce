package com.bikkadit.electronicstore;

import com.bikkadit.electronicstore.entity.Role;
import com.bikkadit.electronicstore.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableWebMvc
public class ElectronicstoreApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ElectronicstoreApplication.class, args);

    }

       @Override
    public void run(String... args) throws Exception {
        System.out.println(passwordEncoder.encode("Nare@1990"));
        try {
            String role_admin_id = "mynameismanmohankaushik";
            String role_normal_id = "mynameismanmohansharma";
            Role role_admin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
            Role role_normal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();
            repository.save(role_admin);
            repository.save(role_normal);


            Role role1=new Role();
            role1.setRoleId(role_normal_id);
            role1.setRoleName("ROLE_NORMAL");

            Role role2=new Role();
            role2.setRoleId(role_normal_id);
            role2.setRoleName("ROLE_ADMIN");

            List<Role> list = List.of(role1, role2);
            List<Role> roles = this.repository.saveAll(list);
            roles.forEach(r -> {
                System.out.println(r.getRoleName());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
