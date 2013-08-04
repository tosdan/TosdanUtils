package com.github.tosdan.utils.sql;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;

public interface DAO
{
	public BasicDAO setCloseConn( boolean closeConn );
	public BasicDAO setAutoCommit(boolean autoCommit) throws BasicDAOException;
	public void commit() throws BasicDAOException;
	public void rollBack() throws BasicDAOException;
	public void rollBack(Savepoint savepoint) throws BasicDAOException;
	public Savepoint setSavepoint() throws BasicDAOException;
	public Savepoint setSavepoint(String name) throws BasicDAOException;
	public int update( String sql ) throws BasicDAOException;
	public List<Map<String, Object>> runAndGetMapList(String sql) throws BasicDAOException;
	public Map<String, Object> runAndGetMap(String sql) throws BasicDAOException;
	public List<Object[]> runAndGetArrayList(String sql) throws BasicDAOException;
	public Object[] runAndGetArray(String sql) throws BasicDAOException;
	public Map<String, Map<String, Object>> runAndGetKeyedMap(String sql, String columnToBeKey) throws BasicDAOException;
	public Map<String, List<Map<String, Object>>> runAndGetKeyedMapList(String sql, String columnToBeKey) throws BasicDAOException ;	
	public <T> T runAndGetSomething(String sql, ResultSetHandler<T> rsh ) throws BasicDAOException;
	public void closeQuietly();
	public void close() throws SQLException;
}
