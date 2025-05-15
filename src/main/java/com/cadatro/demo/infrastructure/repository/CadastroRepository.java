package com.cadatro.demo.infrastructure.repository;

import com.cadatro.demo.domain.entity.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroRepository  extends JpaRepository<Cadastro,Long> {
}
