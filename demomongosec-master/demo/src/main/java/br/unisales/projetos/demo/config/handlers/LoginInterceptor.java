package br.unisales.projetos.demo.config.handlers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import br.unisales.projetos.demo.annotations.ValidaAcesso;
import br.unisales.projetos.demo.models.security.User;
import br.unisales.projetos.demo.services.UserService;
import br.unisales.projetos.demo.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Hebert
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

 private UserService userService;
 private JWTUtil jwtUtil;
 private User user;

 public LoginInterceptor(UserService userService, JWTUtil jwtUtil) {
  this.userService = userService;
  this.jwtUtil = jwtUtil;
 }

 public boolean validaLogin(HttpServletRequest request, HttpServletResponse response, Object handler)
   throws Exception {
  try {
   String jwttoken = jwtUtil.recuperaTokenRequisicao(request);
   String login = jwtUtil.getUsuarioNoToken(jwttoken);

   if (login != null && jwtUtil.validaToken(jwttoken, login)) {
    user = userService.repository().findByLogin(login);
    List<GrantedAuthority> listaPermissoes = new ArrayList<>();
    user.getPermissoes().forEach(p -> {
     listaPermissoes.add(new SimpleGrantedAuthority(p));
    });

    var token = new UsernamePasswordAuthenticationToken(user.getLogin(), null, listaPermissoes);
    SecurityContextHolder.getContext().setAuthentication(token);

    return validaAutorizacao(request, response, handler);

   } else {
    request.setAttribute("jakarta.servlet.error.status_code", 401);
    throw new RuntimeException("Falha no TOKEN");
   }
  } catch (Exception ex) {
   throw ex;
  }
 }

 public boolean validaAutorizacao(HttpServletRequest request, HttpServletResponse response, Object handler)
   throws Exception {
  HandlerMethod hm = (HandlerMethod) handler;
  Method method = hm.getMethod();
  boolean autorizadoMetodo = false, autorizadoClasse = false;

  //caso queira alterar a forma de validação usando o endpoint
  @SuppressWarnings("unused")
  String path = request.getRequestURI().substring(request.getContextPath().length());

  if (user == null) {
   request.setAttribute(null, method);
   request.setAttribute("jakarta.servlet.error.status_code", 401);
   throw new RuntimeException("Nao logado");
  }

  // se é um restcontroller
  if (method.getDeclaringClass().isAnnotationPresent(RestController.class)) {

   if (method.getDeclaringClass().isAnnotationPresent(ValidaAcesso.class)) {

    String[] acessos = method.getDeclaringClass().getAnnotation(ValidaAcesso.class).value();

    for (String verificacao : acessos) {
     if (user.getPermissoes().contains(verificacao)) {
      autorizadoClasse = true;
      break;
     }
    }
   } else {
    autorizadoClasse = true;
   }

   if (method.isAnnotationPresent(ValidaAcesso.class)) {

    String[] acessos = method.getAnnotation(ValidaAcesso.class).value();

    for (String verificacao : acessos) {
     if (user.getPermissoes().contains(verificacao)) {
      autorizadoMetodo = true;
      break;
     }
    }
   } else {
    autorizadoMetodo = true;
   }

   if (!autorizadoClasse || (autorizadoClasse && !autorizadoMetodo)) {
    request.setAttribute("jakarta.servlet.error.status_code", 403);
    throw new RuntimeException("Recurso nao autorizado");
   }
  }
  return true;
 }

 @Override
 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
   throws Exception {
  boolean config = false;
  if (!config && (handler instanceof HandlerMethod)) {
   return this.validaLogin(request, response, handler);
  }
  return true;
 }

 @Override
 public void postHandle(HttpServletRequest request, HttpServletResponse response,
   Object handler, ModelAndView modelAndView) throws Exception {
 }

 @Override
 public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
   Object handler, Exception ex) throws Exception {
 }
}
