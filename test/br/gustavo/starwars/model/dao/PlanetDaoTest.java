package br.gustavo.starwars.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.gustavo.starwars.exceptions.DAOException;
import br.gustavo.starwars.exceptions.ErrorCode;
import br.gustavo.starwars.model.domain.Planet;
import junit.framework.Assert;

class PlanetDaoTest {

	static PlanetDao planetDao = new PlanetDao();
	static List<Planet> planets = new ArrayList<Planet>();
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Planet p;	
		for (int i = 0; i < 5; i++) {
			p = new Planet();
			p.setId(i);
			p.setName((i % 2 == 0) ? "planet" + i : "moon" + i);
			p.setSoil((i % 2 == 0) ? "soil" + i : i + "soil");
			p.setWeather((i % 2 == 0) ? "weather" + i : i + "wheather");
			planets.add(p);
		}

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {

	}

	@BeforeEach
	void setUp() {

	}

	@AfterEach
	void tearDown() {
		// TODO Auto-generated method stub

	}
	
	@Test
	void testList() {
		assertEquals(planets.size(), 5);
		
		planetDao.list();

	}

	@Test
	void testIdLessThenZero() {
		
		assertEquals(planets.size(), 5);
		
		Planet p = new Planet();
		p.setId(-1);
		p.setName("terra");
		p.setSoil("soil");
		p.setWeather("wheather");
		
		
		DAOException expect = assertThrows( DAOException.class,() -> planetDao.getById(-1));
		assertEquals(400, expect.getCode());
	}

}
