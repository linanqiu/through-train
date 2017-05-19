package moves;

import engine.Track.TrackType;

public class Move1DrawCard extends Move1 {

	public TrackType trackType;
	public boolean fromPile;

	// draw closed
	Move1DrawCard() {
		fromPile = true;
	}

	// draw open
	Move1DrawCard(TrackType trackType) {
		this.trackType = trackType;
		fromPile = false;
	}

	public Move1Result accept(Move1Visitor v) {
		return v.visit(this);
	}
}