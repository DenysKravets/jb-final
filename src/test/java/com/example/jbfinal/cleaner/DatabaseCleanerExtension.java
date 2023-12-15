package com.example.jbfinal.cleaner;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseCleanerExtension implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {

        DataSource ds = SpringExtension.getApplicationContext(extensionContext).getBean(DataSource.class);

        executeStatement(ds, "DELETE FROM convict_accomplices");
        executeStatement(ds, "DELETE FROM convict_criminal_organization");
        executeStatement(ds, "DELETE FROM convict_languages");
        executeStatement(ds, "DELETE FROM criminal_organization_convict");
        executeStatement(ds, "DELETE FROM convict");

    }

    private void executeStatement(DataSource ds, String statement) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            connection.prepareStatement(statement).execute();
        } catch (Exception e) {
            throw e;
        }
    }
}
