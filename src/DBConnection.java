import java.sql.*;

public class DBConnection {

	//Connection values
	private static Connection con = null;
	private String url = "jdbc:mysql://127.0.0.1:3306/to_do_list";
	private String username = "root";
	private String password = "MySqlPass";

	//by instanciating the obj we are creating a connection
	public DBConnection() {
		this.createConnection();
	}

	//function for establishing the connection
	private void createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			System.out.println(con.getClientInfo());
			System.out.println("Connected!");
		} catch (SQLException e) {
			throw new Error("Error", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//static getter
	public static Connection getConnection() {
		return con;
	}

	public static void closeConnection() {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
			System.out.println("Connection with database closed");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
