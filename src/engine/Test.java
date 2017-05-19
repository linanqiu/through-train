package engine;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import players.Player;
import players.PlayerDummy;

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

			Player player1 = new PlayerDummy("player1");
			Player player2 = new PlayerDummy("player2");
			StateManager.setupPlayer(state, player1);
			StateManager.setupPlayer(state, player2);
			System.out.println("players");
			System.out.println(state.toStringPlayers());
			System.out.println("state");
			System.out.println(state);
			System.out.println();
			
			List<Player> players = new LinkedList<>();
			players.add(player1);
			players.add(player2);

			StateManager.turnCycle(state, players);

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
