package myPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class DatabaseOperations {
    
    public DatabaseOperations()
    {
        Connect();
    }
    
    Connection con;
    PreparedStatement pst;
    DefaultTableModel df;
    
    
    private void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/equ", "root", "");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableData(JTable jTable1)
    {
        int a;
        try {
            pst = con.prepareStatement("SELECT * FROM equipment order by id asc");
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel)jTable1.getModel();
            df.setRowCount(0);
            while(rs.next())
            {
                Vector v2 = new Vector();
                for(int i = 1; i <= a; i++)
                {
                    v2.add(rs.getString("id"));
                    v2.add(rs.getString("name"));
                    v2.add(rs.getString("type"));
                    v2.add(rs.getString("descr"));
                    
                }
                df.addRow(v2);
               
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addItemToDatabase(String itemID, String itemName, String itemType, String itemDescribtion)
    {
        try {
            pst = con.prepareStatement("insert into equipment(id, name, type, descr) values(?, ?, ?, ?)");
            pst.setString(1, itemID);
            pst.setString(2, itemName);
            pst.setString(3, itemType);
            pst.setString(4, itemDescribtion);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editItemFromDatabase(String itemID, String itemName, String itemType, String itemDescribtion, String oldID)
    {
        try {
           
            
            pst = con.prepareStatement("update equipment set id = ?, name = ?, type = ?, descr = ? where id = ?");
            pst.setString(1, itemID);
            pst.setString(2, itemName);
            pst.setString(3, itemType);
            pst.setString(4, itemDescribtion);
            pst.setString(5, oldID);
            pst.executeUpdate();
            
          
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void removeItemFromDatabase(String itemID)
    {
        try {
            pst = con.prepareStatement("DELETE FROM equipment WHERE id = ?");
            pst.setString(1, itemID);
            pst.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
   
      
 
}

