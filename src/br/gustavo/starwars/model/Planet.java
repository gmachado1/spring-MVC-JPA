package br.gustavo.starwars.model;

public class Planet {

	private int id;
	private String name;
	private String soil;
	private String weather;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSoil() {
		return soil;
	}

	public void setSoil(String soil) {
		this.soil = soil;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "Planet [id=" + id + ", name=" + name + ", soil=" + soil + ", weather=" + weather + "]";
	}

}
