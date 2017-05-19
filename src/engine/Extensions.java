package engine;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import engine.Track.TrackType;

public class Extensions {

	public static Random random = new Random();

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

	public static <T> int countMap(Map<T, Integer> map) {
		int sum = map.values().stream().collect(Collectors.summingInt(Integer::intValue));
		return sum;
	}

	public static <T> T randomDraw(Map<T, Integer> map) {
		int sum = countMap(map);
		int draw = random.nextInt(sum);

		int i = 0;

		for (Entry<T, Integer> entry : map.entrySet()) {
			i += entry.getValue();

			if (draw < i) {
				return entry.getKey();
			}
		}
		throw new UnsupportedOperationException("Random draw fucked up");
	}

	public static <T> void increment(Map<T, Integer> map, T key, int count) {
		map.put(key, map.get(key) + count);
	}

	public static <T> void decrement(Map<T, Integer> map, T key, int count) {
		map.put(key, map.get(key) - count);
	}

	public static <T> void transfer(Map<T, Integer> fromMap, Map<T, Integer> toMap, T key, int count) {
		decrement(fromMap, key, count);
		increment(toMap, key, count);
	}

	public static <T> void transfer(Map<T, Integer> fromMap, Map<T, Integer> toMap, T key) {
		transfer(fromMap, toMap, key, 1);
	}
}
