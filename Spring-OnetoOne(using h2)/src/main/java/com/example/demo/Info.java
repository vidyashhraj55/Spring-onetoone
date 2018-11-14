package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Function;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class Info {
	@Autowired
	DataSource datasource;

	@GetMapping("db/info")
	public ResponseEntity<?> info() throws SQLException
	{
		HashMap<String, String> map = new HashMap<String, String>();
		try(Connection connection = datasource.getConnection();)
		{
			DatabaseMetaData dmd = connection.getMetaData();
			String url = dmd.getURL();
			String userName = dmd.getUserName();
			String databaseProductName = dmd.getDatabaseProductName();
			String databaseProductVersion = dmd.getDatabaseProductVersion();
			map.put("url", url);
			map.put("userName", userName);
			map.put("databaseProductName", databaseProductName);
			map.put("databaseProductVersion", databaseProductVersion);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("db/schemas")
	public void schemas(HttpServletResponse response) throws SQLException, IOException
	{
		CheckedFunction<DatabaseMetaData, ResultSet> fn= p -> p.getSchemas();
		util(response, fn);
	}
	
	@GetMapping("db/catalogs")
	public void catalogs(HttpServletResponse response) throws SQLException, IOException
	{
		CheckedFunction<DatabaseMetaData, ResultSet> fn= p -> {return p.getCatalogs();};
		util(response, fn);
	}
	
	
	@GetMapping("db/tables")
	public void tables(HttpServletResponse response) throws SQLException, IOException
	{
		CheckedFunction<DatabaseMetaData, ResultSet> fn= p -> {return p.getTables(null, null, null, null);};
		util(response, fn);
	}
	
	@GetMapping("db/columns")
	public void columns(@RequestParam("tableName") String tableName, HttpServletResponse response) throws SQLException, IOException
	{
		CheckedFunction<DatabaseMetaData, ResultSet> fn= p -> p.getColumns(null, null, tableName.toUpperCase(), null);
		util(response, fn);
	}
	
	@GetMapping("db/importedKeys")
	public void importedKeys(@RequestParam("tableName") String tableName, HttpServletResponse response) throws SQLException, IOException
	{
		CheckedFunction<DatabaseMetaData, ResultSet> fn= p -> p.getImportedKeys(null, null, tableName.toUpperCase());
		util(response, fn);
	}
	

	@GetMapping("db/x")
	public void x(@RequestParam("tableName") String tableName, HttpServletResponse response) throws SQLException, IOException
	{
		CheckedFunction<DatabaseMetaData, ResultSet> fn= p -> p.getIndexInfo(null, null, tableName.toUpperCase(), true, false);
		util(response, fn);
	}
	
	@GetMapping("db/exportedKeys")
	public void exportedKeys(@RequestParam("tableName") String tableName, HttpServletResponse response) throws SQLException, IOException
	{
		CheckedFunction<DatabaseMetaData, ResultSet> fn= p -> p.getExportedKeys(null, null, tableName.toUpperCase());
		util(response, fn);
	}

	private void util(HttpServletResponse response, CheckedFunction<DatabaseMetaData, ResultSet> fn) throws IOException, SQLException {
		response.setStatus(200);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try(Connection connection = datasource.getConnection();)
		{
			DatabaseMetaData dmd = connection.getMetaData();
			
			ResultSet rs = getRs(dmd, fn);
			showRs(out, rs);
		}
	}

	private ResultSet getRs(DatabaseMetaData dmd, CheckedFunction<DatabaseMetaData, ResultSet> fn) throws SQLException {
		return fn.apply(dmd);
	}

	private void showRs(PrintWriter out, ResultSet rs) throws SQLException {
		out.println("<html><body>");
		out.println("<table border='1'>");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		out.print("<tr>");
		for (int i = 1; i <= columnCount; i++) 
		{
			out.print("<th>");
			out.print(rsmd.getColumnName(i));
			out.print("</th>");
		}
		out.print("</tr>");
		while(rs.next())
		{
			out.print("<tr>");
			for (int i = 1; i <= columnCount; i++) 
			{
				out.print("<td>");
				out.print(rs.getObject(i));
				out.print("</td>");
			}
			out.print("</tr>");
		}
		out.println("</table>");
		out.println("</body><html>");
	}
}