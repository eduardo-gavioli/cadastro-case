package com.cadatro.demo.application.service;

import com.cadatro.demo.application.controller.CadastroController;
import com.cadatro.demo.domain.dto.CadastroRecordDto;
import com.cadatro.demo.domain.entity.CadastroModel;
import com.cadatro.demo.infrastructure.repository.CadastroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
@RequiredArgsConstructor
public class CadastroService {

    @Autowired
    private  CadastroRepository cadastroRepository;
    public CadastroModel saveRegister(CadastroModel cadastroModelDTO) {
        var register=  cadastroRepository.save(cadastroModelDTO);
        UUID id = register.getId();
        register.add(linkTo(methodOn(CadastroController.class).getOneRegister(id)).withSelfRel());
        return register;
    }

    public Optional<CadastroModel> searchRegister(UUID id) {
        return cadastroRepository.findById(id);
    }

    public List<CadastroModel> listRegisters() {
        var registerList =  cadastroRepository.findAll();
        if (!registerList.isEmpty()) {
            for (CadastroModel register : registerList){
                UUID id = register.getId();
                register.add(linkTo(methodOn(CadastroController.class).getOneRegister(id)).withSelfRel());
            }
        }
        return  registerList;
    }

    public Optional<CadastroModel> updateRegister(UUID id, CadastroRecordDto cadastroRecordDto) {
        Optional<CadastroModel> register = cadastroRepository.findById(id);
        if(register.isEmpty())
        {
            return Optional.empty();
        }
        var cadastroModel = register.get();
        BeanUtils.copyProperties(cadastroRecordDto,cadastroModel);
        return Optional.of(cadastroRepository.save(cadastroModel));
    }

    public void deleteRegister(UUID id) {
        cadastroRepository.deleteById(id);
    }
}
