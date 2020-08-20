package com.dangducton.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dangducton.dto.LocationDeaths;

@Service
public class CoronaVirusDataDeathService {
	Date date = new Date();
	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	int year = localDate.getYear();
	int month = localDate.getMonthValue();
	int day = localDate.getDayOfMonth() - 1;
	String dayNew;

	private String VIRUS_CSSEGISandData = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/"
			+ "0" + month + "-" + day + "-" + year + ".csv";
	private List<LocationDeaths> allDeaths = new ArrayList<>();

	public List<LocationDeaths> getAllDeaths() {
		return allDeaths;
	}

	private List<LocationDeaths> allDeathsVietNam = new ArrayList<>();

	public List<LocationDeaths> getAllDeathsVietNam() {
		return allDeathsVietNam;
	}

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void VIRUS_CSSEGISandData() throws IOException, InterruptedException {
		List<LocationDeaths> newDeaths = new ArrayList<>();
		List<LocationDeaths> newDeathsVietNam = new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_CSSEGISandData)).build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
		StringReader csvBodyReader = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		for (CSVRecord record : records) {
			if (record.get("Country_Region").equals("Vietnam")) {
				LocationDeaths locationStatVietNam = new LocationDeaths();
				locationStatVietNam.setProvinceState(record.get("Province_State"));
				locationStatVietNam.setCountryRegion(record.get("Country_Region"));
				int latestCases = Integer.parseInt(record.get(7));
				int prevDayCases = Integer.parseInt(record.get(8));
				int recovered = Integer.parseInt(record.get(9));
				locationStatVietNam.setConfirmed(latestCases);
				locationStatVietNam.setDeaths(prevDayCases);
				locationStatVietNam.setRecovered(recovered);
				locationStatVietNam.setRatioDeaths((prevDayCases * 1f) / latestCases);
				locationStatVietNam.setRatioRecovered((recovered * 1f) / latestCases);
				newDeathsVietNam.add(locationStatVietNam);
			}
			LocationDeaths locationStat = new LocationDeaths();
			locationStat.setProvinceState(record.get("Province_State"));
			locationStat.setCountryRegion(record.get("Country_Region"));
			int latestCases = Integer.parseInt(record.get(7));
			int prevDayCases = Integer.parseInt(record.get(8));
			int recovered = Integer.parseInt(record.get(9));
			locationStat.setConfirmed(latestCases);
			locationStat.setDeaths(prevDayCases);
			locationStat.setRecovered(recovered);
			locationStat.setRatioDeaths((prevDayCases * 1f) / latestCases);
			locationStat.setRatioRecovered((recovered * 1f) / latestCases);
			newDeaths.add(locationStat);
		}
		this.allDeaths = newDeaths;
		this.allDeathsVietNam = newDeathsVietNam;
	}
}
