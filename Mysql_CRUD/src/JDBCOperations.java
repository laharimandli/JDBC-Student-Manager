import java.sql.*;
import java.util.*;
public class JDBCOperations {

       static final String dbURL = "jdbc:mysql://localhost:3306/jdbccrud";
       static final String user="root";
       static final  String password="000000";

       public static void main(String[] args){

           try{
               Connection con = DriverManager.getConnection(dbURL,user,password);
               Scanner sc = new Scanner(System.in);

               System.out.println("Connecting to Database!");

               while(true){
                   System.out.println("\n--- MENU ---");
                   System.out.println("1. Create Table");
                   System.out.println("2. INSERT Record");
                   System.out.println("3. READ Records");
                   System.out.println("4. UPDATE Record");
                   System.out.println("5. DELETE Record");
                   System.out.println("6. EXIT");
                   System.out.println("Enter Choice: ");

                   int choice = sc.nextInt();
                   sc.nextLine();

                   switch (choice){
                       case 1:
                           createTable(con);
                           break;
                       case 2:
                           insertRecord(con,sc);
                           break;
                       case 3:
                           readRecords(con);
                           break;
                       case 4:
                           updateRecord(con,sc);
                           break;
                       case 5:
                           deleteRecord(con,sc);
                           break;
                       case 6:
                           System.out.println("Exiting...");
                           return;
                       default:
                           System.out.println("Invalid choice!");
                   }

               }

           }catch (SQLException e){
               e.printStackTrace();
           }
       }

       private static void createTable(Connection conn) throws SQLException{
           String sql = "CREATE TABLE IF NOT EXISTS student (" +
                   "id INT AUTO_INCREMENT PRIMARY KEY,"+
                    "name VARCHAR(50),"+
                     "branch VARCHAR(50))";
           Statement stmt = conn.createStatement();
           stmt.execute(sql);
           System.out.println("Table created succesfully.");

       }


       private static void insertRecord(Connection con,Scanner sc) throws SQLException{
           String sql ="INSERT INTO student(name,branch) VALUES(?,?)";
           try (PreparedStatement pstmt = con.prepareStatement(sql)) {
               System.out.print("Enter Name: ");
               String name = sc.nextLine();
               System.out.print("Enter Branch: ");
               String branch = sc.nextLine();

               pstmt.setString(1, name);
               pstmt.setString(2, branch);

               int rows = pstmt.executeUpdate();
               System.out.println(rows + " row(s) inserted.");
           }
       }

    private static void readRecords(Connection conn) throws SQLException {
        String sql = "SELECT * FROM student";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nID | Name | Branch");
            System.out.println("-------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("branch"));
            }
        }
    }

    private static void updateRecord(Connection conn, Scanner sc) throws SQLException {
        String sql = "UPDATE student SET branch = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.print("Enter ID to update: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Enter new Branch: ");
            String newBranch = sc.nextLine();

            pstmt.setString(1, newBranch);
            pstmt.setInt(2, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Update successful.");
            else System.out.println("ID not found.");
        }
    }

    private static void deleteRecord(Connection conn, Scanner sc) throws SQLException {
        String sql = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.print("Enter ID to delete: ");
            int id = sc.nextInt();

            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Delete successful.");
            else System.out.println("ID not found.");
        }
    }




}
