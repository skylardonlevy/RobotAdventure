package jsonstuff;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import engine.PhysicsPoly;
import engine.Vector;
import entity.PhysicsRect;
import entity.PhysicsTriangle;

public class Reader {

	
	private ArrayList<DataClass> dataList = new ArrayList<DataClass>();
	private JsonReader reader;
	
	public Reader()
	{
		
	}
	
	public void read(String path){
		
		try {
			reader = Json.createReader(new FileInputStream(path));

			JsonArray array = reader.readArray();

			for(int i = 0; i < array.size(); i++)
			{
				JsonObject object;
				object = array.getJsonObject(i); //Main entire object

				Set<Entry<String,JsonValue>> set = object.entrySet();

				for(Entry<String,JsonValue> e : set)
				{
					Entry<String,JsonValue> entry = e;

					String key = entry.getKey();
					JsonValue val = entry.getValue();

					DataClass data = new DataClass(key);

					if(val instanceof JsonObject)
					{
						JsonObject obj = (JsonObject)val;

						fillData(key,obj,data);

					}else if(val instanceof JsonArray)
					{
						JsonArray arr = (JsonArray)val;
						fillData(key,arr,data);
					}else
					{
						data.addData(null,null, val);
					}

					dataList.add(data);

				}
			}


			System.out.println(dataList);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally
		{
			reader.close();
		}
	}
	
	public ArrayList<String> readFileLocations(String filePath)
	{
		ArrayList<String> fileLocations = new ArrayList<String>();
		
		fileLocations.addAll(getFileLocations());
		
		return fileLocations;
	}
	
	public ArrayList<PhysicsPoly> getPhysicsObjects(String filePath)
	{
		read("levels/levels.json");//!!!!
		ArrayList<PhysicsPoly> physicsObjects = new ArrayList<PhysicsPoly>();
		physicsObjects.addAll(getPhysicsRectObjects());
		physicsObjects.addAll(getPhysicsTriangleObjects());
		return physicsObjects;
	}
	
	public int getIndex(String key)
	{
		for(int i = 0; i < dataList.size(); i++)
			if(dataList.get(i).getKey().equals(key))
				return i;
		return -1;
	}
	
	public ArrayList<PhysicsRect> getPhysicsRectObjects()
	{
		ArrayList<PhysicsRect> list = new ArrayList<PhysicsRect>();
		
		for(int i = 0; i < dataList.size(); i++)
		{
			if(dataList.get(i).getKey().contains("physicsRect"))
			{
				DataClass obj = dataList.get(i);
				Rectangle r = new Rectangle();
				r.x = obj.getIntegerValue("bounds","x");
				r.y = obj.getIntegerValue("bounds","y");
				r.width = obj.getIntegerValue("bounds","w");
				r.height = obj.getIntegerValue("bounds","h");
				
				double vX = obj.getDoubleValue("velocity", "x");
				double vY = obj.getDoubleValue("velocity", "y");
				Vector vel = new Vector(vX,vY);
				
				double torque = obj.getDoubleValue("others", "torque");
				double mass = obj.getDoubleValue("others", "mass");
				double theta = obj.getDoubleValue("others", "theta");
				double dragC = obj.getDoubleValue("others", "dragC");
				
				list.add(new PhysicsRect(r, theta, vel, torque, mass, dragC));
			}
		}
		return list;
	}
	
	public ArrayList<PhysicsTriangle> getPhysicsTriangleObjects()
	{
		ArrayList<PhysicsTriangle> list = new ArrayList<PhysicsTriangle>();
		
		for(int i = 0; i < dataList.size(); i++)
		{
			if(dataList.get(i).getKey().contains("physicsTriangle"))
			{
				DataClass obj = dataList.get(i);
				
				int[] xPts = new int[3];
				int[] yPts = new int[3];
				
				xPts[0] = obj.getIntegerValue("points", "x1");
				xPts[1] = obj.getIntegerValue("points", "x2");
				xPts[2] = obj.getIntegerValue("points", "x3");
				
				yPts[0] = obj.getIntegerValue("points", "y1");
				yPts[1] = obj.getIntegerValue("points", "y2");
				yPts[2] = obj.getIntegerValue("points", "y3");
				
				
				double vX = obj.getDoubleValue("velocity", "x");
				double vY = obj.getDoubleValue("velocity", "y");
				Vector vel = new Vector(vX,vY);
				
				double torque = obj.getDoubleValue("others", "torque");
				double mass = obj.getDoubleValue("others", "mass");
				double theta = obj.getDoubleValue("others", "theta");
				double dragC = obj.getDoubleValue("others", "dragC");
				
				list.add(new PhysicsTriangle(xPts,yPts, theta, vel, torque, mass, dragC));
			}
		}
		return list;
	}
	
	public ArrayList<String> getFileLocations()
	{
		ArrayList<String> fileLocations = new ArrayList<String>();
		
		for(int i = 0; i < dataList.size(); i++)
		{
			
		}
		return fileLocations;
	}
	
	/*
	 * Fills data recursively into DataClass objects 
	 *
	 */
	
	public void fillData(String key, JsonObject obj, DataClass data)
	{

		for(String s : obj.keySet())
		{
			JsonValue val = obj.get(s);

			if(val instanceof JsonObject)
			{

				JsonObject jo = (JsonObject)val;
				if(jo.size() > 1)
					fillData(s,jo,data);
			}
			else if(val instanceof JsonArray)
			{
				JsonArray ja = (JsonArray)val;
				fillData(s,ja, data);
			}else
			{
				if(key.equalsIgnoreCase(data.getKey()))
					key = "others";
				data.addData(key, s, val);
			}
		}
		
	}
	public void fillData(String key, JsonArray arr, DataClass data)
	{
		for(int i = 0; i < arr.size(); i++)
		{
			fillData(key,arr.getJsonObject(i),data);
		}
		
	}
	
}
