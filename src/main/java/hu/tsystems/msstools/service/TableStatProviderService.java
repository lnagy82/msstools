package hu.tsystems.msstools.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import hu.tsystems.msstools.domain.SystemApp;
import hu.tsystems.msstools.repository.SystemRepository;
import hu.tsystems.msstools.service.dto.TableStatDTO;

@Component
public class TableStatProviderService {
	
	@Autowired
	private SystemRepository systemRepository;
	
	@Autowired
	private TableStatService tableStatService;
	
	@Autowired
	private SystemService systemService;

	class LocalUserInfo implements UserInfo {
		String passwd;

		public String getPassword() {
			return passwd;
		}

		public boolean promptYesNo(String str) {
			return true;
		}

		public String getPassphrase() {
			return null;
		}

		public boolean promptPassphrase(String message) {
			return true;
		}

		public boolean promptPassword(String message) {
			return true;
		}

		public void showMessage(String message) {
		}
	}

	private List<TableStatDTO> getResult(SystemApp app) throws Exception {

		String host = app.getIp(); // First level target
		String user = "ths";
		String password = "ths";
		String tunnelRemoteHost = "localhost"; // The host of the second target
		int port = app.getPort();

		JSch jsch = new JSch();
		Session session = jsch.getSession(user, host, port);
		session.setPassword(password);
		LocalUserInfo lui = new LocalUserInfo();
		session.setUserInfo(lui);
		session.setConfig("StrictHostKeyChecking", "no");
		
		// create port from 2233 on local system to port 22 on tunnelRemoteHost
		session.setPortForwardingL(50000, tunnelRemoteHost, 5432);
		session.connect();
		session.openChannel("direct-tcpip");

		List<TableStatDTO> result = query(50000);

		session.disconnect();

		return result;
	}

	private List<TableStatDTO> query(int rport) throws SQLException {

		String dbuserNameAlfresco = "ths";
		String dbpasswordAlfresco = "ths";
		String urlAlfresco = "jdbc:postgresql://localhost:" + rport + "/ths";
		String driverName = "org.postgresql.Driver";
		Session session = null;
		Connection dbConnAlfresco = null;
		List<TableStatDTO> result = new ArrayList<TableStatDTO>();

		try {
			// mysql database connectivity
			Class.forName(driverName).newInstance();
			dbConnAlfresco = DriverManager.getConnection(urlAlfresco, dbuserNameAlfresco, dbpasswordAlfresco);

			System.out.println("Database connection established");

			dbConnAlfresco.setAutoCommit(false);
			String qnameSQL = "select * from pg_stat_all_tables where schemaname = 'ths';";

			PreparedStatement prepStmnt = dbConnAlfresco.prepareStatement(qnameSQL);
			ResultSet rs = prepStmnt.executeQuery();
			while (rs.next()) {
				TableStatDTO dto = createTableStatDTO(rs);
				dto.setSystemId(2651L);
				result.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbConnAlfresco != null && !dbConnAlfresco.isClosed()) {
				System.out.println("Closing ALFRESCO Database Connection");
				dbConnAlfresco.close();
			}
			if (session != null && session.isConnected()) {
				System.out.println("Closing SSH Connection");
				session.disconnect();
			}
		}
		return result;
	}

	private TableStatDTO createTableStatDTO(ResultSet rs) throws SQLException {
		TableStatDTO dto = new TableStatDTO();
		dto.setAnalyzeCount(rs.getBigDecimal("analyze_count"));
		dto.setAutoanalyzeCount(rs.getBigDecimal("autoanalyze_count"));
		dto.setAutovacuumCount(rs.getBigDecimal("autovacuum_count"));
		dto.setIdxScan(rs.getBigDecimal("idx_scan"));
		dto.setIdxTupFetch(rs.getBigDecimal("idx_tup_fetch"));
		dto.setLastAnalyze(createLocalDate(rs.getDate("last_analyze")));
		dto.setLastAutoanalyze(createLocalDate(rs.getDate("last_autoanalyze")));
		dto.setLastAutovacuum(createLocalDate(rs.getDate("last_autovacuum")));
		dto.setLastVacuum(createLocalDate(rs.getDate("last_vacuum")));
		dto.setnDeadTup(rs.getBigDecimal("n_dead_tup"));
		dto.setnLiveTup(rs.getBigDecimal("n_live_tup"));
		dto.setnTupDel(rs.getBigDecimal("n_tup_del"));
		dto.setnTupHotUpd(rs.getBigDecimal("n_tup_hot_upd"));
		dto.setnTupIns(rs.getBigDecimal("n_tup_ins"));
		dto.setnTupUpd(rs.getBigDecimal("n_tup_upd"));
		dto.setRelname(rs.getString("relname"));
		dto.setSchemaname(rs.getString("schemaname"));
		dto.setSeqScan(rs.getBigDecimal("seq_scan"));
		dto.setSeqTupRead(rs.getBigDecimal("seq_tup_read"));
		dto.setVacuumCount(rs.getBigDecimal("vacuum_count"));
		return dto;
	}
	
	
	private LocalDate createLocalDate(java.sql.Date date){
		if(date == null)
			return null;
		else
			return date.toLocalDate();
	}
	
	public void updateAll() throws Exception{
		List<SystemApp> apps = systemRepository.findAll();
		LocalDate updateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
		for (SystemApp systemApp : apps) {
			List<TableStatDTO> tableStats = getResult(systemApp);
			
			for (TableStatDTO tableStatDTO : tableStats) {
				tableStatDTO.setSystemId(systemApp.getId());
				tableStatDTO.setSystemName(systemApp.getName());
				tableStatDTO.setUpdateTime(updateTime);
				tableStatDTO.setUpdateNumber(1);
				tableStatService.save(tableStatDTO);
			}
		}
	}

//	private ZonedDateTime createZonedDateTime(Date date) {
//		if (date == null) {
//			return null;
//		} else {
//			return ZonedDateTime.of(date.toLocalDate().atStartOfDay(), ZoneId.systemDefault());
//		}
//	}
}
