package engine;

import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		try {
			State state = new State();
			StateManager.readData(state, "/Users/linanqiu/Desktop/through-train/");

			System.out.println("players");
			System.out.println(state.toStringPlayers());
			System.out.println("state");
			System.out.println(state);
			System.out.println();

			// Player player1 = new Player("player1");
			// Player player2 = new Player("player2");
			// StateManager.setupPlayer(state, player1);
			// System.out.println("players");
			// System.out.println(state.toStringPlayers());
			// System.out.println("state");
			// System.out.println(state);
			// System.out.println();
			//
			// StateManager.setupPlayer(state, player2);
			// System.out.println("players");
			// System.out.println(state.toStringPlayers());
			// System.out.println("state");
			// System.out.println(state);
			// System.out.println();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
