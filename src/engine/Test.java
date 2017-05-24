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

			List<Player> players = new LinkedList<>();
			for (int i = 0; i < 4; i++) {
				Player player = new PlayerDummy(String.format("Player %d", i));
				players.add(player);
				StateManager.setupPlayer(state, player);
			}

			for (int i = 0; i < 500; i++) {
				System.out.println(String.format("Turn %d", i));
				if (StateManager.turnCycle(state, players)) {
					System.out.println("Game ended");
					break;
				}
			}

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
