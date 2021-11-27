/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderOfService;

/**
 * The main class of the application
 * 
 * @author nathan deverill
 */
public class OrderOfServiceMain {
    
    
    
    
    /**
     * Main Method for launching the application 
     */
    
    // Multiple database objects needed for nesting database work when trying
    // to coordinate multiple drop down boxes on state change events
    public static DBObject dbObject;     //For total life of application
    public static DBObject dbObject2;    //Another database worker
    public static DBObject dbObject3;    //Another database worker
    
    public static void main(String[] args) {
        dbObject = new DBObject();
        dbObject2 = new DBObject();
        dbObject3 = new DBObject();
        
        
        if((dbObject.ErrorCode == 0) && (dbObject2.ErrorCode == 0)) {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
            mainMenu.setLocationRelativeTo(null);

        }
        else {
            //Needs to exit the program
            UserMessageBox MyMessage = new UserMessageBox ();
            MyMessage.setMessage ("Severe Error: Please Close Program.");
            MyMessage.setVisible (true);
        }
    }
}
