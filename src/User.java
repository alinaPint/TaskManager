import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.cj.xdevapi.Statement;

public class User {
	// variable declaration
	int id;
	String name;
	boolean isUserLogged= false;
	Pattern p = Pattern.compile("^[a-zA-z0-9._-]+[@][a-zA-Z.-_]*\\.[a-zA-Z]{2,4}$");
	
	
	// constructor used to sign up a new user 
	public User() {
		try {
			// get user data
			BufferedReader reader = InputController.getInputStream();
			System.out.println("Please enter your email as a username.");
			String username = reader.readLine();
			//proof if a valid email address is inserted
			Matcher m = p.matcher(username);
			boolean flag = false;
			while(!flag) {
				System.out.println("invalid username, please use your email as a username.");
				username = reader.readLine();
				flag = m.matches();
				
			}

			System.out.println("Please enter a password for your account");
			String password = reader.readLine();
			createUserAccount(this, password);
			this.name = username;
			getUserId(username, password);
			this.isUserLogged = true;
			

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	//constructor for logging in
	public User(String username, String password) {
		try {
			getUserId(username, password);
			if(id !=0) {
			this.name = username;
			this.isUserLogged = true;
			System.out.println("You habe been logged.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// adding the user to database
	private void createUserAccount(User user, String pass) throws SQLException {
		PreparedStatement st = DBConnection.getConnection().prepareStatement(new StringBuilder("INSERT INTO user_(user_name, pass_word) Values('")
				.append(user.name).append("', '").append(pass)
				.append("');").toString());
		st.executeUpdate();

		System.out.println("new User has been succesfully registered.");
	}


	//deleting function
	public static void deleteUser(User user) throws SQLException {
		if(user.isUserLogged) {
			PreparedStatement st = DBConnection.getConnection().prepareStatement(
					new StringBuilder("delete from user_ where user_id = '").append(user.id).append("';").toString());
			st.executeUpdate();
		}
		System.out.println("The user has been succesfully deleted!");
	}

	//getting a user id from the database based on the user name and password 
	//is the verify user function redundant ?
	private void getUserId(String username, String password) throws SQLException {
		Integer userId = null;
		try {
		java.sql.Statement st;
		
		st = DBConnection.getConnection().createStatement();
		StringBuilder query = new StringBuilder("select user_id from user_ where user_name = '").append(username)
				.append("' and pass_word = '").append(password).append("';");
		ResultSet result = st.executeQuery(query.toString());
		Integer id = result.findColumn("user_id");
		while (result.next()) {
			userId = result.getInt(id);
		}
		this.id = userId;
		
		}catch(NullPointerException e) {
			System.out.println("The password or the username is faulse.");
		}
	}

}
