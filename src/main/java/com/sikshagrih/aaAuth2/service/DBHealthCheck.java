package com.sikshagrih.aaAuth2.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DBHealthCheck {
	
	public static final Logger logger = LoggerFactory.getLogger("DBHealth");
	
	@Value("${db.healthCheck.enable:false}")
	private boolean healthCheckEnable;
	
	
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public DBHealthCheck(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Scheduled(fixedDelayString = "30000")
	public void dbConnectionCheck() {
		if (healthCheckEnable) {
			try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
				if (connection != null) {
					logger.info("Data base connection is up - Time: {}", Instant.now());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
