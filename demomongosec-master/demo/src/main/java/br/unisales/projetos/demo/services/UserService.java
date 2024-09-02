package br.unisales.projetos.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unisales.projetos.demo.models.security.User;
import br.unisales.projetos.demo.repositories.security.UserRepository;

/**
 * Serviço criado para auxiliar no uso do atlas
 * confere se tem usuário com mesmo login para não duplicar na inserção
 * localmente funciona o index mas no atlas (FREE) não
 */
@Service
public class UserService {
 @Autowired
 UserRepository userRepository;

 /*
  * valida se o login já existe na inserção
  * se existir não cadastra
  */
 public User insert(User user) {
  User _comp = userRepository.findByLogin(user.getLogin());
  if (_comp != null) {
   throw new RuntimeException("Usuário com login " + user.getLogin() + " já existe");
  }
  return userRepository.save(user);
 }
 

 public UserRepository repository() {
  return this.userRepository;
 }
}
