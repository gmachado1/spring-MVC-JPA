package br.gustavo.starwars.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import br.gustavo.starwars.exceptions.DAOException;
import br.gustavo.starwars.model.domain.Planet;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PlanetDaoTest {

	static PlanetDao planetDao = new PlanetDao();
	static List<Planet> planets = new ArrayList<Planet>();
	static int invalidId;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
//		Planet p = new Planet();
//
//		p.setName("Alderaan");
//		p.setSoil("grasslands, mountains");
//		p.setWeather("temperate");
//		planetDao.save(p);
//
//		p.setName("Yavin IV");
//		p.setSoil("jungle, rainforests");
//		p.setWeather("temperate, tropical");
//		planetDao.save(p);
//
//		p.setName("Hoth");
//		p.setSoil("tundra, ice caves, mountain ranges");
//		p.setWeather("frozen");
//		planetDao.save(p);
//
//		p.setName("Dagobah");
//		p.setSoil("swamp, jungles");
//		p.setWeather("murky");
//		planetDao.save(p);

	}

	@BeforeEach
	void setUp() {
//		Planet p = new Planet();
//
//		p.setName("Endor");
//		p.setSoil("forests, mountains, lakes");
//		p.setWeather("temperate");
//		validId = planetDao.save(p).getId();
	}

	@AfterEach
	void tearDown() {
		// planetDao.remove(validId);
	}

	@RepeatedTest(5)
	void testA() {
		// testASavePlanets() {
		planets = planetDao.list();

		int size = planets.size();
		int id = planets.lastIndexOf(planets) + 1;

		Planet p = new Planet();
		p.setName((id % 2 == 0) ? "planet" + id : "moon" + id);
		p.setSoil((id % 2 == 0) ? "soil" + id : id + "soil");
		p.setWeather((id % 2 == 0) ? "habitável" + id : id + "inabitável");

		invalidId = planetDao.save(p).getId() + 1; // used on Test: testEIdNotExist(testE)

		int newSize = planetDao.list().size();

		assertEquals(size + 1, newSize);
	}

	@Test
	void testB() {
		// testBIdExist() {
		Planet aux = planetDao.list().get(0);
		Planet p = planetDao.getById(aux.getId());
		assertEquals(true, planetDao.planetIsValid(p));
	}

	@Test
	void testC() {
		// testCInvalidPlanet() {
		Planet p = new Planet();

		p.setName("");
		p.setSoil("013");
		p.setWeather("wheather");
		DAOException expect = assertThrows(DAOException.class, () -> planetDao.save(p));
		assertEquals(400, expect.getCode());// error name field
		expect = assertThrows(DAOException.class, () -> planetDao.update(p));
		assertEquals(400, expect.getCode());// error name field

		p.setName("Plutão");
		p.setSoil("01");
		p.setWeather("wheather");
		expect = assertThrows(DAOException.class, () -> planetDao.save(p));
		assertEquals(400, expect.getCode());// error Soil field
		expect = assertThrows(DAOException.class, () -> planetDao.update(p));
		assertEquals(400, expect.getCode());// error Soil field

		p.setName("Plutão");
		p.setSoil("013");
		p.setWeather("");
		expect = assertThrows(DAOException.class, () -> planetDao.save(p));
		assertEquals(400, expect.getCode());// error weather field
		expect = assertThrows(DAOException.class, () -> planetDao.update(p));
		assertEquals(400, expect.getCode());// error weather field

	}

	@Test
	void testD() {
		// testDIdLessThenZero() {
		DAOException expect = assertThrows(DAOException.class, () -> planetDao.getById(-1));
		assertEquals(400, expect.getCode());

		Planet p = new Planet();
		p.setId(-1);
		expect = assertThrows(DAOException.class, () -> planetDao.getById(p.getId()));
		assertEquals(400, expect.getCode());

		expect = assertThrows(DAOException.class, () -> planetDao.remove(p.getId()));
		assertEquals(400, expect.getCode());

		expect = assertThrows(DAOException.class, () -> planetDao.update(p));
		assertEquals(400, expect.getCode());
	}

	@Test
	void testE() {
		// testEIdNotExist() {
		assertEquals(true, invalidId > 0);
		DAOException expect = assertThrows(DAOException.class, () -> planetDao.getById(invalidId));
		assertEquals(404, expect.getCode());

		Planet p = new Planet();
		p.setId(invalidId);
		p.setName("AnyName");
		p.setSoil("013");
		p.setWeather("wheather");
		expect = assertThrows(DAOException.class, () -> planetDao.update(p));
		assertEquals(404, expect.getCode());

		expect = assertThrows(DAOException.class, () -> planetDao.remove(invalidId));
		assertEquals(404, expect.getCode());

	}

	@Test
	void testF() {
		// testFFindPlanetByName() {
		Planet p = planetDao.list().get(0);

		assertEquals(true, planetDao.planetIsValid(p));
		int ifExist = planetDao.getByName(p.getName()).size();
		assertEquals(true, ifExist > 0);

		DAOException expect = assertThrows(DAOException.class, () -> planetDao.getByName("zzzz"));
		assertEquals(404, expect.getCode());
		// CAUTION: It may have a planet with the name "zzzz". If you got error here,
		// look on your database.
	}

	@RepeatedTest(5)
	void testG() {
		// testGRemovePlanets() {
		planets = planetDao.list();
		int size = planets.size();

		assertEquals(true, size > 0);

		Planet p = planets.get(size - 1);
		planetDao.remove(p.getId());

		planets = planetDao.list();
		int newSize = planets.size();

		assertEquals(--size, newSize);
	}

}
