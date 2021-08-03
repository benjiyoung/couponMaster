package DB;

import java.sql.*;
import java.time.LocalDate;
import java.util.Map;

public class DBUtilities {
    private static Connection connection;

    /**
     * A method that takes in an SQL query and executes it
     * @param SQLQuery the desired SQL query String
     * @return An indication whether the action was successful or not
     */
    public static boolean runQuery(String SQLQuery) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQuery);
            statement.execute();
            return true;
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * A method that takes in an SQL query, executes it and returns a resultset
     * @param SQLQuery the desired SQL query
     * @return The received resultset from the database
     */

    public static ResultSet runQueryWithResult(String SQLQuery) {
        ResultSet result = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQuery);
            result = statement.executeQuery();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return result;
    }

    /**
     * A method that takes in a map and an SQL query, sets up the statement by checking types of the parameters, and excecutes the query.
     * @param SQLQuery String of the desired query
     * @param parameters Map that contains all parameters needed
     * @return An indication whether the action was successful or not
     */
    public static boolean runQueryWithMap(String SQLQuery, Map<Integer, Object> parameters) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQuery);
            parameters.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (int) value);
                    } else if (value instanceof String) {
                        statement.setString(key, (String) value);
                    } else if (value instanceof LocalDate) {
                        statement.setDate(key, java.sql.Date.valueOf((LocalDate) value));
                    } else if (value instanceof Boolean) {
                        statement.setBoolean(key, (Boolean) value);
                    } else if (value instanceof Double) {
                        statement.setDouble(key, (Double) value);
                    } else if (value instanceof Float) {
                        statement.setFloat(key, (Float) value);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            statement.execute();
            return true;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * A method that takes in a map and an SQL query, sets up the statement by checking types of the parameters, and executes the query
     * @param SQLQuery String of the desired SQL query
     * @param parameters Map that contains all parameters needed
     * @return the resultset received from the database
     */
    public static ResultSet runQueryWithMapAndResult(String SQLQuery, Map<Integer, Object> parameters) {
        ResultSet result = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQuery);
            parameters.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (int) value);
                    } else if (value instanceof String) {
                        statement.setString(key, (String) value);
                    } else if (value instanceof LocalDate) {
                        statement.setDate(key, java.sql.Date.valueOf((LocalDate) value));
                    } else if (value instanceof Boolean) {
                        statement.setBoolean(key, (Boolean) value);
                    } else if (value instanceof Double) {
                        statement.setDouble(key, (Double) value);
                    } else if (value instanceof Float) {
                        statement.setFloat(key, (Float) value);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            result = statement.executeQuery();

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return result;
    }

    /**
     * A method that takes in a map of parameters, SQL query, and is made to return an int value based on both
     * @param SQLQuery the desired SQL query
     * @param parameters Map of all parameters needed
     * @return the int value returned from the database
     */
    public static int runQueryInt(String SQLQuery, Map<Integer, Object> parameters) {
        int total = 0;
        ResultSet result = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQuery);
            parameters.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (int) value);
                    } else if (value instanceof String) {
                        statement.setString(key, String.valueOf(value));
                    } else if (value instanceof LocalDate) {
                        statement.setDate(key, java.sql.Date.valueOf((LocalDate) value));
                    } else if (value instanceof Boolean) {
                        statement.setBoolean(key, (boolean) value);
                    } else if (value instanceof Double) {
                        statement.setDouble(key, (double) value);
                    } else if (value instanceof Float) {
                        statement.setFloat(key, (float) value);
                    }
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            });
            result = statement.executeQuery();
            while (result.next()) {
                total = result.getInt(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return total;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return total;
    }
}