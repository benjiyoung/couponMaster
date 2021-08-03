package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

//we handle all the connection no more then 10.....
public class ConnectionPool {
    private static final int NUM_OF_CONS = 10;
    private static ConnectionPool instance = null;
    private Stack<Connection> connections = new Stack<>();

    private ConnectionPool() throws SQLException {
        //open all connections
        openAllConnections();
    }

    public static ConnectionPool getInstance() {
        //before locking the critical code...
        if (instance == null) {
            //create the connection pool
            synchronized (ConnectionPool.class) {
                //before creating the code.....
                if (instance == null) {
                    try {
                        instance = new ConnectionPool();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException{
        synchronized (connections){
            if (connections.isEmpty()){
                //wait until we will get a connection back
                connections.wait();
            }
            return connections.pop();
        }
    }

    public void returnConnection(Connection connection){
        synchronized (connections){
            connections.push(connection);
            //notify that we got back a connection from the user...
            connections.notify();
        }
    }

    private void openAllConnections() throws SQLException{
        for (int index=0;index < NUM_OF_CONS;index+=1){
            //load the configuration file and update the data for connection
            //DatabaseManager.getConfiguration();
            //make the connection ......
            Connection connection = DriverManager.getConnection(DatabaseManager.url,DatabaseManager.username,DatabaseManager.password);
            connections.push(connection);
        }
    }

    public void closeAllConnection() throws InterruptedException{
        synchronized (connections){
            while (connections.size()<NUM_OF_CONS){
                connections.wait();
            }
            connections.removeAllElements();
            instance = null;
        }
    }
}