package com.cadatro.demo.application.service;

import com.cadatro.demo.domain.dto.CadastroRecordDto;
import com.cadatro.demo.domain.entity.CadastroModel;
import com.cadatro.demo.infrastructure.repository.CadastroRepository;
import jakarta.persistence.EntityManager;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


class CadastroServiceTest {

    @Mock
    EntityManager entityManager;

    private CadastroModel cadastroModel;
    private UUID id;


    private CadastroRecordDto cadastroRecordDto;

    @Mock
    CadastroRepository cadastroRepository;

    @Autowired
    @InjectMocks
    CadastroService cadastroService;

    @BeforeEach
    void setup(){
        id = UUID.randomUUID();
        cadastroModel = new CadastroModel();
        cadastroModel.setId(id);

        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Testa a criação de um registro com sucesso")
    void saveRegisterSuccess() {
        when(cadastroRepository.save(any())).thenReturn(cadastroModel);

        CadastroModel result = cadastroService.saveRegister(cadastroModel);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(cadastroRepository).save(cadastroModel);
    }

    @Test
    @DisplayName("Testa a busca de um registro com sucesso")
    void searchRegisterSuccess() {
        when(cadastroRepository.findById(id)).thenReturn(Optional.of(cadastroModel));

        Optional<CadastroModel> result = cadastroService.searchRegister(id);

        assertTrue(result.isPresent());
        assertEquals(cadastroModel, result.get());
        verify(cadastroRepository).findById(id);
    }

    @Test
    @DisplayName("Testa falha na busca de um registro")
    void searchRegisterFail() {
        when(cadastroRepository.findById(id)).thenReturn(Optional.empty());

        Optional<CadastroModel> result = cadastroService.searchRegister(id);

        assertFalse(result.isPresent());
        verify(cadastroRepository).findById(id);
    }

    @Test
    @DisplayName("Testa listagem de registros com sucesso")
    void listRegistersSuccess() {
        List<CadastroModel> expectedList = List.of(cadastroModel, new CadastroModel());
        when(cadastroRepository.findAll()).thenReturn(expectedList);

        List<CadastroModel> result = cadastroService.listRegisters();

        assertEquals(expectedList.size(), result.size());
        verify(cadastroRepository).findAll();
    }

    @Test
    @DisplayName("Testa falha na listagem de registros")
    void listRegistersFail() {
        when(cadastroRepository.findAll()).thenReturn(List.of());

        List<CadastroModel> result = cadastroService.listRegisters();

        assertTrue(result.isEmpty());
        verify(cadastroRepository).findAll();
    }

    @Test
    @DisplayName("Testa atualização de registro com sucesso")
    void updateRegisterSuccess() {
        CadastroRecordDto cadastroRecordDto = new CadastroRecordDto("João", "Silva", 30, "Brasil");
        when(cadastroRepository.findById(id)).thenReturn(Optional.of(cadastroModel));
        when(cadastroRepository.save(any())).thenReturn(cadastroModel);

        Optional<CadastroModel> result = cadastroService.updateRegister(id, cadastroRecordDto);

        assertTrue(result.isPresent());
        verify(cadastroRepository).save(cadastroModel);
    }

    @Test
    @DisplayName("Testa falha na atualização de registro")
    void updateRegisterFail() {
        CadastroRecordDto cadastroRecordDto = new CadastroRecordDto("João", "Silva", 30, "Brasil");
        when(cadastroRepository.findById(id)).thenReturn(Optional.empty());

        Optional<CadastroModel> result = cadastroService.updateRegister(id, cadastroRecordDto);

        assertFalse(result.isPresent());
        verify(cadastroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Testa remoção de registro com sucesso")
    void deleteRegisterSuccess() {
        doNothing().when(cadastroRepository).deleteById(id);

        cadastroService.deleteRegister(id);

        verify(cadastroRepository).deleteById(id);
    }

    @Test
    @DisplayName("Testa falha na remoção de registro")
    void deleteRegisterFail() {
        doThrow(new RuntimeException("Erro ao deletar")).when(cadastroRepository).deleteById(id);

        Exception exception = assertThrows(RuntimeException.class, () -> cadastroService.deleteRegister(id));

        assertEquals("Erro ao deletar", exception.getMessage());
        verify(cadastroRepository).deleteById(id);
    }

}