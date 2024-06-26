package sample;

public class DistanceFareCalculateService {
	// 基本料金
	private static final int BASE_FARE = 40;
	// 割増率
	//   通常時間帯
	private static final double STANDARD_FARE_RATE = 1.0;
	//   ピーク時間帯
	private static final double PEEK_FARE_RATE = 1.3;
	//   深夜時間帯
	private static final double LATE_TIME_FARE_RATE = 1.5;
	// TODO ここに初乗区間の値を定義すると影響範囲がわからなくなる
	// 初乗区間
	private static final int INITIAL_SECTOIN = 1000;
	// 課金区間単位
	//   短距離区間単位
	private static final int LONG_TRAVEL_SECTOIN_INTERVAL = 400;
	//   長距離区間単位
	private static final int SHORT_TRAVEL_SECTION_INTERVAL= 350;

	private DistanceFareCalculateService() {}

	public static double calculateFare(double totalDistanceM,  Record record) {
		if(totalDistanceM <= INITIAL_SECTOIN) {
			return 0;
		}

		double traveledDistanceM = record.getDistanceM();
		if(isCrossingInterval(totalDistanceM, traveledDistanceM)) {
			TimeZoneType type = record.getTimeZoneType();
			return BASE_FARE * getFareRate(type);
		}

		return 0;
	}

	private static boolean isCrossingInterval(double totalDistanceM, double traveledDistanceM) {
		if((1000 + SHORT_TRAVEL_SECTION_INTERVAL) < totalDistanceM && totalDistanceM <= 10200) {
			return totalDistanceM % SHORT_TRAVEL_SECTION_INTERVAL <= traveledDistanceM;
		} else if(10200 + LONG_TRAVEL_SECTOIN_INTERVAL < totalDistanceM) {
			return totalDistanceM % LONG_TRAVEL_SECTOIN_INTERVAL <= traveledDistanceM;
		}
		return false;
	}

	private static double getFareRate(TimeZoneType type) {
		switch (type) {
		case STANDARD:
			return STANDARD_FARE_RATE;
		case LATE_TIME:
			return LATE_TIME_FARE_RATE;
		case PEEK:
			return PEEK_FARE_RATE;
		default:
			throw new RuntimeException();
		}
	}

}
