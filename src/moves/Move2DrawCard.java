package moves;

import engine.Track.TrackType;

public class Move2DrawCard extends Move2 {
	public TrackType trackType;
	public boolean fromPile;

	// draw closed
	Move2DrawCard() {
		fromPile = true;
	}

	// draw open
	Move2DrawCard(TrackType trackType) {
		this.trackType = trackType;
		fromPile = false;
	}

	public Move2Result accept(Move2Visitor v, Move1Result prevMoveResult) {
		return v.visit(this, prevMoveResult);
	}
	
	@Override
	public String toString() {
		return fromPile ? "DrawCard1FromPile" : ("DrawCard1=" + trackType);
	}
}