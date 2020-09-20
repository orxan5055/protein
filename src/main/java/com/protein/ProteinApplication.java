package com.protein;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.protein.domain.User;
import com.protein.domain.tehlukesizlik.Role;
import com.protein.domain.tehlukesizlik.UserRole;
import com.protein.servis.UserService;
import com.protein.utilitiler.SecurityUtility;

@SpringBootApplication
public class ProteinApplication implements CommandLineRunner{

	@Autowired
	private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(ProteinApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1=new User();
		user1.setFirstName("Orkhan");
		user1.setLastName("Abiyev");
		user1.setUsername("orxan5055");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user1.setEmail("orkhan.abiyev@unilodz.eu");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1,role1));
		userService.createUser(user1,userRoles);
 		
	}

}
