package com.dangducton.dto;

public class LocationDeaths {
	private String ProvinceState;
	private String CountryRegion;
	private int Confirmed;
	private int Deaths;
	private int Recovered;
	private float ratioDeaths;
	private float ratioRecovered;

	public int getRecovered() {
		return Recovered;
	}

	public void setRecovered(int recovered) {
		Recovered = recovered;
	}

	public String getProvinceState() {
		return ProvinceState;
	}

	public void setProvinceState(String provinceState) {
		ProvinceState = provinceState;
	}

	public String getCountryRegion() {
		return CountryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		CountryRegion = countryRegion;
	}

	public int getConfirmed() {
		return Confirmed;
	}

	public void setConfirmed(int confirmed) {
		Confirmed = confirmed;
	}

	public int getDeaths() {
		return Deaths;
	}

	public void setDeaths(int deaths) {
		Deaths = deaths;
	}

	public float getRatioDeaths() {
		return ratioDeaths;
	}

	public void setRatioDeaths(float ratioDeaths) {
		this.ratioDeaths = ratioDeaths;
	}

	public float getRatioRecovered() {
		return ratioRecovered;
	}

	public void setRatioRecovered(float ratioRecovered) {
		this.ratioRecovered = ratioRecovered;
	}

}
