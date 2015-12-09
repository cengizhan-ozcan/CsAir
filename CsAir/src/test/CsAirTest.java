package test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import air.City;
import air.CsAir;
import air.Flight;
import air.Itinerary;


public class CsAirTest {
	CsAir csAir = new CsAir();
	
	@Test
	public void testForSmallestPopulation() throws FileNotFoundException {
		City cityB = new City("Giresun","GRS","TR","Asia",1,"149",842000,1);
		City cityC = new City("Kars","KRS","TR","Asia",1,"153",672000,1);
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(cityB);
		cities.add(cityC);
		csAir.readFile();
		csAir.addCitiesFromCsairData();
		assertEquals(672000,CsAir.findSmallestPopulationOfCities(cities));
	}
	@Test
	public void testForBiggestPopulation() throws FileNotFoundException {
		City city1 = new City("Karabuk","KRB","TR","Asia",1,"123",100000,1);
		City city2 = new City("Denizli","DEN","TR","Asia",1,"125",1500000,1);
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(city1);
		cities.add(city2);
		csAir.readFile();
		csAir.addCitiesFromCsairData();
		assertEquals(1500000,CsAir.findBiggestPopulationOfCities(cities));
	}
	@Test
	public void testForShortestFlight() throws FileNotFoundException {
		City cityA = new City("Hakkari","HKK","TR","Asia",1,"178",3200000,1);
		City cityB = new City("Hatay","HTY","TR","Asia",1,"189",751000,1);
		City cityC = new City("Konya","KNY","TR","Asia",1,"119",1200000,1);
		Flight firstLeg = new Flight(cityA,cityB,370);
		Flight secondLeg = new Flight(cityB,cityC,740);
		ArrayList<City> cities = new ArrayList<City>();
		ArrayList<Flight> fligths = new ArrayList<Flight>();
		cities.add(cityA);
		cities.add(cityB);
		cities.add(cityC);
		fligths.add(firstLeg);
		fligths.add(secondLeg);
		csAir.readFile();
		csAir.addCitiesFromCsairData();
		csAir.addFlightsFromCsairData();
		assertEquals(370,CsAir.findShortestFlight(fligths));
	}
	@Test
	public void testForLongestFlight() throws FileNotFoundException {
		City cityA = new City("Yozgat","YZG","TR","Asia",1,"118",750000,1);
		City cityB = new City("Giresun","GRS","TR","Asia",1,"149",842000,1);
		City cityC = new City("Kars","KRS","TR","Asia",1,"153",672000,1);
		City cityD = new City("Isparta","ISP","TR","Asia",1,"123",132000,1);
		Flight firstLeg = new Flight(cityA,cityB,200);
		Flight secondLeg = new Flight(cityB,cityC,640);
		Flight thirdLeg = new Flight(cityB,cityD,140);
		ArrayList<City> cities = new ArrayList<City>();
		ArrayList<Flight> fligths = new ArrayList<Flight>();
		cities.add(cityA);
		cities.add(cityB);
		cities.add(cityC);
		cities.add(cityD);
		fligths.add(firstLeg);
		fligths.add(secondLeg);
		fligths.add(thirdLeg);
		csAir.readFile();
		csAir.addCitiesFromCsairData();
		csAir.addFlightsFromCsairData();
		assertEquals(640,CsAir.findLongestFlight(fligths));
	}
	@Test
	public void testForAverageDistance() throws FileNotFoundException {
		City cityA = new City("Trabzon","TRB","TR","Asia",1,"161",4000000,1);
		City cityB = new City("Rize","RZE","TR","Asia",1,"153",1300000,1);
		City cityC = new City("Kars","KRS","TR","Asia",1,"153",672000,1);
		Flight firstLeg = new Flight(cityA,cityB,300);
		Flight secondLeg = new Flight(cityB,cityC,600);
		ArrayList<City> cities = new ArrayList<City>();
		ArrayList<Flight> fligths = new ArrayList<Flight>();
		cities.add(cityA);
		cities.add(cityB);
		cities.add(cityC);
		fligths.add(firstLeg);
		fligths.add(secondLeg);
		csAir.readFile();
		csAir.addCitiesFromCsairData();
		csAir.addFlightsFromCsairData();
		assertEquals(450,CsAir.findAverageDistance(fligths));
	}
	@Test
	public void testForAveragePopulation() throws FileNotFoundException {
		City cityA = new City("Yozgat","YZG","TR","Asia",1,"118",750000,1);
		City cityB = new City("Giresun","GRS","TR","Asia",1,"149",842000,1);
		City cityC = new City("Kars","KRS","TR","Asia",1,"153",672000,1);
		City cityD = new City("Isparta","ISP","TR","Asia",1,"123",132000,1);
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(cityA);
		cities.add(cityB);
		cities.add(cityC);
		cities.add(cityD);
		csAir.readFile();
		csAir.addCitiesFromCsairData();
		assertEquals(599000,CsAir.findAveragePopulationOfCities(cities));
	}
	@Test
	public void testSingleFlightDistance() {
		City source = new City("Karabuk","KRB","TR","Asia",1,"123",100000,1);
		City destination = new City("Denizli","DEN","TR","Asia",1,"125",1500000,1);
		Flight firstLeg = new Flight(source,destination,1234);
		source.addFlights(firstLeg);
		Itinerary itinerary = new Itinerary(firstLeg);		
		assertEquals(1234,itinerary.distance());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTwoFlightDistance() {
		City cityA = new City("Adana","ADN","TR","Asia",1,"123",500000,1);
		City cityB = new City("Bursa","BUR","TR","Asia",1,"129",900000,1);
		City cityC = new City("Ceyhan","CYH","TR","Asia",1,"121",350000,1);
		Flight firstLeg = new Flight(cityA,cityB,1944);
		Flight secondLeg = new Flight(cityA,cityC,56);
		cityA.addFlights(firstLeg);
		cityA.addFlights(secondLeg);
		Itinerary itinerary = new Itinerary(firstLeg);
		itinerary.appendCity(cityC);
	}

	@Test
	public void testThreeFlightDistance() {
		City cityA = new City("Balıkesir","BAL","TR","Asia",1,"128",3000000,1);
		City cityB = new City("Mardin","MAR","TR","Asia",1,"130",750000,1);
		City cityC = new City("Ordu","ORD","TR","Asia",2,"110",600000,1);
		City cityD = new City("Diyarbakır","DYR","TR","Asia",2,"160",2500000,1);
		Flight firstLeg = new Flight(cityA,cityB,1200);
		Flight secondLeg = new Flight(cityB,cityC,150);
		Flight thirdLeg = new Flight(cityC,cityD,100);
		cityA.addFlights(firstLeg);
		cityB.addFlights(secondLeg);
		cityC.addFlights(thirdLeg);
		Itinerary itinerary = new Itinerary(firstLeg);
		itinerary.appendCity(cityC);
		itinerary.appendCity(cityD);
		assertEquals(1450,itinerary.distance());
	}

	@Test
	public void testFourFlightDistance() {
		City cityA = new City("Adıyaman","ADY","TR","Asia",1,"121",1000000,1);
		City cityB = new City("Zonguldak","ZGN","TR","Asia",1,"150",450000,1);
		City cityC = new City("Edirne","EDR","TR","Asia",1,"133",674000,1);
		City cityD = new City("Tekirdag","TKR","TR","Asia",1,"143",142000,1);
		City cityE = new City("Antalya","ATY","TR","Asia",1,"117",403000,1);
		Flight firstLeg = new Flight(cityA,cityB,1234);
		Flight secondLeg = new Flight(cityB,cityC,56);
		Flight thirdLeg = new Flight(cityC,cityD,100);
		Flight fourthLeg = new Flight(cityD,cityE,210);
		cityA.addFlights(firstLeg);
		cityB.addFlights(secondLeg);
		cityC.addFlights(thirdLeg);
		cityD.addFlights(fourthLeg);
		Itinerary itinerary = new Itinerary(firstLeg);
		itinerary.appendCity(cityC);
		itinerary.appendCity(cityD);
		itinerary.appendCity(cityE);
		assertEquals(1600,itinerary.distance());
	}

	@Test
	public void testSingleFlightCost() {
		City cityA = new City("Gaziantep","GZA","TR","Asia",1,"134",950000,1);
		City cityB = new City("Bilecik","BLC","TR","Asia",1,"153",425000,1);
		Flight firstLeg = new Flight(cityA,cityB,300);
		cityA.addFlights(firstLeg);
		double cost = 0.35*300;
		Itinerary itinerary = new Itinerary(firstLeg);
		assertEquals(cost,itinerary.cost(),0.0001);	
	}
	
	@Test
	public void testTwoFlightCost() {
		City cityA = new City("Bolu","BOL","TR","Asia",1,"122",650000,1);
		City cityB = new City("Mersin","MRS","TR","Asia",1,"164",1750000,1);
		City cityC = new City("izmir","IZM","TR","Asia",1,"112",6300000,1);
		Flight firstLeg = new Flight(cityA,cityB,300);
		Flight secondLeg = new Flight(cityB,cityC,500);
		cityA.addFlights(firstLeg);
		cityB.addFlights(secondLeg);
		double cost = 0.35*300;
		Itinerary itinerary = new Itinerary(firstLeg);
		itinerary.appendCity(cityC);
		cost += 0.30*500;
		assertEquals(cost,itinerary.cost(),0.0001);	
	}

	@Test
	public void testThreeFlightCost() {
		City cityA = new City("Ankara","ANK","TR","Asia",1,"143",4260000,1);
		City cityB = new City("Van","VAN","TR","Asia",1,"167",832000,1);
		City cityC = new City("Sinop","SNP","TR","Asia",1,"171",591000,1);
		City cityD = new City("Samsun","SMS","TR","Asia",1,"131",932000,1);
		Flight firstLeg = new Flight(cityA,cityB,300);
		Flight secondLeg = new Flight(cityB,cityC,500);
		Flight thirdLeg = new Flight(cityC,cityD,270);
		cityA.addFlights(firstLeg);
		cityB.addFlights(secondLeg);
		cityC.addFlights(thirdLeg);
		double cost = 0.35*300;
		Itinerary itinerary = new Itinerary(firstLeg);
		itinerary.appendCity(cityC);
		cost += 0.30*500;
		itinerary.appendCity(cityD);
		cost += 0.25*270;
		assertEquals(cost,itinerary.cost(),0.0001);	
	}
	
	@Test
	public void testNineFlightCost() {
		City cityA = new City("Sivas","SVS","TR","Asia",1,"142",4210000,1);
		City cityB = new City("Karaman","KRM","TR","Asia",1,"164",837000,1);
		City cityC = new City("Nevsehir","NSH","TR","Asia",1,"321",123000,1);
		City cityD = new City("Tokat","TKT","TR","Asia",1,"153",210000,1);
		City cityE = new City("Erzincan","ERZ","TR","Asia",1,"131",812000,1);
		City cityF = new City("Afyon","AFY","TR","Asia",1,"152",756000,1);
		City cityG = new City("Yalova","YLV","TR","Asia",1,"104",746000,1);
		City cityH = new City("Aydin","AYD","TR","Asia",1,"115",2110000,1);
		City cityI = new City("Bartin","BRT","TR","Asia",1,"182",1520000,1);
		Flight firstLeg = new Flight(cityA,cityB,300);
		Flight secondLeg = new Flight(cityB,cityC,500);
		Flight thirdLeg = new Flight(cityC,cityD,270);
		Flight fourthLeg = new Flight(cityD,cityE,310);
		Flight fifthLeg = new Flight(cityE,cityF,150);
		Flight sixthLeg = new Flight(cityF,cityG,345);
		Flight seventhLeg = new Flight(cityG,cityH,650);
		Flight eightLeg = new Flight(cityH,cityI,170);
		cityA.addFlights(firstLeg);
		cityB.addFlights(secondLeg);
		cityC.addFlights(thirdLeg);
		cityD.addFlights(fourthLeg);
		cityE.addFlights(fifthLeg);
		cityF.addFlights(sixthLeg);
		cityG.addFlights(seventhLeg);
		cityH.addFlights(eightLeg);
		double cost = 0.35*300;
		Itinerary itinerary = new Itinerary(firstLeg);
		itinerary.appendCity(cityC);
		cost += 0.30*500;
		itinerary.appendCity(cityD);
		cost += 0.25*270;
		itinerary.appendCity(cityE);
		cost += 0.20*310;
		itinerary.appendCity(cityF);
		cost += 0.15*150;
		itinerary.appendCity(cityG);
		cost += 0.10*345;
		itinerary.appendCity(cityH);
		cost += 0.05*650;
		itinerary.appendCity(cityI);
		cost += 0.0*170;
		assertEquals(cost,itinerary.cost(),0.0001);	
	}

	@Test
	public void testDuration() {
		City cityA = new City("Trabzon","TRB","TR","Asia",1,"161",4000000,1);
		City cityB = new City("Rize","RZE","TR","Asia",1,"153",1300000,1);
		Flight firstLeg = new Flight(cityA,cityB,680);
		cityA.addFlights(firstLeg);
		double duration = 200.0/((0+750.0)/2.0) + 280.0/750.0 + 200.0/((0+750.0)/2.0);
		Itinerary itinerary = new Itinerary(firstLeg);
		assertEquals(duration,itinerary.duration(),0.0001);
	}

	@Test
	public void testDuration2() {
		City cityA = new City("Hakkari","HKK","TR","Asia",1,"178",3200000,1);
		City cityB = new City("Hatay","HTY","TR","Asia",1,"189",751000,1);
		City cityC = new City("Konya","KNY","TR","Asia",1,"119",1200000,1);
		Flight firstLeg = new Flight(cityA,cityB,370);
		Flight secondLeg = new Flight(cityB,cityC,740);
		cityA.addFlights(firstLeg);
		cityB.addFlights(secondLeg);
		double acceleration = 750.0/(200.0/((0+750.0)/2.0));
		double duration = Math.sqrt(370/acceleration);
		Itinerary itinerary = new Itinerary(firstLeg);
		itinerary.appendCity(cityC);
		duration += 200.0/((0+750.0)/2.0) + 340.0/750.0 + 200.0/((0+750.0)/2.0) +120.0/60;
		assertEquals(duration,itinerary.duration(),0.0001);
	}
	
	@Test
	public void testDuration3() {
		System.out.println("asd");
		City cityA = new City("Yozgat","YZG","TR","Asia",1,"118",750000,1);
		City cityB = new City("Giresun","GRS","TR","Asia",1,"149",842000,1);
		City cityC = new City("Kars","KRS","TR","Asia",1,"153",672000,1);
		City cityD = new City("Isparta","ISP","TR","Asia",1,"123",132000,1);
		Flight firstLeg = new Flight(cityA,cityB,200);
		Flight secondLeg = new Flight(cityB,cityC,640);
		Flight thirdLeg = new Flight(cityB,cityD,140);
		cityA.addFlights(firstLeg);
		cityB.addFlights(secondLeg);
		cityB.addFlights(thirdLeg);
		double acceleration = 750.0/(200.0/((0+750.0)/2.0));
		double duration = Math.sqrt(200/acceleration);
		Itinerary itinerary = new Itinerary(firstLeg);
		itinerary.appendCity(cityC);
		duration += 200.0/((0+750.0)/2.0) + 240.0/750.0 + 200.0/((0+750.0)/2.0)+110.0/60;
		assertEquals(duration,itinerary.duration(),0.0001);
	}

}
