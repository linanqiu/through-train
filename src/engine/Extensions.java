package engine;

public class Extensions {
	public static TrackType toTrackType(String str) {
		str = str.toUpperCase().trim();
		TrackType type = TrackType.EMPTY;
		switch (str) {
		case "EMPTY":
			type = TrackType.EMPTY;
			break;
		case "WHITE":
			type = TrackType.WHITE;
			break;
		case "BLUE":
			type = TrackType.BLUE;
			break;
		case "PINK":
			type = TrackType.PINK;
			break;
		case "GREEN":
			type = TrackType.GREEN;
			break;
		case "YELLOW":
			type = TrackType.YELLOW;
			break;
		case "RED":
			type = TrackType.RED;
			break;
		case "BLACK":
			type = TrackType.BLACK;
			break;
		case "ORANGE":
			type = TrackType.ORANGE;
			break;
		default:
			type = TrackType.EMPTY;
			break;
		}
		return type;
	}
}
