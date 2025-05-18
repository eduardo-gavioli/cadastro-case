package com.cadatro.demo.infrastructure.repository;

import com.cadatro.demo.domain.entity.CadastroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CadastroRepository  extends JpaRepository<CadastroModel, UUID> {
    @Query(value="Select id, nome, sobrenome, idade, pais from cadastro where idade >= :start and idade <= :end")
    List<Object[]> findRegisterByIdadeRange(int start, int end);
}
