package br.gustavo.starwars.service.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.gustavo.starwars.dao.PlanetsDao;
import br.gustavo.starwars.model.Planet;

@Path("/planets")
public class PlanetService {

	private PlanetsDao planetsDao;

	private final String CHARSET_UTF8 = ";charset=utf-8";

	@PostConstruct
	private void init() {
		planetsDao = new PlanetsDao();
	}

	@GET
	// @Path("/list")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Planet> list() {
		List<Planet> list = null;
		try {
			list = planetsDao.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@POST
//	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String add(Planet p) {
		String msg = "Erro adding Planet! :-( ";
		try {
			System.out.println(p);
			int id = planetsDao.add(p);
			msg = "Planet added!";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(msg);
		}

		return msg;
	}

	@GET
	@Path("/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Planet getById(@PathParam("id") int id) {
		Planet p = null;
		try {
			p = planetsDao.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String edit(Planet p, @PathParam("id") int id) {
		String msg = "Error on changing planet!";
		try {
			 
			planetsDao.edit(p);
			System.out.println(p);
			msg = "Planet changed!";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(msg);
		}

		return msg;
	}

	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN + CHARSET_UTF8)
	public String remove(@PathParam("id") int id) {
		String msg = "Error on removing planet!";
		try {

			planetsDao.remove(id);
			msg = "Planet removed! Bye bye...";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(msg);
		}

		return msg;
	}
}
