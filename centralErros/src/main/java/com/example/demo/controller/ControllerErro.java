package com.example.demo.controller;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.ErroRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Erro;


//@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ControllerErro {
	
	@Autowired
	private ErroRepository erroRepository;
	

	@GetMapping("/erro/get")
	public Iterable<Erro> getAllErros() {
		return erroRepository.findAll();
	}
	
	@PostMapping("/erro/post")
	public Erro registraErro(@Valid @RequestBody Erro erro) {	
		System.out.println("Novo erro: " + erro.getTitulo()); 
		return erroRepository.save(erro);

	}
	
	@PutMapping("/erro/put/{id}")
    public ResponseEntity<Erro> atualizaErro(@PathVariable(value = "id") Long erroId,
         @Valid @RequestBody Erro erroDetalhes) throws ResourceNotFoundException {
		Erro erro = erroRepository.findById(erroId.intValue()).orElseThrow(() -> new ResourceNotFoundException("Erro não encontrado para o id " + erroId));

        erro.setTitulo(erroDetalhes.getTitulo());
        erro.setDetalhes(erroDetalhes.getDetalhes());
        erro.setOrigem(erroDetalhes.getOrigem());
        erro.setNivel(erroDetalhes.getNivel());
        erro.setIdUsuario(erroDetalhes.getIdUsuario());
        final Erro erroAtualizado = erroRepository.save(erro);
        return ResponseEntity.ok(erroAtualizado);
    }

    @DeleteMapping("/erro/delete/{id}")
    public Map<String, Boolean> deletaErro(@PathVariable(value = "id") Long erroId)
         throws ResourceNotFoundException {
        Erro erro = erroRepository.findById(erroId.intValue()).orElseThrow(() -> new ResourceNotFoundException("Erro não encontrado para o id " + erroId));

        erroRepository.delete(erro);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

