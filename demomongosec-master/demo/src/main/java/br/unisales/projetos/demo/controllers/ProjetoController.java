package br.unisales.projetos.demo.controllers;

import java.util.List;
import java.util.Optional;

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

import br.unisales.projetos.demo.annotations.ValidaAcesso;
import br.unisales.projetos.demo.models.domain.Projeto;
import br.unisales.projetos.demo.repositories.ProjetoRepository;

@CrossOrigin
@RestController
@RequestMapping("/projetos")
@ValidaAcesso("ROLE_PROJETOS")
public class ProjetoController extends DefaultController{
 @Autowired
 private ProjetoRepository projetoRepository;

 @SuppressWarnings("null")
@PostMapping
 @ValidaAcesso({ "ROLE_USER" })
 public ResponseEntity<?> insere(@RequestBody Projeto tarefa) {
  if (tarefa != null) {
   tarefa = projetoRepository.save(tarefa);
   return new ResponseEntity<>(tarefa, HttpStatus.OK);
  }
  return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
 }

 @GetMapping
 public ResponseEntity<?> todas() {
  List<Projeto> lt = projetoRepository.findAll();
  return new ResponseEntity<>(lt, HttpStatus.OK);
 }

 @GetMapping("/{id}")
    public ResponseEntity<?> getProjetoById(@PathVariable("id") String id) {
        Optional<Projeto> projeto = projetoRepository.findById(id);
        if (projeto.isPresent()) {
            return new ResponseEntity<>(projeto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

 @SuppressWarnings({ "rawtypes", "unchecked" })
@PutMapping("/{id}")
public ResponseEntity<?> atualiza(@PathVariable String id, @RequestBody Projeto projeto) {
    Optional<Projeto> PVar = projetoRepository.findById(id);
    if (PVar.isPresent()) {
        Projeto _projeto = PVar.get();
        
        // Atualizando apenas os campos descricao e professorRepresentante
        _projeto.setDescricao(projeto.getDescricao());
        _projeto.setProfessorRepresentante(projeto.getProfessorRepresentante());
        
        projetoRepository.save(_projeto);
        return new ResponseEntity(_projeto, HttpStatus.OK);
    } else {
        return new ResponseEntity("Projeto não existe com o id " + id, HttpStatus.NOT_FOUND);
    }
}


 @SuppressWarnings({ "rawtypes", "unchecked" })
@DeleteMapping("/{id}")
 public ResponseEntity<?> exclui(@PathVariable String id) {
  Optional<Projeto> ouser = projetoRepository.findById(id);
  if (ouser.isPresent()) {
   Projeto projeto = ouser.get();
   projetoRepository.delete(projeto);
   return new ResponseEntity(projeto, HttpStatus.OK);
  } else {
   return new ResponseEntity("Projeto não existe com o id " + id, HttpStatus.NOT_FOUND);
  }
 }


}
