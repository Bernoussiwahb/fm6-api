package com.fm6.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;      // ✅ import manquant

import com.fm6.repository.UserRepository;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {   // extends correct

    private final JwtService jwt;
    private final UserRepository userRepo;

    public JwtAuthFilter(JwtService jwt, UserRepository userRepo) {
        this.jwt = jwt;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // ── 1. Récupère l’en-tête Authorization ───────────────────────────────────
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);              // pas de token → on poursuit
            return;
        }

        // ── 2. Extrait et vérifie le token ───────────────────────────────────────
        String token = header.substring(7);
        String username = jwt.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userOpt = userRepo.findByUsername(username);
            if (userOpt.isPresent() && jwt.isTokenValid(token, username)) {

                // ── 3. Crée l’objet Authentication et place-le dans le contexte ──
                var auth = new UsernamePasswordAuthenticationToken(
                               userOpt.get(), null, java.util.Collections.emptyList());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // ── 4. Poursuit la chaîne de filtres ─────────────────────────────────────
        chain.doFilter(request, response);
    }
}
