package br.unisales.projetos.demo.controllers.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisales.projetos.demo.annotations.ValidaAcesso;
import br.unisales.projetos.demo.controllers.DefaultController;
import br.unisales.projetos.demo.models.security.TIPOUSUARIO;
import br.unisales.projetos.demo.models.security.User;
import br.unisales.projetos.demo.repositories.security.UserRepository;
import br.unisales.projetos.demo.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/users")
@ValidaAcesso("ROLE_ADMIN")
public class UserController extends DefaultController {

 @Autowired
 UserService userService;

 @Autowired
 PasswordEncoder passwordEncoder;

 @Autowired
 UserRepository repository;

 @SuppressWarnings({ "rawtypes", "unchecked" })
@GetMapping("")
 public ResponseEntity<List<User>> todosPorLogin() {
  return new ResponseEntity(userService.repository().findAllByOrderByLoginAsc(), HttpStatus.OK);
 }

 @GetMapping("/tipo")
 public List<User> getUsuarios(TIPOUSUARIO tipousuario) {
  return repository.findAllByTipoOrderByLogin(tipousuario);
 }

 @SuppressWarnings({ "rawtypes", "unchecked", "null" })
@GetMapping("/{id}")
 public ResponseEntity<?> getMethodName(@PathVariable(value = "id", required = true) String id) {
  User user = userService.repository().findById(id).get();
  if (user != null)
   return new ResponseEntity(user, HttpStatus.OK);
  else
   return new ResponseEntity(null, HttpStatus.NOT_FOUND);
 }

 @SuppressWarnings({ "unchecked", "rawtypes", "null" })
@PostMapping("")
 public ResponseEntity<?> salva(@RequestBody User user) {
  user.setSenha(passwordEncoder.encode(user.getSenha()));

  // Adiciona a role ROLE_USER ao campo permissoes

  if (user.getPermissoes() == null) {
    user.setPermissoes(new ArrayList<>());
}
if (!user.getPermissoes().contains("ROLE_USER")) {
    user.getPermissoes().add("ROLE_USER");
}

  if (user.getId() != null) {
   user = userService.repository().save(user);
   return new ResponseEntity(user, HttpStatus.resolve(204));
  } else {
   user = userService.insert(user);
   return new ResponseEntity(user, HttpStatus.CREATED);
  }
 }

 @SuppressWarnings({ "rawtypes", "unchecked" })
@PutMapping("/{id}")
 public ResponseEntity<?> atualiza(@PathVariable String id, @RequestBody User user) {
  Optional<User> ouser = userService.repository().findById(id);
  if (ouser.isPresent()) {
   User _user = ouser.get();
   _user.setPermissoes(user.getPermissoes());
   userService.repository().save(_user);
   _user.setNome(user.getNome());
   _user.setSobrenome(user.getSobrenome());
   _user.setPeriodo(user.getPeriodo());
   return new ResponseEntity(_user, HttpStatus.OK);
  } else {
   return new ResponseEntity("Usuário não existe com o id " + id, HttpStatus.NOT_FOUND);
  }
 }

 @SuppressWarnings({ "rawtypes", "unchecked" })
@DeleteMapping("/{id}")
 public ResponseEntity<?> exclui(@PathVariable String id) {
  Optional<User> ouser = userService.repository().findById(id);
  if (ouser.isPresent()) {
   User user = ouser.get();
   userService.repository().delete(user);
   return new ResponseEntity(user, HttpStatus.OK);
  } else {
   return new ResponseEntity("Usuário não existe com o id " + id, HttpStatus.NOT_FOUND);
  }
 }

}
