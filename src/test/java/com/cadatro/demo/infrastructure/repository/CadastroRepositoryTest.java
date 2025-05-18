package com.cadatro.demo.infrastructure.repository;

import com.cadatro.demo.domain.dto.CadastroRecordDto;
import com.cadatro.demo.domain.entity.CadastroModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class CadastroRepositoryTest {

    @Autowired
    CadastroRepository cadastroRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Retorna lista de cadastros por idade entre 25 a 35 anos.")
    void findRegisterByIdadeRangeSucess() {
        List<CadastroRecordDto> recordDto = List.of(
                new CadastroRecordDto("João", "Silva", 30, "Brasil"),
                new CadastroRecordDto("Maria", "Ferreira", 25, "Portugal"),
                new CadastroRecordDto("Carlos", "Santos", 40, "Espanha"),
                new CadastroRecordDto("Ana", "Oliveira", 35, "Itália"),
                new CadastroRecordDto("Pedro", "Costa", 28, "França")
        );
        this.createRegister(recordDto);

        List<Object[]> result = this.cadastroRepository.findRegisterByIdadeRange(25,35);
        assertThat(!result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Não localiza nenhum registro de cadastro com idade entre 999 a 1015 anos.")
    void findRegisterByIdadeRangeFail() {
        List<CadastroRecordDto> recordDto = List.of(
                new CadastroRecordDto("João", "Silva", 30, "Brasil"),
                new CadastroRecordDto("Maria", "Ferreira", 25, "Portugal"),
                new CadastroRecordDto("Carlos", "Santos", 40, "Espanha"),
                new CadastroRecordDto("Ana", "Oliveira", 35, "Itália"),
                new CadastroRecordDto("Pedro", "Costa", 28, "França")
        );
        this.createRegister(recordDto);

        List<Object[]> result = this.cadastroRepository.findRegisterByIdadeRange(999,1015);
        assertThat(result.isEmpty()).isTrue();
    }

    private void createRegister(List<CadastroRecordDto> recordDto){

        for (CadastroRecordDto row : recordDto)
        {
            CadastroModel cadastroModel = new CadastroModel();
            BeanUtils.copyProperties(row,cadastroModel);
            this.entityManager.persist(cadastroModel);
        }
    }
}