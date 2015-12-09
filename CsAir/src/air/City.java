package air;
import java.util.ArrayList;

public class City {
	String code;
	String name;
	String country;
	String continent;
	Number timezone;
	String coordinates;
	int population;
	int region;
	ArrayList<Flight> flightList;

	public City(String code, String name, String country, String continent,
			Number timezone, String coordinates, int population, int region) {
		this.code = code;
		this.name = name;
		this.country = country;
		this.continent = continent;
		this.timezone = timezone;
		this.coordinates = coordinates;
		this.population = population;
		this.region = region;
		this.flightList = new ArrayList<Flight>();
	}

	public void addFlights(Flight flight) {
		flightList.add(flight);
	}

	public void removeFlightBySourceCode(String code) {
		for (int i = flightList.size() - 1; i >= 0; i--) {
			if (flightList.get(i).source.getCode().equals(code))
				flightList.remove(i);
		}
	}

	public void removeFlightByDestinationCode(String code) {
		for (int i = flightList.size() - 1; i >= 0; i--) {
			if (flightList.get(i).destination.getCode().equals(code))
				flightList.remove(i);
		}
	}

	public ArrayList<Flight> getFlightList() {
		return flightList;
	}

	public City(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public String getContinent() {
		return continent;
	}

	public Number getTimezone() {
		return timezone;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public int getPopulation() {
		return population;
	}

	public int getRegion() {
		return region;
	}

	@Override
	public String toString() {
		return "{" + "\n\t\t\"code\": \"" + code + "\",\n" + "\t\t\"name\": \""
				+ name + "\",\n" + "\t\t\"country\": \"" + country + "\",\n"
				+ "\t\t\"continent\": \"" + continent + "\",\n"
				+ "\t\t\"timezone\":" + timezone + ",\n"
				+ "\t\t\"coordinates\": " + coordinates + ",\n"
				+ "\t\t\"population\": " + population + ",\n"
				+ "\t\t\"region\": " + region + "\n\t}";
	}
}
