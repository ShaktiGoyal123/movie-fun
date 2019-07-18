package org.superbiz.moviefun.albums;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.superbiz.moviefun.DatabaseServiceCredentials;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class AlbumsDatabaseConfig {

    @Bean
    public DataSource albumsDataSource(DatabaseServiceCredentials serviceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(serviceCredentials.jdbcUrl("albums-mysql"));
        HikariConfig config = new HikariConfig();
        config.setDataSource(dataSource);
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean albumlocalContainerEntityManagerFactoryBean (DataSource albumsDataSource, HibernateJpaVendorAdapter albumsHibernateJpaVendorAdapter)
    {

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean= new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(albumsDataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(albumsHibernateJpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan(this.getClass().getPackage().getName());
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("album-database");
        return localContainerEntityManagerFactoryBean;

    }


    @Bean
    public PlatformTransactionManager albumsTransactionManager(EntityManagerFactory albumlocalContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(albumlocalContainerEntityManagerFactoryBean);
    }

}
