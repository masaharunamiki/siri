package jp.cds.siri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.MariaDB4jService;

@ComponentScan({ "jp.cds" })
@Configuration
@Profile("inmemory")
public class MariaDB4jApplication implements ExitCodeGenerator {
    @Value("${mariaDB4j.port}")
    private Integer mariaDbPort;

    // override MariaDB4jSpringService.class
    // @Bean
    protected SiriMariaDB4jSpringService mariaDB4j() throws ManagedProcessException {
        final SiriMariaDB4jSpringService bean = new SiriMariaDB4jSpringService();
        bean.setDefaultPort(mariaDbPort);
        return bean;
    }

    protected @Autowired SiriMariaDB4jSpringService dbService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MariaDB4jApplication.class, args);

        final SpringApplication app = new SpringApplication(MariaDB4jApplication.class);
        app.setBannerMode(Mode.OFF);
        final ConfigurableApplicationContext ctx = app.run(args);

        MariaDB4jService.waitForKeyPressToCleanlyExit();
        ctx.stop();
        ctx.close();
    }

    @Override
    public int getExitCode() {
        return dbService.getLastException() == null ? 0 : -1;
    }

}
