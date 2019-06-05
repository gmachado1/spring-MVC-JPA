package br.gustavo.starwars.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.gustavo.starwars.bd.config.BDConfig;
import br.gustavo.starwars.model.Planet;

public class PlanetsDao {

	public List<Planet> list() throws Exception {
		List<Planet> planets = new ArrayList<Planet>();
		Planet p;
		Connection conn = BDConfig.getConnection();
		String sql = "select * from planets";

		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			p = new Planet();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setSoil(rs.getString("soil"));
			p.setWeather(rs.getString("weather"));
			planets.add(p);
		}
		return planets;
	}

	public Planet getById(int id) throws Exception {
		Planet p = null;

		Connection conn = BDConfig.getConnection();

		String sql = "Select * from Planets where id=?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			p = new Planet();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setSoil(rs.getString("soil"));
			p.setWeather(rs.getString("weather"));
		}

		return p;
	}

	public int add(Planet p) throws Exception {
		int id = 0;
		Connection conn = BDConfig.getConnection();
		String sql = "insert into star_wars.planets (name,soil,weather) values (?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, p.getName());
		stmt.setString(2, p.getSoil());
		stmt.setString(3, p.getWeather());

		stmt.execute();

		
		ResultSet rs= stmt.getGeneratedKeys();
		if (rs.next()) {
			id=rs.getInt(1);
		}
		return id;
	}

	public void edit(Planet p) throws Exception {
		Connection conn = BDConfig.getConnection();
		String sql = "update star_wars.planets set name= ? ,soil = ? ,weather = ? where id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, p.getName());
		stmt.setString(2, p.getSoil());
		stmt.setString(3, p.getWeather());
		stmt.setInt(4, p.getId());

	}

	public void remove(int id) throws Exception {
		Connection conn = BDConfig.getConnection();
		String sql = "Delete from star_wars.planets where id=? ";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.execute();
	}
}
