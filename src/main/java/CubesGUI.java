import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.sql.*;
import java.util.*;


/**
 * Created by Iniebiyo Joshua on 4/24/2017.
 */
public class CubesGUI extends JFrame {
    private JPanel rootPanel;
    private JLabel NameLabel;
    private JLabel TimeLabel;
    private JLabel SearchNameLabel;
    private JLabel NewTimeLabel;
    private JTextField TimeTextField;
    private JTextField NameTextField;
    private JTextField SearchNameTextField;
    private JTextField NewTimeTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton quitButton;
    private JButton updateButton;
    private JLabel searchLabel;
    private JList<Cubes> DisplayList;
    private JScrollPane DisplayListScrollPane;
    private DefaultListModel<Cubes> DisplayListModel;
    private DBController dbController;

    CubesGUI(DBController dbController) {
        super("Rubik Cube Solver");
        setPreferredSize(new Dimension(600,550));
        this.dbController = dbController;
        addComponents();
        //Configures the list model
        DisplayListModel = new DefaultListModel<Cubes>();
        DisplayList.setModel(DisplayListModel);
        setContentPane(rootPanel);
        pack();
        setVisible(true);
        addListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setContentPane(rootPanel);
    }

    //Event handlers for delete.
    private void addListeners() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cubes cubes = DisplayList.getSelectedValue();
                if(cubes ==null){
                    JOptionPane.showMessageDialog(CubesGUI.this,"Please select the record to delete");
                }else {
                    dbController.delete(cubes);
                    ArrayList<Cubes> cubes1 = dbController.getAllData();
                    setListData(cubes1);
                }
            }
        });
        //Event handler for update.
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchName = SearchNameTextField.getText();
                if(searchName.isEmpty()){
                    JOptionPane.showMessageDialog(CubesGUI.this,
                            "Please type the name to update time");
                    return;
                }
                double updateTime;
                try{
                    updateTime = Double.parseDouble(NewTimeTextField.getText());
                }catch (NumberFormatException ne){
                    JOptionPane.showMessageDialog(CubesGUI.this,
                            "Enter the new time to update");
                    return;
                }
                Cubes cubesUpdateRecord = new Cubes(searchName,updateTime);
                dbController.updateRecord(cubesUpdateRecord);
                SearchNameTextField.setText("");
                NewTimeTextField.setText("");
                ArrayList<Cubes> allData = dbController.getAllData();
                setListData(allData);
            }
        });
        //Event handlers for add.
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Read data, send message to database via controller

                String name = NameTextField.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(CubesGUI.this, "Enter the name");
                    return;
                }
                double time;

                try {
                    time = Double.parseDouble(TimeTextField.getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(CubesGUI.this, "Enter the number for time");
                    return;
                }

                Cubes cubesRecord = new Cubes(name, time);
                dbController.addRecordToDatabase(cubesRecord);

                //Clear input JTextFields
                NameTextField.setText("");
                TimeTextField.setText("");

                //and request all data from DB to update list
                ArrayList<Cubes> allData = dbController.getAllData();
                setListData(allData);
            }
        });
    }
    void setListData(ArrayList<Cubes> data) {

        //Display data in allDataTextArea
        DisplayListModel.clear();

        for (Cubes cb : data) {
            DisplayListModel.addElement(cb);
        }
    }
    //Add GUI components.
    private void addComponents(){
        rootPanel = new JPanel();
        NameLabel = new JLabel("Name");
        NameTextField = new JTextField();
        TimeLabel = new JLabel("Time");
        TimeTextField = new JTextField();
        SearchNameLabel = new JLabel("Search Name");
        SearchNameTextField = new JTextField();
        NewTimeLabel = new JLabel(" New Time ");
        NewTimeTextField = new JTextField();
        addButton = new JButton("   Add   ");
        deleteButton = new JButton("Delete");
        //quitButton = new JButton("quit      ");
        updateButton = new JButton("Update");
        searchLabel = new JLabel("    ");
        DisplayList = new JList<Cubes>();
        DisplayListScrollPane = new JScrollPane(DisplayList);

        BoxLayout layout = new BoxLayout(rootPanel,BoxLayout.Y_AXIS);
        rootPanel.setLayout(layout);

        rootPanel.add(NameLabel);
        rootPanel.add(NameTextField);
        rootPanel.add(TimeLabel);
        rootPanel.add(TimeTextField);
        rootPanel.add(SearchNameLabel);
        rootPanel.add(SearchNameTextField);
        rootPanel.add(NewTimeLabel);
        rootPanel.add(NewTimeTextField);
        rootPanel.add(addButton);
        rootPanel.add(searchLabel);
        rootPanel.add(updateButton);
        //rootPanel.add(searchButton);
        //rootPanel.add(DisplayList);
        rootPanel.add(DisplayListScrollPane);
        rootPanel.add(deleteButton);
        //rootPanel.add(quitButton);
    }
}


