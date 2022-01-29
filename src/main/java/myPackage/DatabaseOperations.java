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
        System.out.println("el2o");
        int a;
        try {
            pst = con.prepareStatement("SELECT * FROM equipment");
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
                    v2.add(rs.getString("ID"));
                    v2.add(rs.getString("Name"));
                    v2.add(rs.getString("Type"));
                    v2.add(rs.getString("Tekst"));
                    
                }
                df.addRow(v2);
                System.out.println("elo");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
   
      
    
}

