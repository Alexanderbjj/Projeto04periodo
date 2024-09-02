package br.unisales.projetos.demo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Anotação para usar como validador de autorizações
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD}) //usa em método ou em classe
public @interface ValidaAcesso {
 String[] value();
}
