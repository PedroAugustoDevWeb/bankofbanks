package com.app.bankofbanks.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bankofbanks.dtos.UsuarioDTO;
import com.app.bankofbanks.models.Usuarios;
import com.app.bankofbanks.repository.UsuarioRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UsuariosServices {

    @Autowired
    private UsuarioRepository repository;

    public void criarUser(UsuarioDTO usuario) {

        if (repository.findByEmail(usuario.email()) != null || repository.findByCpf(usuario.cpf()) != null) {
            throw new RuntimeException("Usuario ja cadastrado");
        } else {

            if (!usuario.password().equals(usuario.confirmarPassword()))  {
                throw new RuntimeException("Senhas não conferem");

            } else {

                Usuarios user = new Usuarios();

                user.setNome_completo(usuario.nomeCompleto());

                user.setPrimeiro_nome(usuario.nomeCompleto().split(" ")[0]);

                user.setCpf(usuario.cpf());

                user.setEmail(usuario.email());

                user.setSenha(usuario.password());

                user.setBalence(BigDecimal.ZERO);

                repository.save(user);

            }

        }

    }

    public void login(UsuarioDTO  usuario, HttpServletResponse request) {

            Usuarios user = repository.findByEmail(usuario.email());

            if (user == null) {
                throw new RuntimeException("Usuario não cadastrado");
            }

            if (!user.getSenha().equals(usuario.password())) {
                throw new RuntimeException("Senha invalida");
            }

            Cookie cookie = new Cookie("id", user.getId().toString());

            cookie.setMaxAge(60 * 60 * 24 * 30);

            cookie.setHttpOnly(true);

            cookie.setPath("/");

            request.addCookie(cookie);

        }

        public boolean existLogin(HttpServletRequest response) {

            Cookie[] cookies = response.getCookies();

            if (cookies == null) {

                System.out.println("Sem cookies");

                return false;
                
            } else {

                return true;
            }

        }

        public Usuarios getUserFromCookie(HttpServletRequest response) {

            Cookie[] cookies = response.getCookies();

            if (cookies == null) {

                return null;
            }

            for (Cookie cookie : cookies) {

                    Long id = Long.parseLong(cookie.getValue());

                    return repository.findById(id).orElse(null);
                }

                            
                return null;   
            }


}

    


