package com.dangducton.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dangducton.dto.LocationDeaths;
import com.dangducton.dto.LocationStats;
import com.dangducton.service.CoronaVirusDataDeathService;
import com.dangducton.service.CoronaVirusDataService;

@Controller
public class CronaController {

	@Autowired
	CoronaVirusDataService coronaVirusDataService;

	@Autowired
	CoronaVirusDataDeathService coronaVirusDataDeathService;

	@GetMapping("/theodoicorona")
	public String home(Model model) {
		List<LocationStats> allStats = coronaVirusDataService.getAllStats();
		List<LocationStats> allStatsVietNam = coronaVirusDataService.getAllStatsVietNam();
		List<LocationDeaths> allDeaths = coronaVirusDataDeathService.getAllDeaths();
		List<LocationDeaths> allDeathsVietNam = coronaVirusDataDeathService.getAllDeathsVietNam();
		int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
		int totalReportedCasesVietNam = allStatsVietNam.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalNewCasesVietNam = allStatsVietNam.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();

		int totalDeaths = allDeaths.stream().mapToInt(stat -> stat.getDeaths()).sum();
		int totalRecovered = allDeaths.stream().mapToInt(stat -> stat.getRecovered()).sum();

		int totalDeathsVietNam = allDeathsVietNam.stream().mapToInt(stat -> stat.getDeaths()).sum();
		int totalRecoveredVietNam = allDeathsVietNam.stream().mapToInt(stat -> stat.getRecovered()).sum();

		model.addAttribute("ratioDeaths", (totalDeaths * 1f) / totalReportedCases);
		model.addAttribute("ratioRecovered", (totalRecovered * 1f) / totalReportedCases);
		model.addAttribute("ratioDeathsVietNam", (totalDeathsVietNam * 1f) / totalReportedCasesVietNam);
		model.addAttribute("ratioRecoveredVietNam", (totalRecoveredVietNam * 1f) / totalReportedCasesVietNam);
		model.addAttribute("totalDeaths", totalDeaths);
		model.addAttribute("totalRecovered", totalRecovered);
		model.addAttribute("totalDeaths", totalDeaths);
		model.addAttribute("totalRecovered", totalRecovered);
		model.addAttribute("totalDeathsVietNam", totalDeathsVietNam);
		model.addAttribute("totalRecoveredVietNam", totalRecoveredVietNam);
		model.addAttribute("locationStats", allStats);
		model.addAttribute("allDeaths", allDeaths);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalNewCases", totalNewCases);
		model.addAttribute("totalReportedCasesVietNam", totalReportedCasesVietNam);
		model.addAttribute("totalNewCasesVietNam", totalNewCasesVietNam);

		return "frontend/corona";
	}
}
