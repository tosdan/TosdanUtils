package com.github.tosdan.utils.sql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * @author Daniele
 * @version 0.0.1-b2014-06-24
 */
public class ConnectionProviderImplV3 {
	private Connection conn;
	private String driver;
	private String url;
	private String username;
	private String password;
	private String dbConfFile;
	private boolean isDBConfLoaded;
	
	/**
	 * 
	 * @param dbConfFile Percorso assoluto del file di configurazione con i parametri di accesso al database.
	 */
	public ConnectionProviderImplV3(String dbConfFile) {
		this.dbConfFile = dbConfFile;
		this.isDBConfLoaded = false;
	}
	
	/**
	 * 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 */
	public ConnectionProviderImplV3( String driver, String url, String username, String password ) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.isDBConfLoaded = false;
	}

	/**
	 * Restituisce una connessione al database.
	 * @return Un oggetto {@link Connection}
	 * @throws IllegalArgumentException
	 */
	public Connection getConnection() {
		if ( ! this.isDBConfLoaded )
			caricaDBConf( this.dbConfFile );
		return connect();
		
	}
	
	/**
	 * Restituisce una connessione al database.
	 * @param dbConfFile Percorso assoluto del file di configurazione con i parametri di accesso al database.
	 * @return Un oggetto {@link Connection}
	 * @throws IllegalArgumentException
	 */
	public Connection getConnection(String dbConfFile) {
		caricaDBConf( dbConfFile );
		return connect();
		
	}
	
	/**
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	private Connection connect()  throws IllegalArgumentException {
		try {
			
			if ( conn == null || conn.isClosed() ) {
				
				try {
					
					Class.forName( driver ).newInstance();
					
				} catch ( InstantiationException e ) {
					throw new IllegalArgumentException( "Costruttore classe Driver non accessibile.", e );
				} catch ( IllegalAccessException e ) {
					throw new IllegalArgumentException( "Creazione istanza classe Driver fallita.", e );
				} catch ( ClassNotFoundException e ) {
					throw new IllegalArgumentException( "Impossibile trovare la classe del Driver", e );
				}
				
				this.conn = DriverManager.getConnection( url, username, password);
				
			}
			
		} catch ( SQLException e ) {
			throw new IllegalArgumentException( "Errore in fase di connessione.", e );
		}
		
		return this.conn;
		
	}
	

	/**
	 * 
	 * @param dbConfFile
	 * @throws IllegalArgumentException
	 */
	public void caricaDBConf( String dbConfFile ) throws IllegalArgumentException
	{
		this.dbConfFile = dbConfFile;
		this.isDBConfLoaded = true;
		Properties prop = new Properties();
		FileInputStream fis = null;
		
        try {
        	fis = new FileInputStream( dbConfFile );
			prop.load(fis);
			
		} catch ( FileNotFoundException e ) {
			throw new IllegalArgumentException( "File di configurazione DB non trovato.", e );
		} catch ( IOException e ) {
			throw new IllegalArgumentException( "Errore in accesso al file di configurazione DB.", e );
		} finally {
        	try {
				fis.close();
				
			}
			catch ( Throwable t ) {
				t.printStackTrace();
			}
        }
        
        this.driver = prop.getProperty("driver");
        this.url = (prop.getProperty("url") != null) ? prop.getProperty("url") : prop.getProperty("connection");
        this.username = prop.getProperty("username");
        this.password = prop.getProperty("password");       
	}

}
