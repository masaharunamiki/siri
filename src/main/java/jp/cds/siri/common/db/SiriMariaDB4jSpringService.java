package jp.cds.siri.common.db;

import java.io.File;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;

public class SiriMariaDB4jSpringService extends MariaDB4jSpringService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_PATH = "db/";
    private static final String DATA_SOURCE_NAME = "dataSource";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void start() {
        super.start();
        // スキーマ作成
        try {
            db.createDB("siritori");

            // DDLの読み込み
            DataSource dataSource = (DataSource) applicationContext.getBean(DATA_SOURCE_NAME);
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

            ClassLoader classLoader = getClass().getClassLoader();
            File dir = new File(classLoader.getResource(BASE_PATH).getFile());

            for (File f : dir.listFiles()) {
                logger.debug(String.format("Create table: %s", f.getName()));
                databasePopulator.addScript(new ClassPathResource(BASE_PATH + f.getName()));
            }
            DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        } catch (final ManagedProcessException ex) {
            ex.printStackTrace();
        }
    }

}