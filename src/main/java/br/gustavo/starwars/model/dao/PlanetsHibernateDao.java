package br.gustavo.starwars.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.gustavo.starwars.exceptions.DAOException;
import br.gustavo.starwars.exceptions.ErrorCode;
import br.gustavo.starwars.model.domain.Planet;

public class PlanetsHibernateDao {

	public List<Planet> list() {
		EntityManager em = JPAUtils.getEntityManager();
		List<Planet> planets = null;

		try {
			planets = em.createQuery("select p from Planets p", Planet.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException("Erro ao recuperar todos os planetas do universo: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		return planets;
	}

	public Planet getById(int id){
		Planet p = null;
		EntityManager em = JPAUtils.getEntityManager();

		if (id <= 0) {
			throw new DAOException("O id precisa ser maior do que 0.", ErrorCode.BAD_REQUEST.getCode());
		}

		try {
			p = em.find(Planet.class, id);
		} catch (RuntimeException ex) {
			throw new DAOException("Erro ao buscar planeta por id na galáxia: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		if (p == null) {
			throw new DAOException("Planeta de id " + id + " não existe.", ErrorCode.NOT_FOUND.getCode());
		}
		return p;
	}

	public Planet save(Planet p) {
		EntityManager em = JPAUtils.getEntityManager();

		if (!planetIsValid(p)) {
			throw new DAOException("Planeta com dados incompletos.", ErrorCode.BAD_REQUEST.getCode());
		}

		try {
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();

		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException("Erro ao salvar planeta no espaço. " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}
		return p;
	}

	public Planet update(Planet p){
		EntityManager em = JPAUtils.getEntityManager();
		Planet planetManaged = null;

		if (p.getId() <= 0) {
			throw new DAOException("O id precisa ser maior do que 0.", ErrorCode.BAD_REQUEST.getCode());
		}
		if (!planetIsValid(p)) {
			throw new DAOException("Planeta com dados incompletos.", ErrorCode.BAD_REQUEST.getCode());
		}

		try {
			em.getTransaction().begin();
			planetManaged = em.find(Planet.class, p.getId());
			planetManaged.setName(p.getName());
			planetManaged.setSoil(p.getSoil());
			planetManaged.setWeather(p.getWeather());
			// em.merge(produtoManaged);
			em.getTransaction().commit();
		} catch (NullPointerException ex) {
			em.getTransaction().rollback();
			throw new DAOException("Planeta informado para atualização não existe: " + ex.getMessage(),
					ErrorCode.NOT_FOUND.getCode());
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException("Erro ao atualizar planeta: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}
		return planetManaged;
	}

	public void remove(int id)  {
		EntityManager em = JPAUtils.getEntityManager();
		Planet planet = null;

		if (id <= 0) {
			throw new DAOException("O id precisa ser maior do que 0.", ErrorCode.BAD_REQUEST.getCode());
		}

		try {
			em.getTransaction().begin();
			planet = em.find(Planet.class, id);
			em.remove(planet);
			em.getTransaction().commit();
		} catch (IllegalArgumentException ex) {
			em.getTransaction().rollback();
			throw new DAOException("Planeta informado para remoção não existe: " + ex.getMessage(),
					ErrorCode.NOT_FOUND.getCode());
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException("Erro ao remover planeta: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}
	}

	private boolean planetIsValid(Planet p) {
		try {
			if ((p.getName().isEmpty()) || (p.getSoil().length() < 3) || (p.getWeather().length() < 1))
				return false;
		} catch (NullPointerException ex) {
			throw new DAOException("Planeta com dados incompletos.", ErrorCode.BAD_REQUEST.getCode());
		}

		return true;
	}

	public List<Planet> getByName(String name) {
		EntityManager em = JPAUtils.getEntityManager();
		List<Planet> planets = null;

		try {
			planets = em.createQuery("select p from Planets p where p.name like :name", Planet.class)
					.setParameter("name", "%" + name + "%").getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException("Erro ao buscar planetas pelo nome: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		if (planets.isEmpty()) {
			throw new DAOException("A consulta não retornou elementos.", ErrorCode.NOT_FOUND.getCode());
		}

		return planets;
	}

}
