package DB;



import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.apache.log4j.Logger;




import properties_def.Access_property;
import properties_def.Properties_assign;
import properties_def.Property_validate;

public class ConnectionPll {

	
	
	
	
	
	private static Logger logger = Logger.getLogger(ConnectionPll.class);

	private static ConnectionPll instance = new ConnectionPll();

	public static ConnectionPll getInstance() {
		return instance;
	}

	private ComboPooledDataSource connectionPool;

	public void init() throws SQLException {
		connectionPool = new ComboPooledDataSource();

		logger.debug("Setting connection pool parameters");
		try {
			connectionPool.setDriverClass(Access_property
					.getProperty(Properties_assign.RAWOBJECTS_DB_DRIVER));
		} catch (PropertyVetoException e) {
			logger.error(
					"Couldn't create connection pool, error while setting pool driver",
					e);
			logger.error(e);
			return;
		}
		connectionPool.setJdbcUrl(Access_property
				.getProperty(Properties_assign.RAWOBJECTS_DB_URL));
		connectionPool.setUser(Access_property
				.getProperty(Properties_assign.RAWOBJECTS_DB_USER));
		connectionPool.setPassword(Access_property
				.getProperty(Properties_assign.RAWOBJECTS_DB_PASSWORD));
		connectionPool.setMinPoolSize(1);
		connectionPool.setAcquireIncrement(5);
		connectionPool.setMaxPoolSize(5);
		connectionPool.setAcquireRetryAttempts(1);

		logger.debug("Driver: "
				+ Access_property
						.getProperty(Properties_assign.RAWOBJECTS_DB_DRIVER));
		logger.debug("Url: "
				+ Access_property
						.getProperty(Properties_assign.RAWOBJECTS_DB_URL));
		logger.debug("User: "
				+ Access_property
						.getProperty(Properties_assign.RAWOBJECTS_DB_USER));

		// Try to get one connection
		Connection conn = null;
		try {
			logger.debug("Trying to get one connection");
			connectionPool.getConnection();
			logger.debug("Connection acquired successfully");

		} catch (SQLException e) {
			throw new SQLException(
					"Unable to get connection and start connection pool. Reason is: "
							+ e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}

		}
	}

	public Connection getConnection() throws SQLException {
		Connection conn = connectionPool.getConnection();
		return conn;
	}

	public void stopGracefully() {
		connectionPool.close();
	}
}
