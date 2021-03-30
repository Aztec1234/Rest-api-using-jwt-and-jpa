package com.aztec.student.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.aztec.student.repository.UserRepository;
import com.aztec.student.config.Iconstants;
import com.aztec.student.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api")
public class Tokencontroller {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody User login) throws ServletException {

        String jwttoken = "";

        if (login.getUsername().isEmpty() || login.getPassword().isEmpty())
            return new ResponseEntity<String>("Username or password cannot be empty.", HttpStatus.BAD_REQUEST);

        String name = login.getUsername();
        String password = login.getPassword();

        List<User> users = userRepository.findAll();

        if (name.equals("ayush"))  {
            Map<String, Object> claims = new HashMap<String, Object>();
            claims.put("usr", login.getUsername());
            claims.put("sub", "Authentication token");
            claims.put("iss", Iconstants.ISSUER);
            claims.put("rol", "User");
            claims.put("iat", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            jwttoken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, Iconstants.SECRET_KEY).compact();
            System.out.println("Returning the following token to the user= " + jwttoken);
        } else {
            return new ResponseEntity<String>("Invalid credentials. Please check the username and password.", HttpStatus.UNAUTHORIZED);

        }

        return new ResponseEntity<String>(jwttoken, HttpStatus.OK);
    }

    public class UserValidation implements PasswordEncoder {


        @Override
        public String encode(CharSequence charSequence) {
            return null;
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {

            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            System.out.println(bc.matches(charSequence, s));
            return bc.matches(charSequence, s);
        }
    }


}

