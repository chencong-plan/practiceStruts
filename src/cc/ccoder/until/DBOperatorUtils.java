package cc.ccoder.until;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author    :  chencong
 * Time      :  2017/9/5 18:53
 * Package   :  cc.ccoder.until
 * Describe  :  TODO
 */
public class DBOperatorUtils {

    /**
     * 查询单条记录
     *
     * @param sql    将要执行的SQL语句
     * @param params 该SQL语句当中的参数列表
     * @param clazz  返回对象的类类型
     * @return 返回该类对象的实际类型
     */
    public static <T> T getSimpleResult(String sql, List<Object> params, Class<T> clazz) {
        Connection connection = DBUtils.getConnection();
        if (connection == null) {
            return null;
        }
        if (sql == null || clazz == null) {
            return null;
        }
        T resultObjects = null;
        int index = 1;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = connection.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pStatement.setObject(index, params.get(i));
                    index++;
                }
            }
            resultSet = pStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colLength = metaData.getColumnCount();
            if (resultSet.next()) {
                resultObjects = clazz.newInstance();
                for (int i = 0; i < colLength; i++) {
                    String colName = metaData.getColumnName(i + 1);
                    Object colValue = resultSet.getObject(colName);
                    if (colValue == null) {
                        colValue = null;
                    }
                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(resultObjects, colValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null || pStatement != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultObjects;
    }

    /**
     * 查询多条记录
     *
     * @param sql    要执行的SQL语句
     * @param params 该SQL语句当中的参数列表
     * @param clazz  返回lists当中对象的类类型
     * @return 返回执行结果的list
     */
    public static <T> List<T> getMoreResult(String sql, List<Object> params, Class<T> clazz) {
        Connection connection = DBUtils.getConnection();
        if (connection == null) {
            return null;
        }
        if (sql == null || clazz == null) {
            return null;
        }
        List<T> lists = new ArrayList<>();
        int index = 1;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = connection.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pStatement.setObject(index, params.get(i));
                    index++;
                }
            }
            resultSet = pStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colLength = metaData.getColumnCount();
            while (resultSet.next()) {
                T resultObject = clazz.newInstance();
                for (int i = 0; i < colLength; i++) {
                    String cloName = metaData.getColumnName(i + 1);
                    Object cloValue = resultSet.getObject(cloName);
                    if (cloValue == null) {
                        cloValue = null;
                    }
                    Field field = clazz.getDeclaredField(cloName);
                    field.setAccessible(true);
                    field.set(resultObject, cloValue);
                }
                lists.add(resultObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null || pStatement != null) {
                    resultSet.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    public static <T> List<T> getMoreResultProcByPage(String sql, List<Object> params, Class<T> clazz) {
        Connection connection = DBUtils.getConnection();
        if (connection == null) {
            return null;
        }
        if (sql == null || clazz == null) {
            return null;
        }
        List<T> lists = new ArrayList<>();
        int index = 1;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            callableStatement = connection.prepareCall(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    callableStatement.setObject(index, params.get(i));
                    index++;
                }
            }
            boolean result = callableStatement.execute();
            if (result) {
                //有结果集
                resultSet = callableStatement.getResultSet();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int colLength = metaData.getColumnCount();
                while (resultSet.next()) {
                    T resultObject = clazz.newInstance();
                    for (int i = 0; i < colLength; i++) {
                        String cloName = metaData.getColumnName(i + 1);
                        Object cloValue = resultSet.getObject(cloName);
                        if (cloValue == null) {
                            cloValue = null;
                        }
                        Field field = clazz.getDeclaredField(cloName);
                        field.setAccessible(true);
                        field.set(resultObject, cloValue);
                    }
                    lists.add(resultObject);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    /**
     * 执行增删改SQL语句
     *
     * @param sql    将要执行的SQL语句
     * @param params 该条SQL语句当中的参数
     * @return 返回是否执行成功
     */
    public static boolean executeUpdateResult(String sql, List<Object> params) {
        Connection connection = DBUtils.getConnection();
        if (connection == null) {
            return false;
        }
        if (sql == null || params == null || params.isEmpty()) {
            return false;
        }
        int num = 0;
        int index = 1;
        PreparedStatement pStatement = null;
        try {
            pStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                pStatement.setObject(index, params.get(i));
                index++;
            }
            num = pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pStatement != null) {
                    pStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return num > 0 ? true : false;
    }


    /**
     * 执行新增操作时候返回当前新增产生的自增id主键
     *
     * @param sql    将要执行的sql语句
     * @param params 当前sql语句的参数
     * @return 返回执行sql语句返回的主键
     */
    public static Integer executeUpdateResultKeys(String sql, List<Object> params) {
        Integer id = null;
        Connection connection = DBUtils.getConnection();
        if (connection == null) {
            return null;
        }
        if (sql == null || params == null || params.isEmpty()) {
            return null;
        }
        int result;
        int index = 1;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.size(); i++) {
                pStatement.setObject(index, params.get(i));
                index++;
            }
            result = pStatement.executeUpdate();
            if (result > 0) {
                resultSet = pStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (pStatement != null || resultSet != null) {
                    pStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }
}
