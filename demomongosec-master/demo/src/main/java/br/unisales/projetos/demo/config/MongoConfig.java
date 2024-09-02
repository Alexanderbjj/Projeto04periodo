package br.unisales.projetos.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

/**
 * configuranção necessária para usar indices no mongodb
 * adicionada a propriedade condicional se esta usando instancia local ou no atlas
 * localmente funciona o index mas no atlas (FREE) não
 */
@ConditionalOnProperty(value = "app.use.mongolocal", havingValue = "true")
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
 @Override
 protected boolean autoIndexCreation() {
  return true;
 }

 @SuppressWarnings("null")
 @Override
 protected String getDatabaseName() {
  return "demosbsec";
 }

}
