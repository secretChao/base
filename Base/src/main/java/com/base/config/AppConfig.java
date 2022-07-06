package com.base.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.jndiName}")
    private String jndiName;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();//启用默认禁用的JNDI命名
                return super.getTomcatWebServer(tomcat);
            }
            @Override
            protected void postProcessContext(Context context) {
                //数据源 1
                //构建一个ContextResource对象，然后添加到Context对象中
                ContextResource resource = new ContextResource();
                resource.setName(jndiName);
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", driverClassName);
                resource.setProperty("url", url);
                resource.setProperty("username", username);
                resource.setProperty("password", password);
                context.getNamingResources().addResource(resource);

                super.postProcessContext(context);
            }
        };
        return tomcat;
    }
}
