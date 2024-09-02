package br.unisales.projetos.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.unisales.projetos.demo.models.security.User;
import br.unisales.projetos.demo.services.UserService;
import br.unisales.projetos.demo.util.JWTUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController extends DefaultController {

 @Autowired
 JWTUtil jwtUtil;

 @Autowired
 UserService userService;

 @Autowired
 PasswordEncoder passwordEncoder;

 @PostMapping("/login")
 public ResponseEntity<String> login(@RequestParam String login, @RequestParam String senha) throws JsonProcessingException {
  User user = userService.repository().findByLogin(login);

  if (user == null) {
   request.setAttribute("jakarta.servlet.error.status_code", 400);
   throw new RuntimeException("Usuário não encontrado");
  }

  if (passwordEncoder.matches(senha, user.getSenha())) {
   List<GrantedAuthority> listaPermissoes = new ArrayList<>();
   user.getPermissoes().forEach(p -> {
    listaPermissoes.add(new SimpleGrantedAuthority(p));
   });

   Map<String, Object> claims = new HashMap<>();
   // adiciona o LOGIN no token
   claims.put("LOGIN", login);
   // adiciona o campo de permissões no token
   claims.put("PERMISSOES", listaPermissoes);
   String jwttoken = jwtUtil.geraTokenUsuario(login, claims);

   Map<String, Object> retorno = new HashMap<>();
   retorno.put("login", login);
   retorno.put("permissoes", listaPermissoes);
   retorno.put("token", jwttoken);

   ObjectMapper objectMapper = new ObjectMapper();
   String retornoDados = objectMapper.writeValueAsString(retorno);

   return ResponseEntity.ok().body(retornoDados);

  } else {
   request.setAttribute("jakarta.servlet.error.status_code", 400);
   throw new RuntimeException("Senha incorreta");
  }

 }

}
