package com.aztec.student.controller;

import com.aztec.student.config.Iconstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtToken {

        public JwtToken(String Authorization) {
            System.out.println("status checking.................");
            boolean status = false;
            String token = Authorization.substring(7);
            System.out.println(token);
            try {
                final Claims claims = Jwts.parser().setSigningKey(Iconstants.SECRET_KEY).parseClaimsJws(token).getBody();
                status = true;

                System.out.println(claims);
            } catch (final SignatureException e) {
            }
            System.out.println(status);

        }}