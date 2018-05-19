package jp.cds.siri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    // @Value("${mariaDB4j.port}")
    // private Integer mariaDbPort;
    //
    // // override MariaDB4jSpringService.class
    // // @Bean
    // protected SiriMariaDB4jSpringService mariaDB4j() throws
    // ManagedProcessException {
    // final SiriMariaDB4jSpringService bean = new SiriMariaDB4jSpringService();
    // bean.setDefaultPort(mariaDbPort);
    // return bean;
    // }
    //
    // protected @Autowired SiriMariaDB4jSpringService dbService;
    //
    // public static void main(String[] args) throws Exception {
    // SpringApplication.run(Application.class, args);
    //
    // // final SpringApplication app = new SpringApplication(Application.class);
    // // app.setBannerMode(Mode.OFF);
    // // final ConfigurableApplicationContext ctx = app.run(args);
    // //
    // // MariaDB4jService.waitForKeyPressToCleanlyExit();
    // // ctx.stop();
    // // ctx.close();
    // }
    //
    // @Override
    // public int getExitCode() {
    // return dbService.getLastException() == null ? 0 : -1;
    // }

}
