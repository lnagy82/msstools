package hu.tsystems.msstools.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import hu.tsystems.msstools.service.dto.TableStatDTO;

@Component
public class TableStatProviderService {

	class localUserInfo implements UserInfo {
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

	public List<TableStatDTO> getResult() throws Exception {

		String host = "10.82.128.145"; // First level target
		String user = "ths";
		String password = "ths";
		String tunnelRemoteHost = "localhost"; // The host of the second target
		int port = 22;

		JSch jsch = new JSch();
		Session session = jsch.getSession(user, host, port);
		session.setPassword(password);
		localUserInfo lui = new localUserInfo();
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

	public List<TableStatDTO> query(int rport) throws SQLException {

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
			String qnameSQL = "select * from pg_stat_all_tables;";

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
		dto.setLastAnalyze(createZonedDateTime(rs.getDate("last_analyze")));
		dto.setLastAutoanalyze(createZonedDateTime(rs.getDate("last_autoanalyze")));
		dto.setLastAutovacuum(createZonedDateTime(rs.getDate("last_autovacuum")));
		dto.setLastVacuum(createZonedDateTime(rs.getDate("last_vacuum")));
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

	private ZonedDateTime createZonedDateTime(Date date) {
		if (date == null) {
			return null;
		} else {
			return ZonedDateTime.of(date.toLocalDate().atStartOfDay(), ZoneId.systemDefault());
		}
	}
}
