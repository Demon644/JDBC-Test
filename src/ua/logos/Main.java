package ua.logos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	static Connection conn;
	
	public static void main(String[] args) throws SQLException {
		String dbUrl = "jdbc:mysql://localhost:3306/university?useSSL=false";
		String username = "root"; // root
		String password = "123456"; // 123456
		
		conn = DriverManager.getConnection(dbUrl, username, password);
		System.out.println("Connected? " + !conn.isClosed());
		
		createTable();
		addStudent();
		
		for (int i = 0; i< 30 ; i++) {
			addFacultys(i);
			addStudents(i);
		}
//		deleteStudent(5);
//		selectStudents();
		
		selectStudents();
		
		
		conn.close();
	}
	
	private static void createTable() throws SQLException {
		String dropQuery = "drop table if exists student;";
		String query = "CREATE TABLE student("
				+ "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"
				+ "full_name VARCHAR(60) NOT NULL,"
				+ "city VARCHAR(45) NOT NULL,"
				+ "age INT NOT NULL," 
				+ "faculty_id int"
				+ ");";
		String dropQueryFac = "drop table if exists faculty;";
		String queryfac = "create table faculty("
				+ "id int not null primary key auto_increment,"
				+ "name varchar(100) not null,"
				+ "short_name varchar(45) not null"
				+ ");";
		Statement stmt = conn.createStatement();
		stmt.execute(dropQuery);
		stmt.execute(dropQueryFac);
		stmt.execute(queryfac);
		stmt.execute(query);
		System.out.println("Table 'Faculty' created");
		System.out.println("Table 'Student' created");
		stmt.close();
	}
	
	private static void addStudent() throws SQLException {
		String query = "insert into student(full_name, city, age) values(?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, "John Doe");
		pstmt.setString(2, "Lviv");
		pstmt.setInt(3, 27);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	private static void selectStudents() throws SQLException {
		String query = "SELECT * FROM student;";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.println(
					"ID: " + rs.getInt("id") + "\t |" +
					"Full Name: " + rs.getString("full_name") + "\t |" +
					"City: " + rs.getString("city") + "\t |" +
					"Age: " + rs.getInt("age") + "\t |" +
					"Faculty: " + rs.getInt("faculty_id")
					);
		}
		
		pstmt.close();
		
	}
	
	private static void addStudents(int i) throws SQLException {
		String query = "insert into student(full_name, city, age) values(?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, "John Doe #" + i);
		pstmt.setString(2, "Lviv #" + i);
		pstmt.setInt(3, 27 + i);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
//	
//	private static void selectStudents(int s) throws SQLException {
//		String query = "SELECT * FROM student where id = ?;";
//		
//		PreparedStatement pstmt = conn.prepareStatement(query);
//		pstmt.setInt(1, s);
//		ResultSet rs = pstmt.executeQuery();
//		
//		while (rs.next()) {
//			System.out.println(
//					"ID: " + rs.getInt("id") + "\t |" +
//					"Full Name: " + rs.getString("full_name") + "\t |" +
//					"City: " + rs.getString("city") + "\t |" +
//					"Age: " + rs.getInt("age")
//					);
//		}
		
//		pstmt.close();
//		
//	}
	
	private static void deleteStudent(int d) throws SQLException {
		String query = "delete from student where id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, d);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	private static void addFacultys(int i) throws SQLException {
		String query = "insert into faculty(name, short_name) values(?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, "Faculty of computer engineering and computer programing #" + i);
		pstmt.setString(2, "FCP #" + i);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	private static void selectFacultys() throws SQLException {
		String query = "SELECT * FROM faculty;";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.println(
					"ID: " + rs.getInt("id") + "\t |" +
					"Name: " + rs.getString("name") + "\t |" +
					"Short Name: " + rs.getString("short_name")
					);
		}
		
		pstmt.close();
		
	}
	
}
