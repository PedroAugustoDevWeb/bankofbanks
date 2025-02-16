package com.app.bankofbanks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.bankofbanks.dtos.UsuarioDTO;
import com.app.bankofbanks.services.UsuariosServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class HomeController {

        @Autowired
        private UsuariosServices usuariosServices;
    
        @GetMapping("/") 
        public String index(HttpServletRequest request) {
    
            if (usuariosServices.existLogin(request) == true) {
    
                usuariosServices.getUserFromCookie(request);
    
                return "redirect:/dashboard";
    
            } else {
    
                return "redirect:/login";
    
            }
        }
    
        @GetMapping("/cadastro")
        public String cadastro() {
    
            return "cadastro";
        }
    
        @PostMapping("/cadastro")
        public String methodPostCadastro(@ModelAttribute  UsuarioDTO  usuario) {
    
            try {
    
                usuariosServices.criarUser(usuario);
    
                return "redirect:/login";
    
            } catch (Exception e) {
    
                System.out.println(e.getMessage());
    
                return "redirect:/cadastro";
            }
    
            }
    
        @GetMapping("/login")
        public String login() {
            return "login";
        }
    
        @PostMapping("/login")
        public String methodPostLongin(@ModelAttribute  UsuarioDTO  usuario, HttpServletResponse request) {
    
            try {
    
                usuariosServices.login(usuario, request);
    
                return "redirect:/home";
    
            } catch (Exception e) {
    
                System.out.println(e.getMessage());
    
                return "login";
            }
    
        }
        
        @GetMapping("/dashboard")
        public String dashboard(HttpServletRequest request, Model model) {
    
            var user = usuariosServices.getUserFromCookie(request);

            model.addAttribute("user", user);
            
            return "dashboard";
    }
    
    
    
}
