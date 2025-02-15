package com.app.bankofbanks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.bankofbanks.models.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    Usuarios findByEmail(String email);

    Usuarios findByCpf(String cpf);
    
}
