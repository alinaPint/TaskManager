import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Menu {
	static DBConnection con = new DBConnection();
	static BufferedReader reader = InputController.getInputStream();
	static User user;
	static Tasks task;
	static boolean flag = false;
	
	public static void main(String[] args) throws IOException, SQLException {
		System.out.println("*** MENU *** \n"
				+ "Press 1 to log in \n"
				+ "Press 2 to sign up \n");
		int num = Integer.parseInt(reader.readLine());
		
		switch(num) {
		case 1:
			System.out.println("enter your username");
			String username = reader.readLine();
			System.out.println("Enter your password");
			String password = reader.readLine();
			user = new User(username, password);
			flag = true;
			while(flag) {
				Menu.userMenu();
			}
			break;
		case 2:
			new User();
			while(flag) {
				Menu.userMenu();
			}
			break;
			
		}
	}
	
	public static void userMenu() {
		try {
		System.out.println("Press 1 to create a new task \n"
				+ "Press 2 to see the full list of your tasks \n"
				+ "Press 3 to complete a task \n"
				+ "Press 4 to delete a task \n"
				+ "Press 5 to log out\n"
				+ "Press 6 to delete your account");
		int num = Integer.parseInt(reader.readLine());
		
			switch(num) {
			case 1: 
				new Tasks(user);
				break;
			case 2:
				Tasks.getTaskList(user);
				break;
			case 3:
				System.out.println("Give the index of the task you want to complete");
				int taskNum = Integer.parseInt(reader.readLine());
				Tasks.completeTask(user, taskNum);
				break;
			case 4:
				System.out.println("Give the task id");
				int id = Integer.parseInt(reader.readLine());
				Tasks.deleteTask(user, num);
				break;
			case 5:
				flag = false;
				break;
			case 6:
				try {
					User.deleteUser(user);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
