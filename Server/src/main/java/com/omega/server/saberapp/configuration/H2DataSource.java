package com.omega.server.saberapp.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
/**
 * 
 * @author Ramon
 *Configuración de la base de datos embebida
 *indicamos el fichero de propiedades, habilita la consola para la bbdd cuando está levantada la app
 *carga los ficheros de la base de datos H2 embebida, en uno de ellos crea las tablas y en otro carga los datos
 *Unicamente serán persistentes si se configura el espacio dentro del disco  donde se almacenarán
 *Se puede así mismo configurar otro data source con una base de datos externa.
 *Facilita el desarrollo y las pruebas.
 *
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:common-database.properties")
public class H2DataSource {

	// jdbc:h2:mem:testdb
	@Bean
	public DataSource dataSource() {

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).addScript("saberapp-schema.sql").addScript("saberapp-data.sql").build();
		return db;

	}
	 @Bean
	   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	      LocalContainerEntityManagerFactoryBean em 
	        = new LocalContainerEntityManagerFactoryBean();
	      em.setDataSource(dataSource());
	      em.setPackagesToScan(new String[] { "com.omega" });
	 
	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	      em.setJpaVendorAdapter(vendorAdapter);
	
	 
	      return em;
	   }
	 @Bean
	 public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	     JpaTransactionManager transactionManager = new JpaTransactionManager();
	     transactionManager.setEntityManagerFactory(emf);
	  
	     return transactionManager;
	 }

	 
}
