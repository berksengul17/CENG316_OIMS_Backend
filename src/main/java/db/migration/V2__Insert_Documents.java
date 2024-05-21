package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class V2__Insert_Documents extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        insertDocument(context, "/db/files/1_TR_SummerPracticeApplicationLetter2023.docx");
        insertDocument(context, "/db/files/1_EN_SummerPracticeApplicationLetter2023.docx");
    }

    private void insertDocument(Context context, String resourcePath) throws SQLException {
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + resourcePath);
            }
            byte[] content = inputStream.readAllBytes();
            try (PreparedStatement statement = context.getConnection().prepareStatement(
                    "INSERT INTO document (content_type, status, type, content) VALUES (?, ?, ?, ?)")) {
                statement.setString(1, "docx");
                statement.setString(2, "APPROVED");
                statement.setString(3, "APPLICATION_LETTER_TEMPLATE");
                statement.setBytes(4, content);
                statement.execute();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error inserting document", e);
        }
    }
}
