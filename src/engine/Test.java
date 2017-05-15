package engine;

import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		try {
			State state = new State();
			StateManager.readData(state, "/Users/linanqiu/Desktop/through-train/");
			System.out.println(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
