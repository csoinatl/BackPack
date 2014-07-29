package Project3;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 3: CityInfo representation of a city node
 * Daniel Kerr and Charles So
 * Date: 08/01/2014
 * File: CityInfo.java
 */
public class CityInfo {

	private String cityName;
	private String cityCode;

        //Convenience enum to keep track of what city a particular node is
	public static enum CitySet {
		ATL, CDG, CPT, DUB, FRA, HYD, JFK, MSY, PEK, SFO, SYD, TOY, ZRH;

		private static final Map<Integer, CitySet> lookup = new HashMap<Integer, CitySet>();
		
		static {
			int ordinal = 0;
			for (CitySet curSet : EnumSet.allOf(CitySet.class)) {
				lookup.put(ordinal, curSet);
				ordinal += 1;
			}
		}
		
		public static CitySet fromOrdinal(int ordinal) {
			return lookup.get(ordinal);
		}

                // We use this to check for city Identity
		public static int getCityOrdinal(String cityName) {
			if (cityName.length() > 3)
				switch (cityName.toUpperCase()) {
				case "ATLANTA":
					return ATL.ordinal();
				case "PARIS":
					return CDG.ordinal();
				case "CAPE TOWN":
					return CPT.ordinal();
				case "DUBAI":
					return DUB.ordinal();
				case "FRANKFURT":
					return FRA.ordinal();
				case "HYDERABAD":
					return HYD.ordinal();
				case "NEW YORK":
					return JFK.ordinal();
				case "NEW ORLEANS":
					return MSY.ordinal();
				case "BEIJING":
					return PEK.ordinal();
				case "SAN FRANCISCO":
					return SFO.ordinal();
				case "SYDNEY":
					return SYD.ordinal();
				case "TOKYO":
					return TOY.ordinal();
				case "ZURICH":
					return ZRH.ordinal();
				default:
					return -1;
				}
			else
				switch (cityName.toUpperCase()) {
				case "DWC":
					return DUB.ordinal();
				case "DXB":
					return DUB.ordinal();
				case "XNB":
					return DUB.ordinal();
				case "NRT":
					return TOY.ordinal();
				case "HND":
					return TOY.ordinal();
				default:
					return CitySet.valueOf(cityName.toUpperCase()).ordinal();
				}
			
		}
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(int cityOrdinal) {
		switch(cityOrdinal){
                    case 0:
                        cityName = "Atlanta";
                        break;
                    case 1:
                        cityName = "Paris";
                        break;
                    case 2:
                        cityName = "Cape Town";
                        break;
                    case 3:
                        cityName = "Dubai";
                        break;
                    case 4:
                        cityName = "Frankfurt";
                        break;
                    case 5:
                        cityName = "Hyderabad";
                        break;
                    case 6:
                        cityName = "New York";
                        break;
                    case 7:
                        cityName = "New Orleans";
                        break;
                    case 8:
                        cityName = "Beijing";
                        break;
                    case 9:
                        cityName = "San Francisco";
                        break;
                    case 10:
                        cityName = "Sydney";
                        break;
                    case 11:
                        cityName = "Tokyo";
                        break;
                    case 12:
                        cityName = "Zurich";
                        break;
                }
		
		setCityCode(CitySet.fromOrdinal(cityOrdinal).toString());
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

}
