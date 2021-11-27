/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderOfService;

import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author natha
 */
public class ReviewServiceDetails extends javax.swing.JFrame {
    private String serviceID;

    /**
     * Creates new form ReviewServiceDetails
     */
    public ReviewServiceDetails(String ServiceID) {
        serviceID = ServiceID;
        
        initComponents();
        
        //Run method to get service details
        getServiceDetails();
        
        //Run method to get songs
        getSongs();
    }
    
    /**
     * Creates new form ReviewServiceDetails
     */
    public ReviewServiceDetails() {
        initComponents();
    }
    
    /**
     * Method that retrieves the details of the particular service that has 
     * been chosen
     */
    public void getServiceDetails() {
        int thisError = 0;
        //Number of records in the table
        int numItems = 0;
        
        //Create SQL Statements
        
        //serviceCount is a string that counts the number of records
        //recieved - should be 1
        String serviceCount = "select COUNT(*) from `Services` where `ServiceID` = " + serviceID;
        
        //serviceSelect is an SQL statement that gets most
        //of the service details
        String serviceSelect = "select * from `Services` where `ServiceID` = " + serviceID;
        
        //readingCount is to see how many different readings a service has - if
        //it has more than one then there is an error and the reading will not
        //be included in the service passage
        String readingCount = "select COUNT(distinct `ReadingID`) from `Contents` where `ServiceID` = " + serviceID;
        
        //selectReading is to return the bible passage (reference) that has been
        //used in a service. It will only be run if readingCount returns a value of 1
        String selectReading = "select distinct `ReadingID` from `Contents` where `ServiceID` = " + serviceID;
        
        //Set sqlString to serviceCount
        OrderOfServiceMain.dbObject.sqlString = serviceCount;
        thisError =  OrderOfServiceMain.dbObject.getCountBySelect();
        
        //Set numItems to the number of services returned by the count statement
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        if(numItems == 1) {
            //Sets the sqlString to serviceSelect
            OrderOfServiceMain.dbObject.sqlString = serviceSelect;
            
            //For testing purposes
            System.out.println(OrderOfServiceMain.dbObject.sqlString);
            
            //Execute the statement
            thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
            if(thisError == 0) {
                try {
                    while(OrderOfServiceMain.dbObject.rs.next()) {
                        String ServiceID = OrderOfServiceMain.dbObject.rs.getString ("ServiceID");
                        String Title = OrderOfServiceMain.dbObject.rs.getString("Title");
                        String Date = OrderOfServiceMain.dbObject.rs.getString ("Date");
                        String Session = OrderOfServiceMain.dbObject.rs.getString ("Session");
                        String ChurchName = OrderOfServiceMain.dbObject.rs.getString ("ChurchName");
                        String Filename = OrderOfServiceMain.dbObject.rs.getString ("Filename");
                        String SpeakerID = OrderOfServiceMain.dbObject.rs.getString ("SpeakerID");
                        
                        DefaultTableModel model = (DefaultTableModel) tblServiceDetails.getModel();
                        
                        model.addRow(new Object[] {ServiceID, Title, Date, Session, ChurchName, Filename, SpeakerID});        
                    }
                }
                catch(SQLException ex) {
                    // Show errors in console
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                    thisError = ex.getErrorCode ();

                    UserMessageBox MyMessage = new UserMessageBox ();
                    MyMessage.setMessage ("SQLException: " + ex.getMessage());
                    MyMessage.setVisible (true);
                }
                //Get the table model to check the number of rows
                DefaultTableModel model = (DefaultTableModel) tblServiceDetails.getModel();
                
                if(model.getRowCount() == 1) {
                    //For testing purposes
                    System.out.println(readingCount);
                    
                    //Set the sqlString to readingCount
                    OrderOfServiceMain.dbObject.sqlString = readingCount;
                    
                    OrderOfServiceMain.dbObject.getCountBySelect();
                    
                    if(OrderOfServiceMain.dbObject.NumberOfRecords == 1) {
                        //Set the sqlString to selectReading
                        OrderOfServiceMain.dbObject.sqlString = selectReading;
                        
                        //For testing purposes
                        System.out.println(selectReading);
                        
                        //Execute the statement
                        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
                        if(thisError == 0) {
                            try {
                                while(OrderOfServiceMain.dbObject.rs.next()) {
                                    String ReadingID = OrderOfServiceMain.dbObject.rs.getString("ReadingID");
                                    
                                    //Set correct index in table to ReadingID
                                    model.setValueAt(ReadingID, 0, 7);
                                }
                            }
                            catch(SQLException ex) {
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
                        
                    }
                }
                
            }
        }
        else {
            UserMessageBox myMessage = new UserMessageBox();
            myMessage.setTitle("Error retrieving data");
            myMessage.setMessage("There has been an error retrieving the data \n"
                    + "Therefore, no data is outputted");
            myMessage.setVisible(true); 
        }
    }
    
    /**
     * Method that gets all the songs and their keys from the service that has
     * been selected
     */
    public void getSongs() {
        int thisError = 0;
        //Number of songs in the service
        int numItems = 0;
        //Array to hold all relevant SongIDs
        String[] songIDs;
        
        //SQL statement that counts all relevant songs
        String songCount = "select COUNT(*) from `Contents` where `ServiceID` = " + serviceID;
        //SQL statement that retrieves all the SongIDs
        String songIDSelect = "select `SongID` from `Contents` where `ServiceID` = " + serviceID;
        
        //For testing purposes
        System.out.println(songCount);
        System.out.println(songIDSelect);
        
        //Get the number of songs
        OrderOfServiceMain.dbObject.sqlString = songCount;
        thisError = OrderOfServiceMain.dbObject.getCountBySelect();
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        if(numItems > 0) {
            //Create the array
            songIDs = new String[numItems];
            
            //Retrive the SongIDs
            OrderOfServiceMain.dbObject.sqlString = songIDSelect;
            thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
            if(thisError == 0) {
                try{
                    //i is a count variable
                    int i = 0;
                    
                    while(OrderOfServiceMain.dbObject.rs.next()) {
                        String SongID = OrderOfServiceMain.dbObject.rs.getString("SongID");
                        
                        //Add the SongID to the array
                        songIDs[i] = SongID;
                        
                        //Increment i
                        i++;
                    }
                }
                catch(SQLException ex) {
                    // Show errors in console
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                    thisError = ex.getErrorCode ();

                    UserMessageBox MyMessage = new UserMessageBox ();
                    MyMessage.setMessage ("SQLException: " + ex.getMessage());
                    MyMessage.setVisible (true);
                }
                //Get the song details using array
                for(int i = 0; i < songIDs.length; i++) {
                    //SQL String that gets the song details
                    String songSelect = "select `SongID`, `SongTitle`, `Key` from `Songs` where `SongID` = " + songIDs[i];
                    
                    //For testing purposes
                    System.out.println(songSelect);
                    
                    //Execute statement
                    OrderOfServiceMain.dbObject.sqlString = songSelect;
                    thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
                    if(thisError == 0) {
                        try {
                            while(OrderOfServiceMain.dbObject.rs.next()) {
                                //Add the songs to the table
                                String SongID = OrderOfServiceMain.dbObject.rs.getString("SongID");
                                String SongTitle = OrderOfServiceMain.dbObject.rs.getString("SongTitle");
                                String Key = OrderOfServiceMain.dbObject.rs.getString("Key");
                                
                                DefaultTableModel model = (DefaultTableModel) tblSongs.getModel();
                                model.addRow(new Object[]{SongID, SongTitle, Key});
                            }
                        }
                        catch(SQLException ex) {
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
                    else {
                        //Create an error message
                        UserMessageBox myMessage = new UserMessageBox();
                        myMessage.setTitle("Error");
                        myMessage.setMessage("There has been an error loading the songs.");
                        myMessage.setVisible(true);
                    }
                }    
            }
            else {
                //Create an error message
                UserMessageBox myMessage = new UserMessageBox();
                myMessage.setTitle("Error");
                myMessage.setMessage("There has been an error loading the songs.");
                myMessage.setVisible(true);
            }
        }
        else {
            //Create an error message
            UserMessageBox myMessage = new UserMessageBox();
            myMessage.setTitle("Error");
            myMessage.setMessage("There appears to be no songs in this service.");
            myMessage.setVisible(true);
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
        lTitle = new javax.swing.JLabel();
        lLogo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblServiceDetails = new javax.swing.JTable();
        lServiceDetails = new javax.swing.JLabel();
        lSongs = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSongs = new javax.swing.JTable();
        bExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GWBC Order of Service Manager - Review Service Details");

        jPanel1.setBackground(new java.awt.Color(218, 227, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));

        lTitle.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lTitle.setForeground(new java.awt.Color(31, 56, 100));
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTitle.setText("GWBC Review Service Details");

        lLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OrderOfService/GWBC Logo.png"))); // NOI18N

        tblServiceDetails.setBackground(new java.awt.Color(218, 227, 243));
        tblServiceDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ServiceID", "Title", "Date", "Session", "ChurchName", "Filename", "SpeakerID", "Bible Passage"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblServiceDetails);

        lServiceDetails.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lServiceDetails.setForeground(new java.awt.Color(31, 56, 100));
        lServiceDetails.setText("Service Details:");

        lSongs.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lSongs.setForeground(new java.awt.Color(31, 56, 100));
        lSongs.setText("Songs:");

        tblSongs.setBackground(new java.awt.Color(218, 227, 243));
        tblSongs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SongID", "Title", "Key"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblSongs);

        bExit.setBackground(new java.awt.Color(180, 199, 231));
        bExit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bExit.setForeground(new java.awt.Color(31, 56, 100));
        bExit.setText("Exit to Review Services");
        bExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lServiceDetails)
                            .addComponent(lSongs)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bExit)
                            .addComponent(lLogo))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lServiceDetails)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lSongs)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)))
                .addComponent(lLogo)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * ActionListener that disposes this form and reopens a ReviewServices form
     * @param evt 
     */
    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExitActionPerformed
        //Create new ReviewServices form and set it visible
        ReviewServices reviewServices = new ReviewServices();
        reviewServices.setVisible(true);
        
        //Dispose this form
        dispose();

    }//GEN-LAST:event_bExitActionPerformed

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
            java.util.logging.Logger.getLogger(ReviewServiceDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReviewServiceDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReviewServiceDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReviewServiceDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReviewServiceDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bExit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lLogo;
    private javax.swing.JLabel lServiceDetails;
    private javax.swing.JLabel lSongs;
    private javax.swing.JLabel lTitle;
    private javax.swing.JTable tblServiceDetails;
    private javax.swing.JTable tblSongs;
    // End of variables declaration//GEN-END:variables
}
