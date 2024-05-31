import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputController {
	static BufferedReader reader;

	//getter
	public static BufferedReader getInputStream() {
		return reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public static void closeInputStream() {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			reader = null;
			System.out.println("Input stream closed.");
		}
	}

}
