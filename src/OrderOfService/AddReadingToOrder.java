/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderOfService;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author natha
 */
public class AddReadingToOrder extends javax.swing.JFrame {

    /**
     * Creates new form AddReadingToOrder
     */
    public AddReadingToOrder() {
        initComponents();
        
        //Fill up the Chapter combo boxes
        for(int i = 1; i < 151; i++) {
            cbStartChapter.addItem(i);
            cbEndChapter.addItem(i);
        }
        
        //Fill up the Verse combo boxes
        for(int i = 1; i < 177; i++) {
            cbStartVerse.addItem(i);
            cbEndVerse.addItem(i);
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lHeading = new javax.swing.JLabel();
        lRStart = new javax.swing.JLabel();
        lREnd = new javax.swing.JLabel();
        cbStartBook = new javax.swing.JComboBox();
        cbStartChapter = new javax.swing.JComboBox();
        cbStartVerse = new javax.swing.JComboBox();
        cbEndBook = new javax.swing.JComboBox();
        cbEndChapter = new javax.swing.JComboBox();
        cbEndVerse = new javax.swing.JComboBox();
        bSubmit = new javax.swing.JButton();
        lLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GWBC Order of Service Manager - Create Service");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(218, 227, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));

        lHeading.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lHeading.setForeground(new java.awt.Color(31, 56, 100));
        lHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lHeading.setText("GWBC Create Service");

        lRStart.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        lRStart.setForeground(new java.awt.Color(31, 56, 100));
        lRStart.setText("Reading Start:");

        lREnd.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        lREnd.setForeground(new java.awt.Color(31, 56, 100));
        lREnd.setText("Reading End:");

        cbStartBook.setBackground(new java.awt.Color(142, 170, 219));
        cbStartBook.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbStartBook.setForeground(new java.awt.Color(31, 56, 100));
        cbStartBook.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Book", "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalm", "Proverbs", "Ecclesiastes", "Song of Songs", "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation" }));
        cbStartBook.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbStartBookItemStateChanged(evt);
            }
        });

        cbStartChapter.setBackground(new java.awt.Color(142, 170, 219));
        cbStartChapter.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbStartChapter.setForeground(new java.awt.Color(31, 56, 100));
        cbStartChapter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Chapter" }));
        cbStartChapter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbStartChapterItemStateChanged(evt);
            }
        });

        cbStartVerse.setBackground(new java.awt.Color(142, 170, 219));
        cbStartVerse.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbStartVerse.setForeground(new java.awt.Color(31, 56, 100));
        cbStartVerse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Verse" }));
        cbStartVerse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbStartVerseItemStateChanged(evt);
            }
        });

        cbEndBook.setBackground(new java.awt.Color(142, 170, 219));
        cbEndBook.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbEndBook.setForeground(new java.awt.Color(31, 56, 100));
        cbEndBook.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Book", "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalm", "Proverbs", "Ecclesiastes", "Song of Songs", "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation" }));

        cbEndChapter.setBackground(new java.awt.Color(142, 170, 219));
        cbEndChapter.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbEndChapter.setForeground(new java.awt.Color(31, 56, 100));
        cbEndChapter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Chapter" }));

        cbEndVerse.setBackground(new java.awt.Color(142, 170, 219));
        cbEndVerse.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbEndVerse.setForeground(new java.awt.Color(31, 56, 100));
        cbEndVerse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Verse" }));

        bSubmit.setBackground(new java.awt.Color(180, 199, 231));
        bSubmit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bSubmit.setForeground(new java.awt.Color(31, 56, 100));
        bSubmit.setText("Submit");
        bSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubmitActionPerformed(evt);
            }
        });

        lLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OrderOfService/GWBC Logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lHeading, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lRStart)
                            .addComponent(lREnd)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbStartBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbStartChapter, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbStartVerse, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbEndBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbEndChapter, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbEndVerse, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bSubmit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lLogo)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lRStart)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbStartChapter)
                        .addComponent(cbStartVerse))
                    .addComponent(cbStartBook))
                .addGap(18, 18, 18)
                .addComponent(lREnd)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbEndBook)
                    .addComponent(cbEndChapter)
                    .addComponent(cbEndVerse))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lLogo)
                    .addComponent(bSubmit))
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
     * Submits the Reading to the table in the CreateServiceManual form
     * @param evt 
     */
    private void bSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSubmitActionPerformed
       //Check that every combo box has a valid choice
        boolean valid = true;
        
        if(cbStartBook.getSelectedIndex() == 0) {
            valid = false;
        }
        if(cbStartChapter.getSelectedIndex() == 0) {
            valid = false;
        }
        if(cbStartVerse.getSelectedIndex() == 0) {
            valid = false;
        }
        if(cbEndBook.getSelectedIndex() == 0) {
            valid = false;
        }
        if(cbEndChapter.getSelectedIndex() == 0) {
            valid = false;
        }
        if(cbEndVerse.getSelectedIndex() == 0) {
            valid = false;
        }
        
        //If valid is false output a message box telling the user there is an error, otherwise submit
        if(valid == false) {
            UserMessageBox MyMessage = new UserMessageBox ();
            MyMessage.setMessage ("Unnacceptable reading: \n"
                    + "Must choose a book, verse and chapter for start and end. \n"
                    + "Reading has not been submitted.");
            MyMessage.setVisible (true);
        }
        else {
            String reading = cbStartBook.getSelectedItem().toString() + " " 
                    + cbStartChapter.getSelectedItem().toString() + ": " 
                    + cbStartVerse.getSelectedItem().toString() + " - "
                    + cbEndBook.getSelectedItem().toString() + " "
                    + cbEndChapter.getSelectedItem().toString() + ": "
                    + cbEndVerse.getSelectedItem().toString();
            
            //Return the current table model for the Order of Service table
            DefaultTableModel model = (DefaultTableModel) OrderOfService.CreateServiceManual.tblOrderOfService.getModel();
            int index = model.getRowCount();
            
            //Add a new row containing the Index, Item Type ("Reading") and the Title (the passage) and null since it is not a song to the model
            model.addRow(new Object[]{index, "Reading", reading, null});
            
            //Set the model of the Order of Service table to the updated version
            OrderOfService.CreateServiceManual.tblOrderOfService.setModel(model);
            
            //Dispose the AddReadingToOrderForm
            dispose();
        }
    }//GEN-LAST:event_bSubmitActionPerformed

    /**
     * Listener that mirrors the selected item to the end book combo box
     * @param evt 
     */
    private void cbStartBookItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbStartBookItemStateChanged
        String selectedBook = cbStartBook.getSelectedItem().toString();
        
        //Find the index where selectedBook is in the Reading End combo box
        int i = 0;
        boolean found = false;
        int index = 0;
        
        while(found == false && i < cbEndBook.getItemCount()) {
            
            if(cbEndBook.getItemAt(i).toString().equals(selectedBook)) {
                index = i;
                found = true;
            }
            
            i++;
        }
        
        //Set the Reading End to the right index
        cbEndBook.setSelectedIndex(index);
    }//GEN-LAST:event_cbStartBookItemStateChanged

    /**
     * Listener that mirrors the selected item to the end chapter combo box
     * @param evt 
     */
    private void cbStartChapterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbStartChapterItemStateChanged
        String selectedChapter = cbStartChapter.getSelectedItem().toString();
        
        
        //Find the index where selectedChapter is in the Reading End combo box
        int index = 0;
        boolean found = false;
        int i = 0;
        
        while(found == false && i < cbEndChapter.getItemCount()) {
            
            if(cbEndChapter.getItemAt(i).toString().equals(selectedChapter)) {
                index = i;
                found = true;
            }
            
            i++;
        }
        
        //Set the Reading End to the right index
        cbEndChapter.setSelectedIndex(index);
    }//GEN-LAST:event_cbStartChapterItemStateChanged

    /**
     * Listener that mirrors the selected item to the end verse combo box
     * @param evt 
     */
    private void cbStartVerseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbStartVerseItemStateChanged
        String selectedVerse = cbStartVerse.getSelectedItem().toString();
        
        
        //Find the index where selectedVerse is in the Reading End combo box
        int index = 0;
        boolean found = false;
        int i = 0;
        
        while(found == false && i < cbEndVerse.getItemCount()) {
            
            if(cbEndVerse.getItemAt(i).toString().equals(selectedVerse)) {
                index = i;
                found = true;
            }
            
            i++;
        }
        
        //Set the Reading End to the right index
        cbEndVerse.setSelectedIndex(index);
    }//GEN-LAST:event_cbStartVerseItemStateChanged

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
            java.util.logging.Logger.getLogger(AddReadingToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddReadingToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddReadingToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddReadingToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddReadingToOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bSubmit;
    private javax.swing.JComboBox cbEndBook;
    private javax.swing.JComboBox cbEndChapter;
    private javax.swing.JComboBox cbEndVerse;
    private javax.swing.JComboBox cbStartBook;
    private javax.swing.JComboBox cbStartChapter;
    private javax.swing.JComboBox cbStartVerse;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lHeading;
    private javax.swing.JLabel lLogo;
    private javax.swing.JLabel lREnd;
    private javax.swing.JLabel lRStart;
    // End of variables declaration//GEN-END:variables
}