/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderOfService;


import java.io.IOException;
import java.io.FileWriter;
import java.sql.SQLException;



/**
 *
 * @author 7-NDeverill
 */
public class SubmitService extends javax.swing.JFrame {

    public OrderOfService.CreateServiceManual createServiceManual;
    private String time; //Variable that makes up part of the .txt filename
    private String longTime; //Morning/Evening version of variable time
    private String filename;
    private String serviceTitle;
    
    /**
     * Creates new form SubmitService
     * @param ManualCreator is used as an identical version of the CreateServiceManual form that this instance is derived from
     */
    public SubmitService(OrderOfService.CreateServiceManual ManualCreator) {
        //Creates a CreateServiceManual form and sets it visible
        createServiceManual = ManualCreator;
        createServiceManual.setVisible(false);
        
        //Initialises components
        initComponents();
        
        time = "";
        longTime = "";
        
        //Gets the correct table model for tblOrderOfService
        tblOrderOfService.setModel(OrderOfService.CreateServiceManual.tblOrderOfService.getModel());
    }
    
    
    /**
     * Creates new form SubmitService
     */
    public SubmitService() {
        initComponents();
        
        time = "";
        longTime = "";
        
        //Get the correct table model for tblOrderOfService
        tblOrderOfService.setModel(OrderOfService.CreateServiceManual.tblOrderOfService.getModel());
    }
    
    
    /**
     * A method used to add a service to the database
     * Adds a record to the service table and a record to the contents table
     */
    private void AddServiceToDatabase() {
        boolean error = false;
        boolean containsChurchName = false;
        boolean containsSpeaker = false;
        int thisError = 0;
        int speakerID = -1; //Default value to speaker is not found
        String SQLStatement1 = ""; //Add to Services table string
        String SQLStatement2 = ""; //Add to Contents table string
        
        
        //Construct the first SQL statement
        
        SQLStatement1 = "insert into `Services` (";
        
        if(tfChurchName.getText() == "") {
            SQLStatement1 = SQLStatement1 + "`Title`, `Date`, `Session`, `Filename`";
        }
        else {
            SQLStatement1 = SQLStatement1 + "`Title`, `Date`, `Session`, `ChurchName`, `Filename`";
            containsChurchName = true;
        }
        
        
        
        //Search table model for a Speaker
        int tableLength = tblOrderOfService.getModel().getRowCount();
        int i = 0;
        String speakerName = "";
        
        while(i < tableLength && containsSpeaker == false) {
            if(tblOrderOfService.getModel().getValueAt(i, 1) == "Speaker") {
                speakerName = tblOrderOfService.getModel().getValueAt(i, 2).toString();
                containsSpeaker = true;
            }
            
            i++;
        }
        
        
        
        //Check if speaker name is an empty string
        if(speakerName != "") {
            //Get the SpeakerID for the Speaker that has been found
            speakerID = findSpeakerID(speakerName);
            
            if(speakerID != -1) {
                SQLStatement1 = SQLStatement1 + ", `SpeakerID`";
            } 
            else {
                containsSpeaker = false;
            }
        }
        
        
        
        SQLStatement1 = SQLStatement1 + ") values ('" + serviceTitle + "', '" + tfDate.getText() + "', '"
                + longTime + "'";
        
        if(containsChurchName == true) {
            SQLStatement1 = SQLStatement1 + ", '" + tfChurchName.getText() + "'";
        }
        
        SQLStatement1 = SQLStatement1 + ", '" + filename + "'";
        
        if(containsSpeaker == true) {
            SQLStatement1 = SQLStatement1 + ", " + speakerID;
        }
        
        SQLStatement1 = SQLStatement1 + ")";
        
        
        
        
        //Add service to database
        
        System.out.println(SQLStatement1);
        
        OrderOfServiceMain.dbObject.sqlString = SQLStatement1;
        OrderOfServiceMain.dbObject.insertRecord();
        if(thisError == 0) {
            //The service has been inserted correctly, now to insert into the contents table
            int serviceID = findServiceID(tfDate.getText(), longTime);
            
            if(serviceID == -1) {
                UserMessageBox myMessage = new UserMessageBox();
                myMessage.setTitle("Error!");
                myMessage.setMessage("There has been an error retrieving the ServiceID. \n"
                        + "Therefore, the contents of the service have not been added \n"
                        + "to the database");
                myMessage.setVisible(true);
            }
            else {
                boolean containsReading = false;
                
                //Construct the second SQL statement
                SQLStatement2 = "insert into `Contents` (`ServiceID`, `SongID`";
                
                //Search table model for a Reading
                i = 0;
                String reading = "";
        
                while(i < tableLength && containsReading == false) {
                    if(tblOrderOfService.getModel().getValueAt(i, 1) == "Reading") {
                        reading = tblOrderOfService.getModel().getValueAt(i, 2).toString();
                        containsReading = true;
                    }
            
                    i++;
                }
                
                if(containsReading == true && reading.isEmpty() == false) {
                    SQLStatement2 = SQLStatement2 + ", `ReadingID`) values (";
                }
                else {
                    SQLStatement2 = SQLStatement2 + ") values (";
                }
                
                //Use a while loop so that every song is a new record
                
                i = 0;
                
                while(i < tableLength) {
                    if(tblOrderOfService.getModel().getValueAt(i, 1) == "Song") {
                        String title = tblOrderOfService.getModel().getValueAt(i, 2).toString();
                        String key = tblOrderOfService.getModel().getValueAt(i, 3).toString();
                        int songID = findSongID(title, key);
                        
                        if(songID != -1) {
                            //SQLStatement3 is a way of retaining the necessary parts of SQLStatement2
                            String SQLStatement3 = SQLStatement2 + serviceID + ", " + songID;
                            
                            if(containsReading == true) {
                                SQLStatement3 = SQLStatement3 + ", '" + reading + "'";
                            }
                            
                            SQLStatement3 = SQLStatement3 + ")";
                            
                            //Add record to Contents table
        
                            System.out.println(SQLStatement3);
        
                            OrderOfServiceMain.dbObject.sqlString = SQLStatement3;
                            OrderOfServiceMain.dbObject.insertRecord();
                        }
                        else {
                            UserMessageBox myMessage = new UserMessageBox();
                            myMessage.setTitle("Error!");
                            myMessage.setMessage("There has been an error retrieving a song ID.");
                            myMessage.setVisible(true);
                        }
                    }
                    i = i + 1;
                }
                
                
            }
            
        }

    }
    
    
    /**
     * Method that returns the SongID given it equals a given title and key
     * @param SongTitle is the title of the song
     * @param SongKey is the key of the song
     * @return the SongID found or -1 if there has been an error
     */
    private int findSongID(String SongTitle, String SongKey) {
        int thisError = 0;
        int numItems = 0;
        String songTitle = SongTitle;
        String songKey = SongKey;
        
        //Sets the SQL string to count from Songs
        OrderOfServiceMain.dbObject.sqlString = "select COUNT(*) from `Songs`"; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Create SQL Statement to retrieve SongID from database
        OrderOfServiceMain.dbObject.sqlString = "select `SongID` from `Songs` where `SongTitle` = '" + songTitle + "' and `Key` = '" + songKey + "'";
        
        
        //Execute statement
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        if (thisError == 0) {
            try {
                
                if(OrderOfServiceMain.dbObject.rs.next()) {
                    //Set SongID to the value found in the database
                    int SongID = Integer.parseInt(OrderOfServiceMain.dbObject.rs.getString("SongID"));
                    
                    //Return the found value for SongID
                    return SongID;
                }
                
                return -1;
            }
            catch (SQLException ex) {
                // Show errors in console
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                thisError = ex.getErrorCode ();

                UserMessageBox MyMessage = new UserMessageBox ();
                MyMessage.setMessage ("SQLException: " + ex.getMessage());
                MyMessage.setVisible (true);
            }
        }
        
        return -1;
    }
    
    
    /**
     * Method that finds a service ID when the Date and Session is used as a parameter
     * @param Date is the Date of the service
     * @param Time is the Session of the service
     * @return the correct ServiceID
     */
    private int findServiceID(String Date, String Time) {
        int thisError = 0;
        int numItems = 0;
        String date = Date;
        String serviceTime = Time;
        
        //Sets the SQL string to count from Services
        OrderOfServiceMain.dbObject.sqlString = "select COUNT(*) from `Services`"; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Create SQL Statement to retrieve ServiceID from database
        OrderOfServiceMain.dbObject.sqlString = "select `ServiceID` from `Services` where `Date` = '" + date + "' and `Session` = '" + serviceTime + "'";
        
        
        //Execute statement
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        if (thisError == 0) {
            try {
                
                if(OrderOfServiceMain.dbObject.rs.next()) {
                    //Set ServiceID to the value found in the database
                    int ServiceID = Integer.parseInt(OrderOfServiceMain.dbObject.rs.getString("ServiceID"));
                    
                    //Return the found value for ServiceID
                    return ServiceID;
                }
                
                return -1;
            }
            catch (SQLException ex) {
                // Show errors in console
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                thisError = ex.getErrorCode ();

                UserMessageBox MyMessage = new UserMessageBox ();
                MyMessage.setMessage ("SQLException: " + ex.getMessage());
                MyMessage.setVisible (true);
            }
        }
        
        return -1;
    }
    
    
    /**
     * Method that finds a speaker ID when the speaker's name is used as a parameter
     * @param SpeakerName is the name of the speaker that the ID is looking for
     * @return 
     */
    private int findSpeakerID(String SpeakerName) {
        int thisError = 0;
        int numItems = 0;
        String speakerName = SpeakerName;
        
        //Sets the SQL string to count from Speakers
        OrderOfServiceMain.dbObject.sqlString = "select COUNT(*) from `Speakers`"; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Create SQL Statement to retrieve SpeakerID from record where SpeakerName is the same as the parameter
        OrderOfServiceMain.dbObject.sqlString = "select `SpeakerID` from `Speakers` where `SpeakerName` = '" + speakerName + "'";
        
        
        //Execute statement
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        if (thisError == 0) {
            try {
                
                if(OrderOfServiceMain.dbObject.rs.next()) {
                    //Set SpeakerID to the value found in the database
                    int SpeakerID = Integer.parseInt(OrderOfServiceMain.dbObject.rs.getString("SpeakerID"));
                    
                    //Return the found value for SpeakerID
                    return SpeakerID;
                }
                
                return -1;
            }
            catch (SQLException ex) {
                // Show errors in console
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                thisError = ex.getErrorCode ();

                UserMessageBox MyMessage = new UserMessageBox ();
                MyMessage.setMessage ("SQLException: " + ex.getMessage());
                MyMessage.setVisible (true);
            }
        }
        
        return -1;
    }
    
    
    /**
     * Method that saves the order of service as a .txt file and subsequently closes the form
     * @throws IOException 
     */
    private void ConvertToTextFile() throws IOException {
        try (FileWriter fw = new FileWriter(filename, true)) {
            //Create a 2D array to hold the order of service
            String[][] orderOfService = new String[tblOrderOfService.getModel().getRowCount()][2];
            
            //Populate the array
            for(int i = 0; i < tblOrderOfService.getModel().getRowCount(); i++) {
                orderOfService[i][0] = tblOrderOfService.getModel().getValueAt(i, 1).toString();
                orderOfService[i][1] = tblOrderOfService.getModel().getValueAt(i, 2).toString();
            }
            
            //Write the Order of Service to a text file
            for(int i = 0; i < orderOfService.length; i++) {
                fw.write(orderOfService[i][0] + ": " + orderOfService[i][1] + System.getProperty("line.separator"));
            }
            
            //Close the filewriter
            fw.flush();
            
            //Create a UserMessageBox that tells the user it has been successful
            UserMessageBox MyMessage = new UserMessageBox();
            MyMessage.setMessage("Success! \n"
                    + "The Order Of Service has been saved. \n"
                    + "It can be found inside the NetBeans project folder.");
            MyMessage.setVisible(true);
            
            //Close the form
            dispose();
        } catch (IOException ex) {
            UserMessageBox MyMessage = new UserMessageBox ();
            MyMessage.setMessage ("Error Saving Service: \n"
                    + "There has been an error saving the order of service. \n"
                    + "Service has not been saved.");
            MyMessage.setVisible (true);
        }
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lHeading = new javax.swing.JLabel();
        spTable = new javax.swing.JScrollPane();
        tblOrderOfService = new javax.swing.JTable();
        lDate = new javax.swing.JLabel();
        tfDate = new javax.swing.JTextField();
        lLogo = new javax.swing.JLabel();
        cbTime = new javax.swing.JComboBox();
        bBacktoCreate = new javax.swing.JButton();
        bExit = new javax.swing.JButton();
        bSubmit = new javax.swing.JButton();
        tfChurchName = new javax.swing.JTextField();
        lChurchName = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GWBC Order of Service Manager - Submit Service");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(218, 227, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));

        lHeading.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lHeading.setForeground(new java.awt.Color(31, 56, 100));
        lHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lHeading.setText("GWBC Submit Service");

        spTable.setBackground(new java.awt.Color(218, 227, 243));
        spTable.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Order of Service"));

        tblOrderOfService.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        spTable.setViewportView(tblOrderOfService);

        lDate.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        lDate.setForeground(new java.awt.Color(31, 56, 100));
        lDate.setText("Date of Service (YYYY-MM-DD):");

        tfDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfDateKeyTyped(evt);
            }
        });

        lLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OrderOfService/GWBC Logo.png"))); // NOI18N

        cbTime.setBackground(new java.awt.Color(142, 170, 219));
        cbTime.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbTime.setForeground(new java.awt.Color(31, 56, 100));
        cbTime.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Service Time", "Morning", "Evening" }));
        cbTime.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTimeItemStateChanged(evt);
            }
        });

        bBacktoCreate.setBackground(new java.awt.Color(180, 199, 231));
        bBacktoCreate.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bBacktoCreate.setForeground(new java.awt.Color(31, 56, 100));
        bBacktoCreate.setText("Back to Create Service");
        bBacktoCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBacktoCreateActionPerformed(evt);
            }
        });

        bExit.setBackground(new java.awt.Color(180, 199, 231));
        bExit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bExit.setForeground(new java.awt.Color(31, 56, 100));
        bExit.setText("Exit to Main Menu");
        bExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExitActionPerformed(evt);
            }
        });

        bSubmit.setBackground(new java.awt.Color(180, 199, 231));
        bSubmit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bSubmit.setForeground(new java.awt.Color(31, 56, 100));
        bSubmit.setText("Submit Service");
        bSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubmitActionPerformed(evt);
            }
        });

        lChurchName.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        lChurchName.setForeground(new java.awt.Color(31, 56, 100));
        lChurchName.setText("Church Name:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lLogo)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bExit, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bBacktoCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfDate)
                        .addComponent(cbTime, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfChurchName))
                    .addComponent(lChurchName))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lDate)
                        .addGap(18, 18, 18)
                        .addComponent(tfDate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbTime, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lChurchName)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(tfChurchName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(bExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bBacktoCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lLogo)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the forms that are open and returns to the Main Menu
     * @param evt 
     */
    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExitActionPerformed
        //Closes the current form
        dispose();
    }//GEN-LAST:event_bExitActionPerformed

    /**
     * Close this form and set the CreateServiceManualForm visible again
     * @param evt 
     */
    private void bBacktoCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBacktoCreateActionPerformed
        createServiceManual.setVisible(true);
        
        dispose();
    }//GEN-LAST:event_bBacktoCreateActionPerformed

    /**
     * Set the variables time, longTime and serviceTitle depending on what 
     * is selected (or not) in the combo box
     * @param evt 
     */
    private void cbTimeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTimeItemStateChanged
        if(cbTime.getSelectedItem() == "Morning") {
            time = "am";
            longTime = "Morning";
            serviceTitle = "Order of Morning Service";
        }
        else if(cbTime.getSelectedItem() == "Evening") {
            time = "pm";
            longTime = "Evening";
            serviceTitle = "Order of Evening Service";
        }
        else {
            time = "";
            longTime = "";
            serviceTitle = "";
        }
    }//GEN-LAST:event_cbTimeItemStateChanged

    /**
     * ActionListener for bSubmit
     * Submits a service once validated
     * @param evt 
     */
    private void bSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSubmitActionPerformed
        Boolean isValid = true;
        
        //Check the date has been inputted
        if(tfDate.getText().equals("")) {
            isValid = false;
        }
        
        //Check the time has been selected
        else if(time.isEmpty()) {
            isValid = false;
        }
        
        //Check the title of the service is not empty
        else if(serviceTitle.isEmpty()) {
            isValid = false;
        }
        
        //Check the table is not empty
        else if(tblOrderOfService.getModel().getColumnCount() == 0 || tblOrderOfService.getModel().getRowCount() == 0) {
            isValid = false;
        }
        
        
        if(isValid == true) {
            try {
                filename = tfDate.getText() + "_" + time + " order of service.txt";
                ConvertToTextFile();
                AddServiceToDatabase();
            } catch (IOException ex) {
                UserMessageBox MyMessage = new UserMessageBox();
                MyMessage.setMessage("IO Exception \n"
                        + "Order of Service has not been converted.");
                MyMessage.setVisible(true);
            }
        }
        else {
            UserMessageBox MyMessage = new UserMessageBox();
                MyMessage.setMessage("Error \n"
                        + "There has been an error saving the Order Of Service. \n"
                        + "Please check that: \n"
                        + "1. There are items in the order of service; \n"
                        + "2. The date has been inputted; \n"
                        + "3. You have selected a time");
                MyMessage.setVisible(true);
        }
    }//GEN-LAST:event_bSubmitActionPerformed

    /**
     * Key listener that performs a format check on the tfDate text field
     * @param evt is the event, holding info such as the character typed
     */
    private void tfDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfDateKeyTyped
        char inChar = evt.getKeyChar();
        int varSize = tfDate.getText().length();
        boolean isOK = true;
        
        //Set isOK to false in these conditions
        //The inputted character must be a digit unless in following conditions
        if(Character.isDigit(inChar) == false) {
            isOK = false;
        }
        //The 5th character in the string which is held in tfDate must be a '-'
        else if(varSize == 4 && (inChar == '-') == false) {
            isOK = false;
        }
        //The 8th character in the string which is held in tfDate must be a '-'
        else if(varSize == 7 && (inChar == '-') == false) {
            isOK = false;
        }
        //The length check of the validation
        else if(varSize == 10) {
            isOK = false;
        }
        
        //However, in these conditions, isOK is set to true
        //The character typed is '-' and the string is of length 4 
        if(varSize == 4 && inChar == '-') {
            isOK = true;
        }
        //The character typed is '-' and the string is of length 7
        else if(varSize == 7 && inChar == '-') {
            isOK = true;
        }
        
        //If at the end of the checks, isOK is false, the event evt must be consumed
        if(isOK == false) {
            evt.consume();
        }
        
    }//GEN-LAST:event_tfDateKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SubmitService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubmitService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubmitService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubmitService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubmitService().setVisible(true); 
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBacktoCreate;
    private javax.swing.JButton bExit;
    private javax.swing.JButton bSubmit;
    private javax.swing.JComboBox cbTime;
    private javax.swing.JPanel jPanel1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JLabel lChurchName;
    private javax.swing.JLabel lDate;
    private javax.swing.JLabel lHeading;
    private javax.swing.JLabel lLogo;
    private javax.swing.JScrollPane spTable;
    protected static javax.swing.JTable tblOrderOfService;
    private javax.swing.JTextField tfChurchName;
    private javax.swing.JTextField tfDate;
    // End of variables declaration//GEN-END:variables
}
