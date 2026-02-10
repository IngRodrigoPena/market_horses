package com.nocountry.markethorses.audit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.nocountry.markethorses.domain.Role;
import com.nocountry.markethorses.domain.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final List<User> users = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception{
        //seed de datos
        User seller = new User();
        seller.setId(1L);
        seller.setName("Seller Demo");
        seller.setRole(Role.SELLER);

        User buyer = new User();
        buyer.setId(2L);
        buyer.setName("Buyer Demo");
        buyer.setRole(Role.BUYER);

        User verifier = new User();
        verifier.setId(3L);
        verifier.setName("Verifier Demo");
        verifier.setRole(Role.VERIFIER);

        User admin = new User();
        admin.setId(4L);
        admin.setName("Admin Demo");
        admin.setRole(Role.ADMIN);

        users.add(seller);
        users.add(buyer);
        users.add(verifier);
        users.add(admin);

        System.out.println("=== Usuarios iniciales cargados ===");
        users.forEach(user->
             System.out.println(user.getId() + " - " + user.getName() + " (" + user.getRole() + ")")
        );
    }
}
