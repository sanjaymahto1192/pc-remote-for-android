package hu.za.pc_remote.jersey_webapp;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/16/11
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class DAO {
    private static final Logger log = Logger.getLogger(DAO.class);
    private static final String query = "SELECT name as name FROM layouts";


    public static List<String> getLayouts(){

        List<String> result = new ArrayList<String>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = getJNDIConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                result.add(resultSet.getString("name"));
            }

        }catch (SQLException se){
            log.error(se);
        }
        finally {
            if(resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error(e);
                }
            if(preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                   log.error(e);
                }
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                   log.error(e);
                }
        }
        return result;
    }

    /**
     * Uses JNDI and Datasource (preferred style).
     */
    static Connection getJNDIConnection() {
        String DATASOURCE_CONTEXT = "jdbc/mysqldb";

        Connection result = null;
        try {
            Context initialContext = new InitialContext();
            if (initialContext == null) {
                log.error("JNDI problem. Cannot get InitialContext.");
            }
            DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
            if (datasource != null) {
                result = datasource.getConnection();
            } else {
                log.error("Failed to lookup datasource.");
            }
        } catch (NamingException ex) {
            log.error("Cannot get connection: " + ex);
        } catch (SQLException ex) {
            log.error("Cannot get connection: " + ex);
        }
        return result;
    }
}
