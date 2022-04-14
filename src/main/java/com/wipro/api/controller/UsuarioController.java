package com.wipro.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wipro.api.model.Usuario;
import com.wipro.api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@GetMapping("/{id}") 
	public ResponseEntity<Usuario> GetById(@PathVariable Integer id) { // variavel presente na uri 
		Usuario obj = this.service.findById(id); 
    	return ResponseEntity.ok().body(obj); 
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> GetAll() {
		List<Usuario> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@PostMapping 
	public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario) { 
		Usuario newObj = service.create(usuario); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri(); 
		//return ResponseEntity.status(HttpStatus.GONE).body(service.create(usuario)); 
		return ResponseEntity.created(uri).build(); 
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> Put(@PathVariable Integer id, @RequestBody Usuario obj) {
		Usuario newUsuario = service.update(id, obj);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(newUsuario);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> Delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
