package br.unisales.projetos.demo.models.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("usuarios")
public class User {

 @Id
 private String id;
 @Indexed(unique = true)
 private String login;
 private String senha;
 private String nome;
 private String sobrenome;
 private String periodo;
 TIPOUSUARIO tipo;


 private List<String> permissoes = new ArrayList<>();

}