package com.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.model.Inventory;
import com.model.Warehouse;

public class WarehouseManagement {

	public boolean addWarehouse(Warehouse warehouse) throws ClassNotFoundException, SQLException {
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("INSERT INTO warehouse VALUES(?, ?, ?, ?, ?)");
			){
			
			st.setString(1, warehouse.getWarehouseId());
			st.setString(2, warehouse.getWarehouseName());
			st.setString(3, warehouse.getLocation());
			st.setInt(4, warehouse.getCapacity());
			st.setInt(5, warehouse.getCurrentCapacity());
			
			return st.executeUpdate()>0;
		}
	}
	
	public boolean deleteWarehouse(String warehouseId) throws ClassNotFoundException, SQLException {
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("DELETE FROM warehouse WHERE LOWER(warehouseId) = LOWER(?)");
			){
			
			st.setString(1, warehouseId);
			
			return st.executeUpdate()>0;
		}
	}
	
	public boolean updateWarehouse(Warehouse warehouse) throws ClassNotFoundException, SQLException {
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("UPDATE warehouse SET warehouseName = ?, location = ?, capacity = ? WHERE LOWER(warehouseId) = LOWER(?)");
			){
			
			st.setString(1, warehouse.getWarehouseName());
			st.setString(2, warehouse.getLocation());
			st.setInt(3, warehouse.getCapacity());
			st.setString(4, warehouse.getWarehouseId());
			
			return st.executeUpdate()>0;
		}
	}
	
	public ArrayList<Warehouse> viewWarehouse() throws ClassNotFoundException, SQLException {
		ArrayList<Warehouse> list = new ArrayList<Warehouse>();
		
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM warehouse");
			){
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Warehouse wh = new Warehouse(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				wh.setCurrentCapacity(rs.getInt(5));
				
				list.add(wh);
			}
			return list;
		}
	}
	
	public ArrayList<Warehouse> searchWarehouseById(String warehouseId) throws ClassNotFoundException, SQLException{
		ArrayList<Warehouse> list = new ArrayList<Warehouse>();
		
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM warehouse WHERE LOWER(warehouseId) = LOWER(?)")
			){
			
			st.setString(1, warehouseId);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Warehouse wh = new Warehouse(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				wh.setCurrentCapacity(rs.getInt(5));
				
				list.add(wh);
			}
			return list;
		}
	}
	
	public ArrayList<Warehouse> searchWarehouseByName(String warehouseName) throws ClassNotFoundException, SQLException{
		ArrayList<Warehouse> list = new ArrayList<Warehouse>();
		
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM warehouse WHERE LOWER(warehouseName) LIKE  LOWER(?)")
			){
			
			st.setString(1,  "%"+warehouseName+"%");
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Warehouse wh = new Warehouse(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				wh.setCurrentCapacity(rs.getInt(5));
				
				list.add(wh);
			}
			return list;
		}
	}
	
	public boolean addInventory(String warehouseId, String inventoryId) throws ClassNotFoundException, SQLException {
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("INSERT INTO warehouse_storage VALUES(?, ?)");
			){
			
			st.setString(1, warehouseId);
			st.setString(2, inventoryId);
			
			return st.executeUpdate()>0;
			
		}
	}
	
	public boolean deleteInventory(String warehouseId, String inventoryId) throws ClassNotFoundException, SQLException {
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("DELTE FROM warehouse_storage WHERE LOWER(warehouseId) = LOWER(?) AND LOWER(inventoryId) = LOWER(?)");
			){
			
			st.setString(1, warehouseId);
			st.setString(2, inventoryId);
			
			return st.executeUpdate()>0;
			
		}
	}
	
	public ArrayList<Inventory> viewInventoryDetails(String warehouseId) throws ClassNotFoundException, SQLException{
		
		ArrayList<Inventory> list = new ArrayList<Inventory>();
		
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("select inventoryId, productId, productName, unitPrice, quntityInStock, lastStockUpdate from warehouse_storage natural join warehouse natural join inventory natural join products where LOWER(warehouseId) = LOWER(?)");
			){
			
			st.setString(1, warehouseId);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				String inventoryId = rs.getString("inventoryId");
				
				
			}
			return list;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	public boolean addCapacity(String warehouseId, int capacity) throws ClassNotFoundException, SQLException {
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("UPDATE FROM warehouse SET currentCapacity = currentCapacity + ? WHERE LOWER(warehouseId) = LOWER(?)");
			){
			
			st.setInt(1, capacity);
			st.setString(2, warehouseId);
			
			return st.executeUpdate()>0;
		}
	}
	
	public boolean deleteCapacity(String warehouseId, int capacity) throws ClassNotFoundException, SQLException {
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("UPDATE FROM warehouse SET currentCapacity = currentCapacity - ? WHERE LOWER(warehouseId) = LOWER(?)")
			){
			
			st.setInt(1, capacity);
			st.setString(2, warehouseId);
			
			return st.executeUpdate()>0;
			
		}
	}
	
	public boolean checkingWarehouse(String warehouseName, String location) throws ClassNotFoundException, SQLException {
		
		try(
				Connection con = DBConnection.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM warehouse WHERE LOWER(warehouseName) = LOWER(?) AND LOWER(location) = LOWER(?)");
			){
			
			st.setString(1, warehouseName);
			st.setString(2, location);
			
			return st.executeQuery().next();
			
		} 
	}
	

}
