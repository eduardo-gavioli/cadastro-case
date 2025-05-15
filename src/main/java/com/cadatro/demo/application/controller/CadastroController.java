package com.cadatro.demo.application.controller;

import com.cadatro.demo.application.service.CadastroService;
import com.cadatro.demo.domain.dto.CadastroDTO;
import com.cadatro.demo.domain.entity.Cadastro;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cadastros")
@RequiredArgsConstructor
public class CadastroController {

    @Autowired
    private  CadastroService cadastroService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Cadastro>> listarCadastros() {
        return ResponseEntity.ok(cadastroService.listarCadastros());
    }
//    @PostMapping
//    public Cadastro criarCadastro(@RequestBody CadastroDTO cadastro) {
//        return cadastroService.salvarCadastro(cadastro);
//    }

    @GetMapping("/{id}")
    public Cadastro buscarCadastro(@PathVariable Long id) {
        return cadastroService.buscarCadastro(id);
    }


//    @PatchMapping("/{id}")
//    public Cadastro atualizarCadastro(@PathVariable Long id, @RequestBody Cadastro cadastro) {
//        return cadastroService.atualizarCadastro(id, cadastro);
//    }

    @DeleteMapping("/{id}")
    public void excluirCadastro(@PathVariable Long id) {
        cadastroService.excluirCadastro(id);
    }

}
