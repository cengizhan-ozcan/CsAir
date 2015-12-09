package air;
// author : Cengizhan Ozcan 

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class CsAir {
	public static JSONObject csAirData;
	public static ArrayList<City> cities = new ArrayList<City>();
	public static ArrayList<Flight> allFlights = new ArrayList<Flight>();
	public static ArrayList<City> allCities;
	public static ArrayList<Flight> flights;
	public static ArrayList<String> datas;
	public static ArrayList<String> schedules;
	public static ArrayList<String> scheduledSource;
	public static ArrayList<String> scheduledDestination;
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		readFile();
		addCitiesFromCsairData();
		addFlightsFromCsairData();
		addDataSourceFromCsairData();
		createConsole();
	}

	public static void readFile() throws FileNotFoundException {
		FileReader reader = new FileReader("csair.json");
		csAirData = (JSONObject) JSONValue.parse(reader);
	}

	public static void addCitiesFromCsairData() {
		JSONArray metros = (JSONArray) csAirData.get("metros");
		allCities = new ArrayList<City>();
		for (int i = 0; i < metros.size(); i++) {
			JSONObject metro = (JSONObject) metros.get(i);
			String code = (String) metro.get("code");
			String name = (String) metro.get("name");
			String country = (String) metro.get("country");
			String continent = (String) metro.get("continent");
			Number timezone = ((Number) metro.get("timezone"));
			String coordinates = (String) metro.get("coordinates").toString();
			int population = ((Long) metro.get("population")).intValue();
			int region = ((Long) metro.get("region")).intValue();
			City city = new City(code, name, country, continent, timezone,
					coordinates, population, region);
			allCities.add(city);
		}
	}

	public static void addFlightsFromCsairData() {
		JSONArray routes = (JSONArray) csAirData.get("routes");
		flights = new ArrayList<Flight>();
		for (int i = 0; i < routes.size(); i++) {
			JSONObject route = (JSONObject) routes.get(i);
			JSONArray portCodes = (JSONArray) route.get("ports");
			City source = getCityFromCode(((String) portCodes.get(0)));
			City destination = getCityFromCode((String) portCodes.get(1));
			int distance = ((Long) route.get("distance")).intValue();
			Flight flight = new Flight(source, destination, distance); // A to B
			Flight oppositeFlight = new Flight(destination, source, distance); // B
																				// to
																				// A
			source.addFlights(flight);
			destination.addFlights(oppositeFlight);
			flights.add(flight);
			flights.add(oppositeFlight);
		}
		System.out.println("Welcome to CsAir!");
	}

	public static void addDataSourceFromCsairData() {
		JSONArray dataSources = (JSONArray) csAirData.get("data sources");
		datas = new ArrayList<String>();
		for (int i = 0; i < dataSources.size(); i++) {
			String data = (String) dataSources.get(i);
			datas.add(data);
		}

	}

	public static City getCityFromCode(String code) {
		for (int i = 0; i < allCities.size(); i++) {
			if (allCities.get(i).getCode().equalsIgnoreCase(code))
				return allCities.get(i);
		}
		return null;
	}

	public static void createConsole() {
		while (true) {
			try {
				Scanner newScanner = new Scanner(System.in);
				printOptions();
				int userInput = Integer.parseInt(newScanner.nextLine());
				if (userInput == 0)
					closeProgram();
				if (userInput == 1)
					getListOfCities();
				if (userInput == 2)
					getSpecificDataForCity();
				if (userInput == 3)
					getStatisticalData();
				if (userInput == 4)
					addCity();
				if (userInput == 5)
					addFlight();
				if (userInput == 6)
					removeCity();
				if (userInput == 7)
					removeFlight();
				if (userInput == 8)
					printJSONData();
				if (userInput == 9)
					getItinerary();
				if (userInput == 10)
					addNewSchedule();
				if (userInput == 11)
					showSchedule();
				if (userInput == 12)
					getDataSources();
				if (userInput == 13)
					showInfo();
				if (!(userInput <= 13 && userInput >= 0))
					System.out.println("\nPlease enter a number between [0,8]");
			} catch (NumberFormatException e) {
				System.out.println("\nPlease enter a integer!");
			}
		}
	}

	private static void showSchedule() {
		if (schedules == null) {
			System.out.println("-----");
			System.out.println("-----");
			System.out.println("There is no schedule to print.");
		} else if (schedules.size() != 0) {
			System.out.println("-----");
			System.out.println("-----");
			for (int i = 0; i < schedules.size(); i++) {
				System.out.print("Time:");
				System.out.println(schedules.get(i));
				System.out.print("Source:");
				System.out.println(scheduledSource.get(i));
				System.out.print("Time:");
				System.out.println(scheduledDestination.get(i));
			}
		}

	}

	private static void addNewSchedule() {
		boolean isWorkedForSource = false;
		boolean isWorkedForDestination = false;
		boolean forSource = true;
		boolean forDestination = true;
		String destination = "";
		String source = "";
		System.out.print("Please enter your schedule: ");
		Scanner scanner = new Scanner(System.in);
		String schedule = scanner.nextLine();
		schedules = new ArrayList<String>();
		schedules.add(schedule);
		while (!isWorkedForSource) {
			if (!forSource) {
				System.out.println("Please select existing source!!");
			}
			System.out.println("Please select your source:");
			source = scanner.nextLine();
			for (int i = 0; i < allCities.size(); i++) {
				if (!allCities.get(i).name.equals(source)) {
					isWorkedForSource = false;
					forSource = false;
				} else {
					isWorkedForSource = true;
					forSource = true;
					break;
				}
			}
		}
		scheduledSource = new ArrayList<String>();
		scheduledSource.add(source);
		source = scanner.nextLine();
		while (!isWorkedForDestination) {
			if (!forDestination) {
				System.out.println("Please select existing destination!!");
			}
			System.out.println("Please select your destination:");
			source = scanner.nextLine();
			for (int i = 0; i < allCities.size(); i++) {
				if (!allCities.get(i).name.equals(source)) {
					isWorkedForDestination = false;
					forDestination = false;
				} else {
					isWorkedForDestination = true;
					forDestination = true;
					break;
				}
			}
		}
		scheduledDestination = new ArrayList<String>();
		scheduledDestination.add(source);
		System.out.println("\n-------");
		System.out.println("\n-------");
		System.out.println("Schule:");
		for (int i = 0; i < schedules.size(); i++) {
			System.out.print("Time:");
			System.out.println(schedules.get(i));
			System.out.print("Source:");
			System.out.println(scheduledSource.get(i));
			System.out.print("Time:");
			System.out.println(scheduledDestination.get(i));
		}

	}

	private static void getDataSources() {
		System.out.println("--");
		for (int i = 0; i < datas.size(); i++) {
			System.out.println("" + datas.get(i));
		}

	}

	private static void showInfo() {
		System.out.println("--");
		System.out.println("This program was created by Cengizhan Özcan");
	}

	public static void closeProgram() {
		System.out.println("\nGood Bye!");
		System.exit(0);
	}

	public static void printOptions() {
		System.out.println("\n-----" + "\n-----");
		System.out.println("\n1. List of all the cities."
				+ "\n2. Get specific information about a specific"
				+ " city in the CSAir route network."
				+ "\n3. Statistical information about CSAir's route network."
				+ "\n4. Add new city." + "\n5. Add new flight."
				+ "\n6. Remove city." + "\n7. Remove flight."
				+ "\n8. Print JSON Data." + "\n9. Get itineraries."
				+ "\n10. Add new Schedule" + "\n11. Print Schedules"
				+ "\n12. Print Data Source." + "\n13. Get Info About Program"
				+ "\n(press 0 to exit)");
		System.out.print("Please select a number for information: ");
	}

	public static void getListOfCities() {
		System.out.println("\nList of all cities:");
		for (int i = 0; i < allCities.size(); i++) {
			System.out.println("-" + allCities.get(i).name + " ("
					+ allCities.get(i).code + ")");
		}
	}

	public static void getSpecificDataForCity() {
		System.out.print("Please enter a city code: ");
		String code = scanner.nextLine();
		boolean isWorked = false;
		for (int i = 0; i < allCities.size(); i++) {
			if (code.equalsIgnoreCase(allCities.get(i).code)) {
				printSpecificInformationsOfCity(i);
				code = allCities.get(i).code;
				isWorked = true;
			}
		}
		if (isWorked)
			System.out.println("--Flights--");
		for (int i = 0; i < flights.size(); i++) {
			if (code.equals(flights.get(i).source.getCode())) {
				printAllFlightsOfThisCity(i);
			}
		}
		if (!isWorked)
			System.out.println("\n'" + code + "'"
					+ " is not found in the list.");
	}

	public static void printSpecificInformationsOfCity(int i) {
		System.out.println("\nCode: " + allCities.get(i).code);
		System.out.println("Name: " + allCities.get(i).name);
		System.out.println("Country: " + allCities.get(i).country);
		System.out.println("Continent: " + allCities.get(i).continent);
		System.out.println("Timezone: " + allCities.get(i).timezone);
		System.out.println("Coordinates: " + allCities.get(i).coordinates);
		System.out.println("Population: " + allCities.get(i).population);
		System.out.println("Region: " + allCities.get(i).region + "\n");
	}

	public static void printAllFlightsOfThisCity(int i) {
		System.out.print(flights.get(i).source.getName());
		System.out.print(" -> ");
		System.out.println(flights.get(i).destination.getName());
		System.out.println(" -- ");
		System.out.println(flights.get(i).distance + " km.");
	}

	public static void getStatisticalData() {
		try {
			Scanner newScanner = new Scanner(System.in);
			printOptionOfStatisticalInformations();
			int statisticalInfo = newScanner.nextInt();
			if (statisticalInfo == 0)
				closeProgram();
			if (statisticalInfo == 1)
				findLongestFlight(allFlights);
			if (statisticalInfo == 2)
				findShortestFlight(allFlights);
			if (statisticalInfo == 3)
				findAverageDistance(allFlights);
			if (statisticalInfo == 4)
				findBiggestPopulationOfCities(cities);
			if (statisticalInfo == 5)
				findSmallestPopulationOfCities(cities);
			if (statisticalInfo == 6)
				findAveragePopulationOfCities(cities);
			if (statisticalInfo == 7)
				findListOfContinents();
			if (statisticalInfo == 8)
				identifyHubCities();
			if (!(statisticalInfo < 9 && statisticalInfo > 0))
				System.out.println("\nPlease enter a number between [0,8]");
		} catch (Exception e) {
			System.out.println("\nPlease enter a integer!");
		}
	}

	public static void printOptionOfStatisticalInformations() {
		System.out.print("\n-----");
		System.out.println("\n-----");
		System.out.println("\n1. The longest single flight in the network"
				+ "\n2. The shortest single flight in the network"
				+ "\n3. The average distance of all the flights in the network"
				+ "\n4. The biggest city (by population) served by CSAir"
				+ "\n5. The smallest city (by population) served by CSAir"
				+ "\n6. The average size (by population) of all "
				+ "the cities served by CSAir"
				+ "\n7. A list of the continents served by "
				+ "CSAir and which cities are in them"
				+ "\n8. Identifying CSAir's hub cities: the cities "
				+ "that have the most direct connections."
				+ "\n(press 0 to exit)");
		System.out.print("Please select a number for information: ");
	}

	public static int findLongestFlight(ArrayList<Flight> allFlights) {
		int largestValue = Integer.MIN_VALUE;
		int largestValueForTest = Integer.MIN_VALUE;
		String sourceAndDestination = "";
		for (int i = 0; i < flights.size(); i++) {
			if (flights.get(i).distance > largestValue) {
				largestValue = flights.get(i).distance;
				sourceAndDestination = flights.get(i).source.getName() + " to "
						+ flights.get(i).destination.getName();
			}
		}
		System.out.println("\nThe longest flight is " + largestValue
				+ " km from " + sourceAndDestination + " in the network.");
		for (int i = 0; i < allFlights.size(); i++) {
			if (allFlights.get(i).distance > largestValueForTest) {
				largestValueForTest = allFlights.get(i).distance;
			}
		}
		return largestValueForTest;
	}

	public static int findShortestFlight(ArrayList<Flight> allFlights) {
		int smallestValue = Integer.MAX_VALUE;
		int smallestValueForTest = Integer.MAX_VALUE;
		String sourceAndDestination = "";
		for (int i = 0; i < flights.size(); i++) {
			if (flights.get(i).distance < smallestValue) {
				smallestValue = flights.get(i).distance;
				sourceAndDestination = flights.get(i).source.getName() + " to "
						+ flights.get(i).destination.getName();
			}
		}
		System.out.println("\nThe shortest flight is " + smallestValue
				+ " km from " + sourceAndDestination + " in the network.");
		for (int i = 0; i < allFlights.size(); i++) {
			if (allFlights.get(i).distance < smallestValueForTest) {
				smallestValueForTest = allFlights.get(i).distance;
			}
		}
		return smallestValueForTest;
	}

	public static int findAverageDistance(ArrayList<Flight> allFlights) {
		int sumOfDistance = 0;
		int averageDistance = 0;
		int sumOfDistanceForTest = 0;
		int averageDistanceForTest = 0;
		for (int i = 0; i < flights.size(); i++) {
			sumOfDistance += flights.get(i).distance;
		}
		averageDistance = sumOfDistance / flights.size();
		System.out.println("\nThe average distance of all the flights is "
				+ averageDistance + " km in the network");
		for (int i = 0; i < allFlights.size(); i++) {
			sumOfDistanceForTest += allFlights.get(i).distance;
		}
		averageDistanceForTest = sumOfDistanceForTest / allFlights.size();
		return averageDistanceForTest;
	}

	public static int findBiggestPopulationOfCities(ArrayList<City> cities) {
		int biggestPopulation = Integer.MIN_VALUE;
		int biggestPopulationForTest = Integer.MIN_VALUE;
		String cityName = "";
		for (int i = 0; i < allCities.size(); i++) {
			if (allCities.get(i).population > biggestPopulation) {
				biggestPopulation = allCities.get(i).population;
				cityName = allCities.get(i).name;
			}
		}
		System.out.println("\nThe biggest city " + cityName + " has "
				+ biggestPopulation + " people (served by CSAir)");

		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i).population > biggestPopulationForTest) {
				biggestPopulationForTest = cities.get(i).population;
			}
		}
		return biggestPopulationForTest;
	}

	public static int findSmallestPopulationOfCities(ArrayList<City> cities) {
		int smallestPopulation = Integer.MAX_VALUE;
		int smallestPopulationForTest = Integer.MAX_VALUE;
		String cityName = "";
		for (int i = 0; i < allCities.size(); i++) {
			if (allCities.get(i).population < smallestPopulation) {
				smallestPopulation = allCities.get(i).population;
				cityName = allCities.get(i).name;
			}
		}
		System.out.println("\nThe smallest city " + cityName + " has "
				+ smallestPopulation + " people (served by CSAir)");
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i).population < smallestPopulationForTest) {
				smallestPopulationForTest = cities.get(i).population;
			}
		}
		return smallestPopulationForTest;
	}

	public static int findAveragePopulationOfCities(ArrayList<City> cities) {
		int sumOfPopulation = 0;
		int averagePopulation = 0;
		int sumOfPopulationForTest = 0;
		int averagePopulationForTest = 0;
		for (int i = 0; i < allCities.size(); i++) {
			sumOfPopulation += allCities.get(i).population;
		}
		averagePopulation = sumOfPopulation / allCities.size();
		System.out.println("\nThe average size (by population) "
				+ "of all the cities is " + averagePopulation
				+ " (served by CSAir)");
		for (int i = 0; i < cities.size(); i++) {
			sumOfPopulationForTest += cities.get(i).population;
		}
		averagePopulationForTest = sumOfPopulationForTest / cities.size();
		return averagePopulationForTest;
	}

	public static void findListOfContinents() {
		ArrayList<String> allContinents = new ArrayList<String>();
		for (int i = 0; i < allCities.size(); i++) {
			allContinents.add(allCities.get(i).continent);
		}
		Collections.sort(allContinents);
		removeDuplicateContinents(allContinents);
		printContinentsAndCities(allContinents);
	}

	public static void removeDuplicateContinents(ArrayList<String> allContinents) {
		for (int i = allContinents.size() - 1; i > 0; i--) {
			if (allContinents.get(i).equals(allContinents.get(i - 1))) {
				allContinents.remove(i);
			}
		}
	}

	public static void printContinentsAndCities(ArrayList<String> allContinents) {
		System.out.println("");
		for (int i = 0; i < allContinents.size(); i++) {
			System.out.println(allContinents.get(i) + ":");
			for (int j = 0; j < allCities.size(); j++) {
				if (allCities.get(j).continent.equals(allContinents.get(i))) {
					System.out.println("- " + allCities.get(j).name);
				}
			}
		}
	}

	public static void identifyHubCities() {
		ArrayList<String> sourceCities = new ArrayList<String>();
		for (int i = 0; i < flights.size(); i++) {
			sourceCities.add(flights.get(i).source.getCode());
		}
		Collections.sort(sourceCities);
		ArrayList<String> hubCities = getModeOfList(sourceCities);
		System.out.println("\nCSAir's hub cities:");
		for (int i = 0; i < hubCities.size(); i++) {
			System.out.println("-" + hubCities.get(i));
		}
	}

	// finds the value that appears most often in array of Flights
	public static ArrayList<String> getModeOfList(ArrayList<String> sourceCities) {
		int[] counts = new int[allCities.size()];
		String[] cities = new String[allCities.size()];
		for (int i = 0; i < allCities.size(); i++) {
			cities[i] = allCities.get(i).code;
		}
		Arrays.sort(cities);
		countAndSaveCityFlights(counts, sourceCities, cities);
		int biggestValue = getMostFlight(counts);
		ArrayList<String> hubCities = new ArrayList<String>();
		for (int i = 0; i < cities.length; i++) {
			if (biggestValue == counts[i]) {
				hubCities.add(cities[i]);
			}
		}
		return hubCities;
	}

	public static int getMostFlight(int[] counts) {
		int biggestValue = Integer.MIN_VALUE;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > biggestValue) {
				biggestValue = counts[i];
			}
		}
		return biggestValue;
	}

	// keeps a list of number of all city flights
	public static void countAndSaveCityFlights(int[] counts,
			ArrayList<String> sourceCities, String[] cities) {
		int count = 0;
		for (int i = 0; i < allCities.size(); i++) {
			for (int j = 0; j < sourceCities.size(); j++) {
				if (cities[i].equals(sourceCities.get(j))) {
					count++;
				}
			}
			counts[i] = count;
			count = 0;
		}
	}

	public static void addCity() {
		try {
			System.out.print("Code: ");
			String code = scanner.nextLine();
			checkCode(code);
			System.out.print("Name: ");
			String name = scanner.nextLine();
			System.out.print("Country: ");
			String country = scanner.nextLine();
			System.out.print("Continent: ");
			String continent = scanner.nextLine();
			System.out.print("Coordinates: ");
			String coordinates = scanner.nextLine();
			System.out.print("Population: ");
			int population = Integer.parseInt(scanner.nextLine());
			System.out.print("Timezone: ");
			Number timezone = Integer.parseInt(scanner.nextLine());
			System.out.print("Region: ");
			int region = Integer.parseInt(scanner.nextLine());
			City city = new City(code, name, country, continent, timezone,
					coordinates, population, region);
			allCities.add(city);
			addFlight(code);
			System.out.println("\n" + name + " is added!");
		} catch (Exception e) {
			System.out.println("Please enter a integer!!");
		}
	}

	public static void checkCode(String code) {
		for (int i = 0; i < allCities.size(); i++) {
			if (code.equalsIgnoreCase(allCities.get(i).code)) {
				System.out.println("\n'" + code + "' already exists.");
				createConsole();
			}
		}
	}

	public static void addFlight(String code) {
		City sourceCity = getCityFromCode(code);
		while (true) {
			System.out.println("---FLIGHTS---");
			System.out.print("0 -> exit - 1-> continue: ");
			int input = Integer.parseInt(scanner.nextLine());
			if (input == 0)
				return;
			if (input == 1) {
				System.out.print("Destination: ");
				String destination = scanner.nextLine();
				City destinationCity = getCityFromCode(destination);
				if (checkCityCode(destination))
					checkDuplicateFlight(sourceCity, destinationCity);
				System.out.print("Distance: ");
				int distance = Integer.parseInt(scanner.nextLine());
				Flight flight = new Flight(sourceCity, destinationCity,
						distance);
				Flight oppositeFlight = new Flight(destinationCity, sourceCity,
						distance);
				sourceCity.addFlights(flight);
				destinationCity.addFlights(oppositeFlight);
				flights.add(flight);
				flights.add(oppositeFlight);
			}
		}
	}

	public static void addFlight() {
		System.out.print("Source: ");
		String source = scanner.nextLine();
		City sourceCity = getCityFromCode(source);
		if (checkCityCode(source))
			System.out.print("Destination: ");
		String destination = scanner.nextLine();
		City destinationCity = getCityFromCode(destination);
		if (checkCityCode(destination)) {
			checkDuplicateFlight(sourceCity, destinationCity);
		}
		System.out.print("Distance: ");
		int distance = Integer.parseInt(scanner.nextLine());
		Flight flight = new Flight(sourceCity, destinationCity, distance);
		Flight oppositeFlight = new Flight(destinationCity, sourceCity,
				distance);
		sourceCity.addFlights(flight);
		destinationCity.addFlights(oppositeFlight);
		flights.add(flight);
		flights.add(oppositeFlight);
		System.out.println("\nThe flight from " + source + " to " + destination
				+ " is added!");

	}

	public static void checkDuplicateFlight(City sourceCity,
			City destinationCity) {
		for (int i = 0; i < flights.size(); i++) {
			if (sourceCity.equals(flights.get(i).getSource())
					&& destinationCity.equals(flights.get(i).getDestination())) {
				System.out.println("\nFlight from " + sourceCity.getName()
						+ " to " + destinationCity.getName()
						+ " already exists.");
				createConsole();
			}
		}
	}

	public static boolean checkCityCode(String cityCode) {
		for (int i = 0; i < allCities.size(); i++) {
			if (cityCode.equalsIgnoreCase(allCities.get(i).code)) {
				return true;
			}
		}
		System.out.println("\n'" + cityCode + "' is not found!");
		createConsole();
		return false;
	}

	public static void removeCity() {
		System.out.print("Code: ");
		String code = scanner.nextLine();
		if (isValidCode(code) == true) {
			for (int i = allCities.size() - 1; i >= 0; i--) {
				if (code.equalsIgnoreCase(allCities.get(i).code)) {
					allCities.remove(i);
				}
			}
			deleteFlights(code);
		} else {
			System.out.println("\n'" + code + "' is not found!");
			createConsole();
		}
	}

	public static void deleteFlights(String code) {
		for (int i = flights.size() - 1; i >= 0; i--) {
			if (code.equalsIgnoreCase(flights.get(i).source.getCode())
					|| code.equalsIgnoreCase(flights.get(i).destination
							.getCode())) {
				flights.get(i).getDestination()
						.removeFlightByDestinationCode(code);
				flights.get(i).getSource().removeFlightBySourceCode(code);
				flights.remove(i);
			}
		}
		System.out.print("\n" + code);
		System.out.println("is removed!");
	}

	public static boolean isValidCode(String code) {
		for (int i = 0; i < allCities.size(); i++) {
			if (code.equalsIgnoreCase(allCities.get(i).code)) {
				return true;
			}
		}
		return false;
	}

	public static void removeFlight() {
		System.out.print("Source: ");
		String sourceCode = scanner.nextLine();
		if (checkCityCode(sourceCode))
			System.out.print("Destination: ");
		String destinationCode = scanner.nextLine();
		if (checkCityCode(destinationCode))
			if (checkValidInputs(sourceCode, destinationCode) == true) {
				for (int i = flights.size() - 1; i >= 0; i--) {
					if ((sourceCode.equalsIgnoreCase(flights.get(i).source
							.getCode()) || sourceCode.equalsIgnoreCase(flights
							.get(i).destination.getCode()))
							&& (destinationCode
									.equalsIgnoreCase(flights.get(i).destination
											.getCode()) || destinationCode
									.equalsIgnoreCase(flights.get(i).source
											.getCode()))) {
						flights.get(i).getSource()
								.removeFlightBySourceCode(sourceCode);
						flights.get(i).getDestination()
								.removeFlightByDestinationCode(destinationCode);
						flights.remove(i);
					}
				}
				System.out.println("\nFlight is removed!!");
			}
	}

	public static boolean checkValidInputs(String source, String destination) {
		for (int i = 0; i < flights.size(); i++) {
			if (source.equalsIgnoreCase(flights.get(i).source.getCode())
					&& destination.equalsIgnoreCase(flights.get(i).destination
							.getCode())) {
				return true;
			}
		}
		System.out.println("\n'" + source + "' to '" + destination
				+ "' flight doesn't exists");
		return false;
	}

	public static void printJSONData() {
		System.out.println("{\n\"metros\" :  [");
		for (int i = 0; i < allCities.size(); i++) {
			if (i == 0)
				System.out.print("\t");
			System.out.print(allCities.get(i).toString());
			if (i != allCities.size() - 1)
				System.out.print(" , ");
		}
		System.out.println("\n ] ,");
		System.out.println("\"routes\" : [");
		for (int i = 0; i < flights.size(); i += 2) {
			if (i == 0)
				System.out.print("\t");
			System.out.print(flights.get(i).toString());
			if (i != flights.size() - 1)
				System.out.print(" , ");
		}
		System.out.println("\n ]\n}");
	}

	public static void getItinerary() {
		ArrayList<String> flightItinerary = new ArrayList<String>();
		System.out.print("Please enter a itinerary: ");
		String cityCodes = scanner.nextLine();
		getCityCode(cityCodes, flightItinerary);
		checkCityCodes(flightItinerary);
		Itinerary itinerary = null;
		for (int i = 0; i < flightItinerary.size(); i++) {
			if (i == 0) {
				City code = getCityFromCode(flightItinerary.get(i));
				for (int j = 0; j < code.getFlightList().size(); j++) {
					if (code.getFlightList().get(j).destination.getCode()
							.equals(flightItinerary.get(1))) {
						itinerary = new Itinerary(code.getFlightList().get(j));
					}
				}
			}
			if (i > 1) {
				City code = getCityFromCode(flightItinerary.get(i));
				itinerary.appendCity(code);
			}
		}
		System.out.println("\nDistance: " + itinerary.distance()
				+ " km\nCost: " + itinerary.cost() + " USD.\nDuration: "
				+ itinerary.duration() * 60 + " minutes ("
				+ itinerary.duration() + " hours )");
	}

	public static void getCityCode(String itinerary,
			ArrayList<String> flightItinerary) {
		Scanner newScanner = new Scanner(itinerary);
		newScanner.useDelimiter(",");
		while (newScanner.hasNext()) {
			if (newScanner.hasNext()) {
				String code = newScanner.next();
				flightItinerary.add(code);
			} else {
				break;
			}
		}
	}

	// checks that does entered city exist and do flights exist
	public static void checkCityCodes(ArrayList<String> flightItinerary) {
		ArrayList<String> cityCodes = new ArrayList<String>();
		for (int i = 0; i < allCities.size(); i++) {
			cityCodes.add(allCities.get(i).code);
		}
		for (int j = 0; j < flightItinerary.size(); j++) {
			if (!cityCodes.contains(flightItinerary.get(j))) {
				System.out.println("\n'" + flightItinerary.get(j)
						+ "' is not a valid code.");
				createConsole();
				return;
			}
		}
		checkCityFlights(flightItinerary);
	}

	public static void checkCityFlights(ArrayList<String> flightItinerary) {
		int counter = 1;
		for (int i = 0; i < flightItinerary.size(); i++) {
			counter = 1;
			for (int j = 0; j < flights.size(); j++) {
				if (flightItinerary.get(i).equals(
						flights.get(j).source.getCode())) {
					if (i != flightItinerary.size() - 1
							&& flightItinerary.get(i + 1).equals(
									flights.get(j).destination.getCode())) {
						counter--;
					}
				}
			}
			if (counter == 1 && i != flightItinerary.size() - 1) {
				System.out.println("\nThere is no flight from '"
						+ flightItinerary.get(i) + "' to '"
						+ flightItinerary.get(i + 1) + "'.");
				createConsole();
				return;
			}
		}
	}
}
