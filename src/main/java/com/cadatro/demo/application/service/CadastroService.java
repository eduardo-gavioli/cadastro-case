package com.cadatro.demo.application.service;

import com.cadatro.demo.domain.dto.CadastroDTO;
import com.cadatro.demo.domain.entity.Cadastro;
import com.cadatro.demo.infrastructure.repository.CadastroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CadastroService {

    @Autowired
    private  CadastroRepository cadastroRepository;
//    public Cadastro salvarCadastro(CadastroDTO cadastroDTO) {
//        Cadastro cadastro = new Cadastro();
//        cadastro.setNome(cadastroDTO.getNome());
//        cadastro.setSobrenome(cadastroDTO.getSobrenome());
//        cadastro.setIdade(cadastroDTO.getIdade());
//        cadastro.setPais(cadastroDTO.getPais());
//
////        return cadastroRepository.save(cadastro);
//        return  null;
//    }

    public Cadastro buscarCadastro(Long id) {
        return cadastroRepository.findById(id).orElse(null);
    }

    public List<Cadastro> listarCadastros() {
        return cadastroRepository.findAll();
    }

//    public Cadastro atualizarCadastro(UUID id, CadastroDTO novosDados) {
//        Optional<Cadastro> cadastroExistente = cadastroRepository.findById(id);
//        if (cadastroExistente.isPresent()) {
//            Cadastro cadastro = cadastroExistente.get();
//            cadastro.setNome(novosDados.getNome());
//            return cadastroRepository.save(cadastro);
//        }
//        return null;
//    }

    public void excluirCadastro(Long id) {
        cadastroRepository.deleteById(id);
    }
}
