package players;

import java.util.ArrayList;
import java.util.Random;

import engine.State;
import engine.ValidMoves;
import moves.Move1;
import moves.Move1Result;
import moves.Move2;

public class PlayerDummy extends Player {

	private Random random = new Random();

	public PlayerDummy(String name) {
		super(name);
	}

	@Override
	public Move1 getMove1(State state) {
		if (random.nextDouble() < 0.5) {
			return Move1.drawFromPile();
		}
		ArrayList<Move1> moves = new ArrayList<>(ValidMoves.calculateValidMove1s(state, this));
		int choice = random.nextInt(moves.size());
		Move1 move = moves.get(choice);
		return move;
	}

	@Override
	public Move2 getMove2(State state, Move1Result prevMoveResult) {
		if (random.nextDouble() < 0.5) {
			return Move2.drawFromPile();
		}
		ArrayList<Move2> moves = new ArrayList<>(ValidMoves.calculateValidMove2s(state, this, prevMoveResult));
		int choice = random.nextInt(moves.size());
		Move2 move = moves.get(choice);
		return move;
	}
}
