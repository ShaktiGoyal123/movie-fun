package org.superbiz.moviefun.movies;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.superbiz.moviefun.DatabaseServiceCredentials;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class MovieDatabaseConfig {

    @Bean
    public DataSource movieDataSource(DatabaseServiceCredentials ServiceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(ServiceCredentials.jdbcUrl("movies-mysql"));
        HikariConfig config = new HikariConfig();
        config.setDataSource(dataSource);
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean movielocalContainerEntityManagerFactoryBean (DataSource movieDataSource,HibernateJpaVendorAdapter movieHibernateJpaVendorAdapter)
    {

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean= new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(movieDataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(movieHibernateJpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan(this.getClass().getPackage().getName());
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("movies");
        return localContainerEntityManagerFactoryBean;

    }

    @Bean
    public PlatformTransactionManager moviesTransactionManager(  EntityManagerFactory  movielocalContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(movielocalContainerEntityManagerFactoryBean);
    }

}
