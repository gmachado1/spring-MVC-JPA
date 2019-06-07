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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.gustavo.starwars.model.domain.Planet;
import br.gustavo.starwars.service.PlanetService;

/**
 * atendendo as especificações INTERNACIONAIS HTTP
 * 
 *      código(ok)       |        ERROR      |     ERROR     |     ERROR     |
 *    200  get           |     500-internal  |      ---      |     ----      |
 *    200  get/id        |     500-internal  | 400-BAD REQ   | 404-NOT FOUND |
 *    201  post          |     500-internal  | 400-BAD REQ   |      ---      |
 *    204  put           |     500-internal  | 400-BAD REQ   | 404-NOT FOUND |
 *    204 delete         |     500-internal  | 400-BAD REQ   | 404-NOT FOUND |
 * 
 * 
 * @author gustavo
 *
 */
@Path("/planets")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PlanetServiceRest {

	private PlanetService service;

	@PostConstruct
	private void init() {
		service = new PlanetService();
	}

	@GET
	// @Path("/list")
	public List<Planet> list(@QueryParam("name") String name) {
		if (name!=null) {
			return service.getPlanetByName(name);
		}
		return service.list();
	}

	@GET
	@Path("/{id}")
	public Planet getById(@PathParam("id") int id) {
		Planet p = null;
		p = service.getById(id);
		return p;
	}

	@POST
//	@Path("/add")
	public Response save(Planet p) {
		String msg = "Erro adding Planet! :-( ";
		try {
			System.out.println(p);
			p = service.save(p);
			msg = "Planet added! id of the planet:" + p.getId();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(msg);
		}

		return Response.status(Status.CREATED).entity(p).build();
	}

	@PUT
	@Path("/{id}")
	public void update(Planet p, @PathParam("id") int id) {
		String msg = "Error on changing planet!";
		p.setId(id);
		try {

			service.update(p);
			System.out.println(p);
			msg = "Planet changed!";
			System.out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(msg);
		}

	}

	@DELETE
	@Path("/{id}")
	public void remove(@PathParam("id") int id) {
		String msg = "Error on removing planet!";
		try {

			service.remove(id);
			msg = "Planet removed! Bye bye...";
			System.out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(msg);
		}
	}
}
