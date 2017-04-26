import java.util.ArrayList;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Iniebiyo Joshua on 4/24/2017.
 */
public class DBController {
    static CubesGUI cubesGUI;
    static CubesDB cubesDB;

    //Create a main class.
    public static void main(String[] args) {

        DBController dbController = new DBController();
        dbController.startApp();

    }

    private void startApp() {

        cubesDB = new CubesDB();
        cubesDB.createTable();
        ArrayList<Cubes> allData = cubesDB.fetchAllRecords();
        cubesGUI = new CubesGUI(this);
        cubesGUI.setListData(allData);
    }
    void delete(Cubes cubes){
        cubesDB.delete(cubes);
    }
    void updateRecord(Cubes cubes){
        cubesDB.updateRecord(cubes);
    }


    ArrayList<Cubes> getAllData() {
        return cubesDB.fetchAllRecords();
    }

    void addRecordToDatabase(Cubes cubes) {
        cubesDB.addRecord(cubes);
    }
}




