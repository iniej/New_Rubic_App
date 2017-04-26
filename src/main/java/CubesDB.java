import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Iniebiyo Joshua on 4/24/2017.
 */
public class CubesDB {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
    //Connection string – connection to MYSQL Database in MySQL
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/rubicCubes";   //Name of Database -- rubicCubes.
    private static final String USER = "friday";   //MYSQL username
    private static final String PASSWORD = "friday";   //MYSQL password
    private static final String TABLE_NAME = "cube";
    private static final String CUBE_SOLVER_COLUMN = "Things"; //Things is the name of the rubik cube solver.
    private static final String TIME_TAKEN_COLUMN = "timeTaken"; //This the time taken to solve a rubik cube.

    CubesDB() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drives and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }
    }
    //Create a table for data handling.
    void createTable() {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String createSQLTableTemplate = "CREATE TABLE IF NOT EXISTS %s (%s VARCHAR (50), %s DOUBLE)";
            String createSQLTable = String.format(createSQLTableTemplate, TABLE_NAME, CUBE_SOLVER_COLUMN, TIME_TAKEN_COLUMN);

            statement.executeUpdate(createSQLTable);

            statement.close();
            connection.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    void addRecord(Cubes cubes)  {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            String addSQLcubes = "INSERT INTO " + TABLE_NAME + " VALUES ( ? , ? ) " ;
            PreparedStatement addSQLcubesRecord = connection.prepareStatement(addSQLcubes);
            addSQLcubesRecord.setString(1, cubes.name);
            addSQLcubesRecord.setDouble(2, cubes.time);

            addSQLcubesRecord.execute();

            //TO DO add a message dialog box with "Added cube solver record for 'cubesolver name'" message.

            addSQLcubesRecord.close();
            connection.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

    }

    //
    void delete(Cubes cubes){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)){
            String deleteSQLcubes = "DELETE FROM %s WHERE %s = ? AND %s = ?";
            String deleteSQLcubesRecord = String.format(deleteSQLcubes,TABLE_NAME,CUBE_SOLVER_COLUMN,TIME_TAKEN_COLUMN);
            PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteSQLcubesRecord);
            deletePreparedStatement.setString(1,cubes.name);
            deletePreparedStatement.setDouble(2,cubes.time);
            //System.out.println(deletePreparedStatement.toString());

            deletePreparedStatement.execute();
            deletePreparedStatement.close();
            connection.close();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    void updateRecord(Cubes cubes){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL,USER,PASSWORD)){
            String updateSQLcubes = "UPDATE %s SET %s = ? WHERE %s = ?";
            String updateSQLcubesRecord = String.format(updateSQLcubes,TABLE_NAME,TIME_TAKEN_COLUMN,CUBE_SOLVER_COLUMN);
            PreparedStatement updatePreparedStatement = connection.prepareStatement(updateSQLcubesRecord);
            updatePreparedStatement.setDouble(1,cubes.time);
            updatePreparedStatement.setString(2,cubes.name);
            updatePreparedStatement.execute();
            updatePreparedStatement.close();
            connection.close();
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    ArrayList<Cubes> fetchAllRecords() {

        ArrayList<Cubes> allRecords = new ArrayList();

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String selectSQLtable = "SELECT * FROM " + TABLE_NAME;
            ResultSet rs = statement.executeQuery(selectSQLtable);

            while (rs.next()) {
                String name = rs.getString(CUBE_SOLVER_COLUMN);
                double time = rs.getDouble(TIME_TAKEN_COLUMN);
                Cubes cubeSolverRecord = new Cubes(name, time);
                allRecords.add(cubeSolverRecord);
            }

            rs.close();
            statement.close();
            connection.close();

            return allRecords;    //If there's no data, this will be empty

        } catch (SQLException se) {
            se.printStackTrace();
            return null;  //since we have to return something.
        }
    }
}





