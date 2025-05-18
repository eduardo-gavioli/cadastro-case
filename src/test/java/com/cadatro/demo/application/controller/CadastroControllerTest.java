package com.cadatro.demo.application.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cadatro.demo.application.controller.CadastroController;
import com.cadatro.demo.application.service.CadastroService;
import com.cadatro.demo.domain.dto.CadastroRecordDto;
import com.cadatro.demo.domain.entity.CadastroModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class CadastroControllerTest {

    @Mock
    private CadastroService cadastroService;

    @InjectMocks
    private CadastroController cadastroController;

    private UUID id;
    private CadastroModel cadastroModel;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        cadastroModel = new CadastroModel();
        cadastroModel.setId(id);
    }

    @Test
    @DisplayName("Testa a criação de um registro com sucesso")
    void saveRegisterSuccess() {
        when(cadastroService.saveRegister(any())).thenReturn(cadastroModel);

        ResponseEntity<CadastroModel> response = cadastroController.saveRegister(new CadastroRecordDto("João", "Silva", 30, "Brasil"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cadastroModel, response.getBody());
        verify(cadastroService).saveRegister(any());
    }

    @Test
    @DisplayName("Testa a obtenção de todos os registros com sucesso")
    void getAllRegistersSuccess() {
        List<CadastroModel> expectedList = List.of(cadastroModel, new CadastroModel());
        when(cadastroService.listRegisters()).thenReturn(expectedList);

        ResponseEntity<List<CadastroModel>> response = cadastroController.getAllRegisters();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList.size(), response.getBody().size());
        verify(cadastroService).listRegisters();
    }

    @Test
    @DisplayName("Testa a obtenção de um único registro com sucesso")
    void getOneRegisterSuccess() {
        when(cadastroService.searchRegister(id)).thenReturn(Optional.of(cadastroModel));

        ResponseEntity<Object> response = cadastroController.getOneRegister(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cadastroModel, response.getBody());
        verify(cadastroService).searchRegister(id);
    }

    @Test
    @DisplayName("Testa falha ao buscar um único registro")
    void getOneRegisterFail() {
        when(cadastroService.searchRegister(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = cadastroController.getOneRegister(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Register not found.", response.getBody());
        verify(cadastroService).searchRegister(id);
    }

    @Test
    @DisplayName("Testa atualização de um registro com sucesso")
    void updateRegisterSuccess() {
        when(cadastroService.updateRegister(eq(id), any())).thenReturn(Optional.of(cadastroModel));

        ResponseEntity<Object> response = cadastroController.updateRegister(id, new CadastroRecordDto("João", "Silva", 30, "Brasil"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cadastroModel, response.getBody());
        verify(cadastroService).updateRegister(eq(id), any());
    }

    @Test
    @DisplayName("Testa falha na atualização de um registro")
    void updateRegisterFail() {
        when(cadastroService.updateRegister(eq(id), any())).thenReturn(Optional.empty());

        ResponseEntity<Object> response = cadastroController.updateRegister(id, new CadastroRecordDto("João", "Silva", 30, "Brasil"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Register not found.", response.getBody());
        verify(cadastroService).updateRegister(eq(id), any());
    }

    @Test
    @DisplayName("Testa remoção de um registro com sucesso")
    void deleteRegisterSuccess() {
        when(cadastroService.searchRegister(id)).thenReturn(Optional.of(cadastroModel));
        doNothing().when(cadastroService).deleteRegister(id);

        ResponseEntity<Object> response = cadastroController.deleteRegister(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Register " + id + " Deleted suscessfully", response.getBody());
        verify(cadastroService).searchRegister(id);
        verify(cadastroService).deleteRegister(id);
    }

    @Test
    @DisplayName("Testa falha na remoção de um registro")
    void deleteRegisterFail() {
        when(cadastroService.searchRegister(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = cadastroController.deleteRegister(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Register not found.", response.getBody());
        verify(cadastroService).searchRegister(id);
        verify(cadastroService, never()).deleteRegister(id);
    }
}