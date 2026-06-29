package mx.edu.utez.integradora_poo_2026.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class SqlConnection {

    private static HikariDataSource dataSource;

    static {
        try {
            // 1. Localizar la carpeta de la wallet de forma dinámica usando el ClassLoader
            ClassLoader classLoader = SqlConnection.class.getClassLoader();
            URL walletUrl = classLoader.getResource("wallet/");

            if (walletUrl == null) {
                throw new RuntimeException("No se encontró la carpeta 'wallet' en los recursos del proyecto (classpath).");
            }

            // Convertir URL a un path de sistema de archivos compatible
            String walletPath = new File(walletUrl.toURI()).getAbsolutePath();
            walletPath = walletPath.replace("\\", "/");

            // 2. Configurar HikariCP
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("oracle.jdbc.OracleDriver");

            config.setJdbcUrl("jdbc:oracle:thin:@veterinaria_high?TNS_ADMIN=" + walletPath);

            // Credenciales de tu base de datos en Oracle Cloud
            config.setUsername("USER");
            config.setPassword("yUgCqhKvpqK4cT5");

            // Configuración óptima del Pool para Hikari
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(20000);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);
            System.out.println("Hikari Pool inicializado exitosamente apuntando a Oracle Cloud.");

        } catch (Exception e) {
            System.err.println("Error crítico al inicializar el Pool de Hikari:");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    // Método para obtener una conexión del pool
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Método para cerrar el pool limpiamente cuando la app se detenga
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}