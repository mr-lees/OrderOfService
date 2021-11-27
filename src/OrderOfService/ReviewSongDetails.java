/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderOfService;

import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;

/**
 *
 * @author natha
 */
public class ReviewSongDetails extends javax.swing.JFrame {
    private String songID;

    /**
     * Creates new form ReviewSongDetails
     */
    public ReviewSongDetails(String SongID) {
        songID = SongID;
        
        initComponents();
        
        //Run method to retrieve the details about the particular song
        getSong();
        
        //Run method to retrieve all the tags of the song
        getTags();
    }
    
    /**
     * Creates new form ReviewSongDetails
     */
    public ReviewSongDetails() {
        songID = "";
        
        initComponents();
    }
    
    
    /**
     * Method that retrieves the song and its data 
     * and adds it to the table
     * @param SongCount is the count statement (SQL)
     * @param SelectSongs is the select statement (SQL)
     */
    public void getSong() {
        int thisError = 0;
        //Number of records in the table
        int numItems = 0;
        
        //SQL Statement that counts the number of records in table `Songs` of DB
        String songCount = "select COUNT(*) from `Songs` where `SongID` = " + songID;
        
        //SQL Statement that gets all the information in the Song's record 
        String selectSong = "select * from `Songs` where `SongID` = " + songID;
        
        //SQL Statement that gets the number of tags for a particular song
        String numTags = "select COUNT(*) from `SongTags` where `SongID` = " + songID;
        
        //SQL Statement that gets the number of uses for a song
        String numUses = "select COUNT(*) from `Contents` where `SongID` = " + songID;
         
        //SQL Statement that returns the last time a song was used
        String getMaxDate = "select max(date) as `Date` from `Services` "
                + "where `ServiceID` IN ("
                + "select `ServiceID` from `Contents` "
                + "where `SongID` = " + songID + ")";
        
        //Sets the SQL string to songCount
        OrderOfServiceMain.dbObject.sqlString = songCount; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        if(numItems == 1) {
            //Sets the SQL string to selectSongs
            OrderOfServiceMain.dbObject.sqlString = selectSong;
            
            //For testing purposes
            System.out.println(OrderOfServiceMain.dbObject.sqlString);
        
            //Execute the statement
            thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
            if(thisError == 0) {
                try { 
                    while(OrderOfServiceMain.dbObject.rs.next()) {
                        String SongID = OrderOfServiceMain.dbObject.rs.getString ("SongID");
                        String SongTitle = OrderOfServiceMain.dbObject.rs.getString ("SongTitle");
                        String Artist = OrderOfServiceMain.dbObject.rs.getString ("Artist");
                        String Year = OrderOfServiceMain.dbObject.rs.getString ("SongYear");
                        String Key = OrderOfServiceMain.dbObject.rs.getString ("Key");

                        DefaultTableModel model = (DefaultTableModel) tblSong.getModel();

                        model.addRow(new Object[]{SongID, SongTitle, Artist, Year, Key});
                    }  
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
            //Get the table model to check number of rows
            DefaultTableModel model = (DefaultTableModel) tblSong.getModel();
            
            if(model.getRowCount() > 0) {
                
                //For testing purposes
                System.out.println(numTags);
                System.out.println(numUses);
                
                OrderOfServiceMain.dbObject.sqlString = numTags;

                //Execute the statement
                OrderOfServiceMain.dbObject.getCountBySelect();

                int NumTags = OrderOfServiceMain.dbObject.NumberOfRecords;


                OrderOfServiceMain.dbObject.sqlString = numUses;

                //Execute the statement
                OrderOfServiceMain.dbObject.getCountBySelect();

                int NumUses = OrderOfServiceMain.dbObject.NumberOfRecords;
            
            
                //Add NumTags and NumUses to table
                model.setValueAt(NumTags, 0, 5);
                model.setValueAt(NumUses, 0, 6);
            
                OrderOfServiceMain.dbObject.sqlString = getMaxDate;
                
                System.out.println(OrderOfServiceMain.dbObject.sqlString);
            
                //Execute the statement
                OrderOfServiceMain.dbObject.getCountBySelect();
            
                try {
                    Date LastUsed = OrderOfServiceMain.dbObject.rs.getDate("Date");

                    //Add NumTags and NumUses and LastUsed to table
                    model.setValueAt(LastUsed, 0, 7);
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
        else {
            UserMessageBox myMessage = new UserMessageBox();
            myMessage.setTitle("Error retrieving data");
            myMessage.setMessage("There has been an error retrieving the data \n"
                    + "Therefore, no data is outputted");
            myMessage.setVisible(true); 
        }
        
        
    }
    
    /**
     * A method that retrieves all the relevant tags and adds them to the tags table
     */
    public void getTags() {
        int thisError = 0;
        //Number of tags
        int numItems = 0;
        
        //SQL statement that counts the number of relevant tags
        String countStatement = "select COUNT(*) from `Tags` where `TagID` = (" +
                "select `TagID` from `SongTags` where `SongID` = " + songID + ")";
        
        //SQL statement that retrieves all the relevant tags
        String selectStatement = "select * from `Tags` where `TagID` = (" +
                "select `TagID` from `SongTags` where `SongID` = " + songID + ")";
        
        //For testing purposes
        System.out.println(countStatement);
        System.out.println(selectStatement);
        
        //Get the number of tags
        OrderOfServiceMain.dbObject.sqlString = countStatement;
        thisError = OrderOfServiceMain.dbObject.getCountBySelect();
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Only run this if there is at least one relevant tag
        if(numItems > 0) {            
            //Retrieve the tags
            OrderOfServiceMain.dbObject.sqlString = selectStatement;
            thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
            if(thisError == 0) {
                try {                    
                    while(OrderOfServiceMain.dbObject.rs.next()) {
                        String TagID = OrderOfServiceMain.dbObject.rs.getString("TagID");
                        String TagDesc = OrderOfServiceMain.dbObject.rs.getString("TagDescription");
                        
                        //Add the tag to the tags table
                        DefaultTableModel model = (DefaultTableModel) tblTags.getModel();
                        model.addRow(new Object[]{TagID, TagDesc});
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSong = new javax.swing.JTable();
        bExit = new javax.swing.JButton();
        lLogo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTags = new javax.swing.JTable();
        lTags = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GWBC Order of Service Manager - Review Song Details");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(218, 227, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));

        lTitle.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lTitle.setForeground(new java.awt.Color(31, 56, 100));
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTitle.setText("GWBC Review Song Details");

        jScrollPane1.setForeground(new java.awt.Color(218, 227, 243));

        tblSong.setBackground(new java.awt.Color(218, 227, 243));
        tblSong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SongID", "Title", "Artist", "Year", "Key", "Number of Tags", "Number of Uses", "Last Used"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSong.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tblSong);

        bExit.setBackground(new java.awt.Color(180, 199, 231));
        bExit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bExit.setForeground(new java.awt.Color(31, 56, 100));
        bExit.setText("Exit to Review Songs");
        bExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExitActionPerformed(evt);
            }
        });

        lLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OrderOfService/GWBC Logo.png"))); // NOI18N

        tblTags.setBackground(new java.awt.Color(218, 227, 243));
        tblTags.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TagID", "Tag Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblTags);

        lTags.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lTags.setForeground(new java.awt.Color(31, 56, 100));
        lTags.setText("Tags:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lLogo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lTags)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bExit)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lTags)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 27, Short.MAX_VALUE)
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
     * Action Listener that creates a new ReviewSongs form and disposes this form
     * @param evt 
     */
    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExitActionPerformed
        //Create new ReviewSongs form and set it visible
        ReviewSongs reviewSongs = new ReviewSongs();
        reviewSongs.setVisible(true);
        
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
            java.util.logging.Logger.getLogger(ReviewSongDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReviewSongDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReviewSongDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReviewSongDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReviewSongDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bExit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lLogo;
    private javax.swing.JLabel lTags;
    private javax.swing.JLabel lTitle;
    private javax.swing.JTable tblSong;
    private javax.swing.JTable tblTags;
    // End of variables declaration//GEN-END:variables
}
