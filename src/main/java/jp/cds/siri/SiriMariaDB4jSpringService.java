package jp.cds.siri;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.google.common.collect.Lists;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;

@Configuration
@Profile("inmemory")
public class SiriMariaDB4jSpringService extends MariaDB4jSpringService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_PATH = "db/migration/";
    private static final String DATA_SOURCE_NAME = "dataSource";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void start() {
        super.start();
        // スキーマ作成
        try {
            db.createDB("siri");

            // DDLの読み込み
            DataSource dataSource = (DataSource) applicationContext.getBean(DATA_SOURCE_NAME);
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

            // TODO: 動的に取るようにする
            List<String> migrationFile = Lists.newArrayList();
            migrationFile.add("V201805152300__create_database.sql");
            migrationFile.add("V201805191800__add_index.sql");
            for (String name : migrationFile) {
                logger.debug(String.format("Create table: %s", name));
                databasePopulator.addScript(new ClassPathResource(BASE_PATH + name));
            }

            DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        } catch (final ManagedProcessException ex) {
            ex.printStackTrace();
        }
    }

}