import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Tasks {

	private static String taskName;
	private int taskId;

	//getting the task description from the user
	public Tasks(User user) {
		try {
			BufferedReader reader = InputController.getInputStream();
			System.out.println("Please enter the task.");
			this.taskName = reader.readLine();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.addNewTask(user);
	}

	//adding the task to database
	public void addNewTask(User user) {
		try {
			PreparedStatement st = DBConnection.getConnection()
					.prepareStatement(new StringBuilder("insert into user_tasks (user_id, task_name) values('").append(user.id).append("', '").append(taskName).append("');").toString());		
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("new task has been succesfully added.");
	}
	
	//calls the list of tasks from the user
	public static void getTaskList(User user) {
		java.sql.Statement st;
		try {
			st = DBConnection.getConnection().createStatement();
			StringBuilder query = new StringBuilder("select task_id, task_name, task_status, date_edited\r\n"
					+ "from\r\n"
					+ "user_tasks\r\n"
					+ "where user_id = ").append(user.id).append(";");
			ResultSet result = st.executeQuery(query.toString());
			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			System.out.println("*************");
			while(result.next()) {
				for(int i = 1; i <= columnCount; i++) {
					System.out.println(new StringBuilder(metaData.getColumnName(i)).append(" -> ").append(result.getString(i)).append("\t"));
				}
				System.out.println("*************");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//gets the id of a task from the database
	public void getTaskId(String taskName, User user) {
		try {
			Statement st = DBConnection.getConnection().createStatement();
			StringBuilder query = new StringBuilder("Select task_id from user_tasks\r\n"
					+ "where task_name =").append(taskName).append("and user_id =").append(user.id).append(";");
			ResultSet result = st.executeQuery(query.toString());
			int id = result.findColumn("task_id");
			while (result.next()) {
				this.taskId = result.getInt(id);
			}
			
		}catch(SQLException e) {
			
		}
	}

	
	//changes the task status to completed
	public static void completeTask(User user, int taskId) {
		try {
			
			PreparedStatement st = DBConnection.getConnection().prepareStatement(new StringBuilder("Update user_tasks set task_status = \"in progress\" "
					+ "where user_id = ").append(user.id).append(" and task_id = ").append(taskId).append(";").toString());
			st.executeUpdate();
			System.out.println("The task has been succesfully completed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//deletes a task by its id
	public static void deleteTask(User user, int id) {
		try {
		PreparedStatement st = DBConnection.getConnection().prepareStatement(new StringBuilder("Delete * from user_tasks where task_id = ").append(id).append("and user_id = ").append(user.id).toString());
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	


}
