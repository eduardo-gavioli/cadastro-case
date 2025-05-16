package com.cadatro.demo.application.controller;

import com.cadatro.demo.application.service.CadastroService;
import com.cadatro.demo.domain.dto.CadastroRecordDto;
import com.cadatro.demo.domain.entity.CadastroModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cadastros")
public class CadastroController {

    @Autowired
    private  CadastroService cadastroService;

    @PostMapping()
    public ResponseEntity<CadastroModel> saveRegister(@RequestBody @Valid CadastroRecordDto cadastroRecordDto){
        var cadastroModel = new CadastroModel();
        BeanUtils.copyProperties(cadastroRecordDto, cadastroModel);
        return  ResponseEntity.status(HttpStatus.CREATED).body(cadastroService.saveRegister(cadastroModel));
    }

    @GetMapping()
    public ResponseEntity<List<CadastroModel>> getAllRegisters() {
        var listCad = cadastroService.listRegisters();

        return ResponseEntity.ok(listCad);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneRegister(@PathVariable(value = "id") UUID id){
        Optional<CadastroModel> register0 = cadastroService.searchRegister(id);
        if (register0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Register not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(register0.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRegister(@PathVariable(value = "id") UUID id, @RequestBody @Valid CadastroRecordDto cadastroRecordDto){
        Optional<CadastroModel> register0 = cadastroService.updateRegister(id,cadastroRecordDto);
        if (register0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Register not found.");
        }
        var productModel = register0.get();

        return  ResponseEntity.status(HttpStatus.OK).body(productModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRegister (@PathVariable(value = "id")UUID id)
    {
        Optional<CadastroModel> register0 = cadastroService.searchRegister(id);
        if (register0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Register not found.");
        }
        cadastroService.deleteRegister(id);
        return ResponseEntity.status(HttpStatus.OK).body("Register "+id+" Deleted suscessfully");
    }
}
