package br.gustavo.starwars.service;

import java.util.List;

import br.gustavo.starwars.model.dao.PlanetsHibernateDao;
import br.gustavo.starwars.model.domain.Planet;

public class PlanetService {

	private PlanetsHibernateDao dao = new PlanetsHibernateDao();
	//private PlanetsHibernateDao dao = new PlanetsJDBCDao();

	public List<Planet> list() {
		return dao.list();
	}

	public Planet getById(int id) {
		return dao.getById(id);
	}

	public Planet save(Planet p) {
		return dao.save(p);
	}

	public Planet update(Planet p) {
		return dao.update(p);
	}

	public void remove(int id) {
		dao.remove(id);
	}

	public List<Planet> getPlanetByName(String name) {
		return dao.getByName(name);
	}

}
