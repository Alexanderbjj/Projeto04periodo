package br.unisales.projetos.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Hebert
 */
@Component
@PropertySource("classpath:jwt.properties")
public class JWTUtil implements Serializable {
  @Value("${jwt.validadetoken}")
 private Long validadeToken;
 @Value("${jwt.segredotoken}")
 private String segredoToken;

 /**
  * retorna o valor do segredo que é a verificação a ser usda para serializar o
  * token
  * nesse retorno é usado um algoritmo de proteção
  * 
  * @return
  */
 private SecretKey CHAVE() {
  return Keys.hmacShaKeyFor(segredoToken.getBytes(StandardCharsets.UTF_8));
 }

 /**
  * extrai todos os dados de um token
  * esses dados são chamados de Claim
  * 
  * @param token
  * @return
  */
 public Claims extraiTodosDados(String token) {
  Jws<Claims> jc = Jwts.parser().verifyWith(CHAVE()).build().parseSignedClaims(token);
  return jc.getPayload();
 }

 /**
  * extrai um dado específico do token
  * 
  * @param <T>
  * @param token
  * @param claimsResolver
  * @return
  */
 public <T> T extraiDado(String token, Function<Claims, T> claimsResolver) {
  final Claims claims = extraiTodosDados(token);
  return claimsResolver.apply(claims);
 }

 // public <T> T getClaimFromToken(String token, Function<Claims, T>
 // claimsResolver) {
 // final Claims claims = extraiTodosDados(token);
 // return claimsResolver.apply(claims);
 // };

 /**
  * recupera o nome do usuário que consta no token
  * 
  * @param token
  * @return
  */
 public String getUsuarioNoToken(String token) {
  if (token == null)
   return null;
  return extraiDado(token, Claims::getSubject);
 }

 /**
  * recupera a data de validade do token
  * 
  * @param token
  * @return
  */
 public Date getDataValidadeToken(String token) {
  return extraiDado(token, Claims::getExpiration);
 }

 /**
  * verifica se um token está expirado
  * 
  * @param token
  * @return
  */
 private Boolean tokenExpirado(String token) {
  if (token == null)
   return true;
  final Date expiration = getDataValidadeToken(token);
  return expiration.before(new Date());
 }

 /**
  * gera um token com um usuário associado a ele
  * sem nenhuma outra propriedade
  * 
  * @param nomeUsuario
  * @return
  */
 public String geraTokenUsuario(String nomeUsuario) {
  Map<String, Object> claims = new HashMap<>();
  return geraToken(claims, nomeUsuario);
 }

 /**
  * gera um token com um usuário associado a ele
  * com outras propriedades associadas
  * 
  * @param nomeUsuario
  * @param claims
  * @return
  */
 public String geraTokenUsuario(String nomeUsuario, Map<String, Object> claims) {
  return geraToken(claims, nomeUsuario);
 }

 /**
  * gera um token com propriedades associadas
  * 
  * @param claims
  * @param subject
  * @return
  */
 private String geraToken(Map<String, Object> claims, String nomeUsuario) {
  JwtBuilder builder = Jwts.builder()
    .subject(nomeUsuario)
    .expiration(new Date(System.currentTimeMillis() + validadeToken))
    .signWith(CHAVE());

  builder.claims(claims);

  String jwtToken = builder.compact();

  return jwtToken;
 }

 /**
  * valida um token verificando se
  * 1 - existe usuário
  * 2 - não está expirado
  * 
  * @param token
  * @param nomeUsuario
  * @return
  */
 public Boolean validaToken(String token, String nomeUsuario) {
  final String _nomeUsuario = getUsuarioNoToken(token);
  return (nomeUsuario.equals(_nomeUsuario) && !tokenExpirado(token));
 }

 /**
  * valida um token verificando:
  * 1 - não está expirado
  * 
  * @param token
  * @return
  */
 public Boolean validaToken(String token) {
  return (!tokenExpirado(token));
 }

 /**
  * recupera o token proveniente de uma requisição
  * a requisição deve ter um HEADER = "Authorization"
  * e o valor dessa chave (HEADER) deve inicar com "Bearer "
  * 
  * @param request
  * @return
  */
 public String recuperaTokenRequisicao(HttpServletRequest request) {
  String requestTokenHeader = request.getHeader("Authorization");
  if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
   return requestTokenHeader.substring(7);
  }
  return null;
 }

 /**
  * método auxiliar para verificar se
  * um token possui uma determinada permissão
  * 
  * @param token
  * @param permissao
  * @return
  */
 public boolean verificaAccesso(String token, String permissao) {
  try {
   Claims c = this.extraiTodosDados(token);
   if (c.containsKey("PERMISSOES")) {
    String permissoes = (String) c.get("PERMISSOES");
    if (permissoes.contains(permissao)) {
     return true;
    }
   }
  } catch (Exception e) {
   throw new RuntimeException("Token inválido");
  }
  return false;
 }
}