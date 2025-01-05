package pt.ipp.isep.dei.esoft.project.files;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_RESET;
import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_YELLOW;

public class ExportTimes {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "C##manager";
    private static final String PASSWORD = "manager123";

    public static void updateReservedTable(float value, int id) {
        String updateQuery = "UPDATE Operation SET EXPECTEDTIME = ? WHERE OPERATION_ID = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Set the values for the placeholders
            preparedStatement.setInt(1, (int) value);
            preparedStatement.setInt(2, id);

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();

            System.out.printf("%n%sUpdate completed successfully!%s%n   • Rows affected: %s%n   • Operation ID : %s%n   • Time : %s%n",ANSI_YELLOW,ANSI_RESET,rowsAffected,id,value);

        } catch (SQLException e) {
            System.err.println("\nError during update execution: " + e.getMessage());
        }
    }


}
