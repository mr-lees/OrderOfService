/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderOfService;

/**
 *
 * @author natha
 */
public class AddTag extends javax.swing.JFrame {

    /**
     * Creates new form AddTag
     */
    public AddTag() {
        initComponents();
    }
    
    
    /**
     * Class that adds the tag to the database
     */
    public void AddTagToDatabase() {
        boolean error = false;
        int thisError = 0;
        String SQLStatement = "";
        
        //Check to see if the user has given an input for the TagDesc text field and if it fits the length
        if(tfTagDesc.getText().length() <= 0 || tfTagDesc.getText().length() > 70) {
            error = true;
        }
        
        if(error == false) {
            //Make the correct SQL statement
            SQLStatement = "insert into `Tags` (`TagDescription`) values ('" + tfTagDesc.getText() + "')";
            
            System.out.println(SQLStatement);
            
            //Add the Tag to the database
            OrderOfServiceMain.dbObject.sqlString = SQLStatement;
            OrderOfServiceMain.dbObject.insertRecord();
            
            if(thisError == 0) {
                UserMessageBox myMessage = new UserMessageBox();
                myMessage.setTitle("Success!");
                myMessage.setMessage("The Tag has been successfully added to the database!");
                myMessage.setVisible(true);
            }
        }
        else {
            //Inform the user of an input error
            UserMessageBox myMessage = new UserMessageBox();
                myMessage.setTitle("Error!");
                myMessage.setMessage("There has been an error with the inputs. \n"
                        + "Please make sure that you have: \n"
                        + "1. Entered a description \n"
                        + "2. The description is not more than 70 characters long");
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
        bSubmit = new javax.swing.JButton();
        lLogo = new javax.swing.JLabel();
        lTagDesc = new javax.swing.JLabel();
        tfTagDesc = new javax.swing.JTextField();
        bExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GWBC Order of Service Manager - Add Tag");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(218, 227, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));

        lTitle.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lTitle.setForeground(new java.awt.Color(31, 56, 100));
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTitle.setText("GWBC Add Tag");

        bSubmit.setBackground(new java.awt.Color(180, 199, 231));
        bSubmit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bSubmit.setForeground(new java.awt.Color(31, 56, 100));
        bSubmit.setText("Submit Tag");
        bSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubmitActionPerformed(evt);
            }
        });

        lLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OrderOfService/GWBC Logo.png"))); // NOI18N

        lTagDesc.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lTagDesc.setForeground(new java.awt.Color(31, 56, 100));
        lTagDesc.setText("Enter Tag Description:");

        bExit.setBackground(new java.awt.Color(180, 199, 231));
        bExit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bExit.setForeground(new java.awt.Color(31, 56, 100));
        bExit.setText("Exit to Menu");
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lLogo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lTagDesc)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(tfTagDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bExit, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTagDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lTagDesc))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lLogo)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action Listener for exit button
     * Disposes the form, but doesn't stop the main application
     * @param evt 
     */
    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExitActionPerformed
        dispose();
    }//GEN-LAST:event_bExitActionPerformed

    /**
     * Action Listener for Submit button
     * Runs the AddTagToDatabase method
     * @param evt 
     */
    private void bSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSubmitActionPerformed
        AddTagToDatabase();
    }//GEN-LAST:event_bSubmitActionPerformed

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
            java.util.logging.Logger.getLogger(AddTag.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddTag.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddTag.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddTag.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddTag().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bExit;
    private javax.swing.JButton bSubmit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lLogo;
    private javax.swing.JLabel lTagDesc;
    private javax.swing.JLabel lTitle;
    private javax.swing.JTextField tfTagDesc;
    // End of variables declaration//GEN-END:variables
}
