package com.bisa.cam.datasources;


import com.bisa.cam.business.repositories.TasaCambioRepository;
import com.bisa.cam.domain.Cuenta;
import com.bisa.cam.domain.TasaCambio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

import org.springframework.beans.factory.annotation.Value;

@Component
public class ConexionMysql implements TasaCambioRepository {

    private final Logger logger = LogManager.getLogger(getClass());

    @Value("${base-datos.mysql.user}")
    private String usuario;

    @Value("${base-datos.mysql.password}")
    private String password;

    @Value("${base-datos.mysql.urljdbc}")
    private String urljdbc;




    @Override
    public TasaCambio consultarTasaCambio(Date fecha, String moneda) throws Exception {
        logger.info("user:{} pwd:{} url:{}", usuario, password, urljdbc);
        String query = "SELECT id,canal, fecha, moneda, cambioOficial,\n" +
                " cambioCompra, cambioVenta, estado\n" +
                " FROM dbtasas.tasa_cambio\n" +
                " where date(fecha)= date(?)\n" +
                " and moneda = ?";
        logger.info("CONSULTANDO >>>>" +  " MYSQL:");

        DataSource dataSource = getDataSource(usuario, password, urljdbc);
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Boolean feriado = false;
        TasaCambio tasa = null;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(query);
            cs.setDate(1, fecha);
            cs.setString(2, moneda);

            logger.info("CONSULTANDO >>>>" +  " SQL:" + cs);
            rs = cs.executeQuery();
            while (rs.next()) {
                logger.info("RESULTADO SQL>>>>" + rs.getString(1));
                tasa = new TasaCambio();
                tasa.setId(rs.getLong("id"));
                tasa.setCanal(rs.getString("canal"));
                tasa.setEstado(rs.getString("estado"));
                tasa.setMoneda(rs.getString("moneda"));
                tasa.setCambioOficial(rs.getDouble("cambioOficial"));
                tasa.setCambioCompra(rs.getDouble("cambioOficial"));
                tasa.setCambioOficial(rs.getDouble("cambioCompra"));
                tasa.setCambioOficial(rs.getDouble("cambioVenta"));
                logger.info("OBJETO CUENTA>>>>" + tasa);
            }

        } catch (SQLException e) {
            logger.info("<SQLException>>>>>", e);
            throw new Exception(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return tasa;
    }

    @Override
    public Boolean registrarTasaCambio(TasaCambio tasa) throws Exception {
        String query = "INSERT INTO dbtasas.tasa_cambio\n" +
                " (canal, fecha, moneda,  cambioOficial,\n" +
                " cambioCompra,  cambioVenta,  estado)\n" +
                " VALUES ('ATM', ?, ?, ?, ?, ?, 'A')";

        logger.info("CONSULTANDO >>>>" +  " MYSQL:");

        DataSource dataSource = getDataSource(usuario, password, urljdbc);
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Boolean registrado = false;
        try {
            con = dataSource.getConnection();
            cs = con.prepareCall(query);
            cs.setTimestamp(1, tasa.getFecha());
            cs.setString(2, tasa.getMoneda());
            cs.setDouble(3, tasa.getCambioOficial());
            cs.setDouble(4, tasa.getCambioCompra());
            cs.setDouble(5, tasa.getCambioVenta());
            logger.info("CONSULTANDO >>>>" +  " SQL:" + cs);
            int res = cs.executeUpdate();
            if(res>0) registrado = true;

        } catch (SQLException e) {
            logger.info("<SQLException>>>>>", e);
            throw new Exception(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return registrado;
    }


    public static DataSource getDataSource(final String user, final String pwd, final String connectionUrl) {

        LogManager.getLogger("Conecatando a BD con usuario: {"+user+"} passwoed: {"+pwd+"}");
        LogManager.getLogger().info("Obteniendo DataSource...");

        PoolConfiguration config = new PoolProperties();
        config.setMaxActive(100);
        config.setMaxIdle(10);
        config.setMinIdle(5);
        //final String connectionUrl = "jdbc:mysql://localhost:3306/dbcuentas";
        //final String connectionUrl = "jdbc:mysql://172.19.0.2:3306/dbcuentas";
        LogManager.getLogger().info("URL={}", connectionUrl);
        config.setUrl(connectionUrl);
        config.setUsername(user);
        config.setPassword(pwd);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(config);
        return dataSource;
    }


}
