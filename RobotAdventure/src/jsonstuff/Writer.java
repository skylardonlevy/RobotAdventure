package jsonstuff;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import engine.Vector;

public class Writer {
	
	private JsonWriter writer;
	private JsonArray[] jsonList;
	private static final File directory = new File("levels");
	
	public Writer()
	{
		
		jsonList = new JsonArray[1];
		addLevelData(1);
		writeLevel("levels/levels.json",1);
		
	}
	
	public void writeLevel(String file,int level)
	{
		try
		{
			File f = new File(file);
			if(!directory.exists())
			{
				directory.mkdir();
			}
			if(!f.exists())
			{
				f.createNewFile();
			}		
			
			writer = Json.createWriter(new FileOutputStream(f));
			writer.writeArray(jsonList[level-1]);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			writer.close();
		}
	}
	
	public void addLevelData(int level)
	{		
		//Level 1
		JsonArrayBuilder builder = Json.createArrayBuilder();
		builder.add(addPhysicsRectObject(new Rectangle(0,100,100,50),30,new Vector(5,0),5,500,1.05));	
		builder.add(addPhysicsRectObject(new Rectangle(10,300,100,200), 40, new Vector(2,0), 2, 100, 1));
		builder.add(addPhysicsTriangleObject(new int[]{10,10,100}, new int[]{100,275,100}, 10, new Vector(3,1), 10, 100, 1.0));
				
		jsonList[level-1] = builder.build();
	}
	
	
	
	public JsonObject addPhysicsRectObject(Rectangle r, double theta , Vector velocity, double torque, double mass, double dragC )
	{
		JsonObject rectangle = Json.createObjectBuilder()
				.add("x", r.x)
				.add("y", r.y)
				.add("w",r.width)
				.add("h",r.height).build();
		
		JsonObject vel = Json.createObjectBuilder()
				.add("x",velocity.XExact())
				.add("y",velocity.YExact()).build();
		
		JsonObject object = Json.createObjectBuilder()
				.add("bounds", rectangle)
				.add("theta", theta)
				.add("velocity", vel)
				.add("torque",torque)
				.add("mass",mass)
				.add("dragC",dragC).build();	
		
		return Json.createObjectBuilder().add("physicsRect"+(physicsRectCount++),object).build();
	}
	private int physicsRectCount = 0;
	
	public JsonObject addPhysicsTriangleObject(int[] xPts,int[] yPts, double theta , Vector velocity, double torque, double mass, double dragC)
	{
		JsonObject points = Json.createObjectBuilder()
				.add("x1",xPts[0])
				.add("y1",yPts[0])
				.add("x2",xPts[1])
				.add("y2",yPts[1])
				.add("x3",xPts[2])
				.add("y3",yPts[2]).build();
		
		JsonObject vel = Json.createObjectBuilder()
				.add("x",velocity.XExact())
				.add("y",velocity.YExact()).build();
		
		JsonObject object = Json.createObjectBuilder()
				.add("points", points)
				.add("theta", theta)
				.add("velocity", vel)
				.add("torque",torque)
				.add("mass",mass)
				.add("dragC",dragC).build();
		
		return Json.createObjectBuilder().add("physicsTriangle"+(physicsTriangleCount++),object).build();
				
	}
	private int physicsTriangleCount = 0;
	
//	public JsonObject addFileLocation(String level,String[] keys, String[] values)
//	{			
//		JsonObjectBuilder builder = Json.createObjectBuilder();
//		for(int i = 0; i < keys.length; i++)
//		{
//			builder.add(keys[i], values[i]);
//		}
//		return Json.createObjectBuilder().add(level, builder.build()).build();
//		
//	}
}
