package br.unisales.projetos.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping({ "", "/" })
public class IndexController {

 @GetMapping("")
 public HashMap<String, Object> index() {
  HashMap<String, Object> ret = new HashMap<>();
  ret.put("mensagem", "início ok");
  return ret;
 }

}
