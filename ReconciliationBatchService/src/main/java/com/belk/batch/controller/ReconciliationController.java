package com.belk.batch.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.belk.batch.model.ReconciliationReport;
import com.belk.batch.service.ReconciliationService;

@RestController
public class ReconciliationController {

	@Autowired
	private ReconciliationService service;

	@Value("${app.batchInterval}")
	private long batchInterval;

	private final static Logger LOGGER = LoggerFactory.getLogger(ReconciliationController.class);

	@GetMapping(path = "/reconciliationbatch")
	public ReconciliationReport reconciliationPorcess(@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime, @RequestParam String organizationId) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Incoming Request for Reconciliation Process: {} {} {}", startTime, endTime, organizationId);
		}

		ReconciliationReport reconciliationReport = service.reconciliationPorcess(convertDatetoIso8601Format(startTime),
				convertDatetoIso8601Format(endTime), organizationId);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Completed Reconciliation Process");
		}

		return reconciliationReport;
	}

	@PostMapping(path = "/reconciliationbatchprocess")
	public ReconciliationReport reconciliationBatchPorcess1AM() throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Incoming Request for Reconciliation Process: ");
		}
		LocalDate todayDate = LocalDate.now();

		String startTime = todayDate.minusDays(1).toString() + "00:00:00";
		String endTime = todayDate.minusDays(1).toString() + "23:59:00";

		ReconciliationReport reconciliationReport = service.reconciliationPorcess(convertDatetoIso8601Format(startTime),
				convertDatetoIso8601Format(endTime), "belkinc");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Completed Reconciliation Process");
		}

		return reconciliationReport;
	}

	@PostMapping(path = "/reconciliationcustombatchprocess")
	public ReconciliationReport reconciliationCustomBatchPorcess() throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Incoming Request for Reconciliation Process: ");
		}
		LocalDateTime startDate = LocalDateTime.now().minusHours(batchInterval);
		String startTime = startDate.format(DateTimeFormatter.ofPattern("uuuu-MM-ddHH:mm:ss"));

		LocalDateTime endDate = LocalDateTime.now();
		String endTime = endDate.format(DateTimeFormatter.ofPattern("uuuu-MM-ddHH:mm:ss"));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("StartTime: {} ;EndTime: {} ", startTime, endTime);
		}

		ReconciliationReport reconciliationReport = service.reconciliationPorcess(convertDatetoIso8601Format(startTime),
				convertDatetoIso8601Format(endTime), "belkinc");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Completed Reconciliation Process");
		}

		return reconciliationReport;
	}

	private DateTime convertDatetoIso8601Format(String input) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		Date formatedDate = sdf.parse(input);
		return new DateTime(formatedDate.getTime()).withZoneRetainFields(DateTimeZone.UTC);

	}

}
