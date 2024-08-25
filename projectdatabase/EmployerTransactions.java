package projectdatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployerTransactions {
    private Connection con;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    
    public ArrayList<Employer> bringEmployers(){
        ArrayList<Employer> output = new ArrayList<Employer>();
        try {
            statement = con.createStatement();
            String query = "Select * From employers";
            rs = statement.executeQuery(query);
            
            while(rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String surname = rs.getString("SURNAME");
                String department = rs.getString("DEPARTMENT");
                String salary = rs.getString("SALARY");
                output.add(new Employer(id, name, surname, department, salary));
            }
            return output;
            
        } catch (SQLException ex) {
            Logger.getLogger(EmployerTransactions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public void addEmployer(String name, String surname, String department, String salary){
        String query = "Insert Into employers (NAME, SURNAME, DEPARTMENT, SALARY) VALUES(?, ?, ?, ?)";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, salary);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployerTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateEmployer(int id, String newName, String newSurname, String newDepartment, String newSalary){
        String query = "Update employers set NAME = ?, SURNAME = ?, DEPARTMENT = ?, SALARY = ? where ID = ?";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newSurname);
            preparedStatement.setString(3, newDepartment);
            preparedStatement.setString(4, newSalary);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployerTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteEmployer(int id){
        String query = "Delete From employers where ID = ?";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployerTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean singIn(String username, String password){
        String query = "Select * From admins where USERNAME = ? and PASSWORD = ?";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(EmployerTransactions.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public EmployerTransactions() {
        String url = "jdbc:mysql://localhost:3306/company";
        try {
            con = DriverManager.getConnection(url, Database.username, Database.password);
            System.out.println("You have connected");
        } catch (SQLException ex) {
            System.out.println("An error occured");
        }
    }
    
    public static void main(String[] args) {
        EmployerTransactions et = new EmployerTransactions();
    }
    
}
