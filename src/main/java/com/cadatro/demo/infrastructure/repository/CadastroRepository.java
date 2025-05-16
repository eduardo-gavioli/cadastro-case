package com.cadatro.demo.infrastructure.repository;

import com.cadatro.demo.domain.entity.CadastroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CadastroRepository  extends JpaRepository<CadastroModel, UUID> {
}
