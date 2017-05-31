package jsonstuff;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class DataClass {

	
	private String key;
	private TreeMap<String,DataNode> dataList;
	
	public DataClass(String key)
	{
		this.key = key;
		dataList = new TreeMap<String,DataNode>();
	}
	
	public void addData(String key1,String key2,Object value)
	{
//		if(key1 == null )
//		{
//			System.out.println("key1 = null");
//		}
//		if(key2 == null )
//		{
//			System.out.println("key2 = null");
//		}
//		if(value == null )
//		{
//			System.out.println("value = null");
//		}
//		
//		System.out.println("ADDING DATA: " + key + " " + key2 + " " + value.toString());
		if(key1 == null || key2 == null)
		{
			
			DataNode node = new DataNode();
			node.add("Value", value);
			dataList.put("",node);
		}
		else if(dataList.get(key1) == null)
		{
			
			DataNode node = new DataNode();
			node.add(key2, value);
			dataList.put(key1,node);
		}
		else
		{
//			System.out.println("KEY1: " + key1 + " ,VALUE:" + value);
			dataList.get(key1).add(key2, value);
		}
	}
	
	public String getKey(){return key;}
	
	public String toString()
	{
		String s = "\n----------------------"+ key +"---------\n" ;
		for(String str : dataList.keySet())
		{
			s += str;
			if(!str.equals("")&& !s.isEmpty() )
			{
				s+=": ";
			}
			s+= "[ " + dataList.get(str).toString() + " ]\n";
		}
		return s + "-------------------------------\n";
	}
	
	public int getIntegerValue(String objectType, String key )
	{
		DataNode node;
		
		if(objectType != null)
		{
			 node = dataList.get(objectType);
			 if(node != null)
				 return Integer.valueOf(node.getValue(key).toString());
		}else if( (node = dataList.get(this.key))!= null)
		{
			return Integer.valueOf(node.getValue("Value").toString());
		}
		
		return -1;
	}
	
	public double getDoubleValue(String objectType, String key)
	{
		DataNode node;
		
		if(objectType != null)
		{
			 node = dataList.get(objectType);
			 if(node != null)
				 return Double.valueOf(node.getValue(key).toString());
		}else if( (node = dataList.get(this.key))!= null)
		{
			return Double.valueOf( node.getValue("Value").toString() );
		}
		
		return -1.0;
	}
	
	public String getStringValue(String objectType, String key )
	{
		DataNode node;
		
		if(objectType != null)
		{
			 node = dataList.get(objectType);
			 if(node != null)
				 return node.getValue(key).toString();
		}else if( (node = dataList.get(this.key))!= null)
		{
			return node.getValue(key).toString();
		}
		
		return null;
	}
	
	public ArrayList<String> getStringValues(String objectType)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		if(objectType != null)
		{
			
			DataNode node = dataList.get(objectType);
			
			Set<String> keys = node.getDataMap().keySet();
			
			for(String s : keys)
			{
				String n = s + "=" + node.getDataMap().get(s).toString();
				list.add(n);
			}
			
			return list;
		}
		else
		{
			DataNode node = dataList.get(this.key);
			Set<String> keys = node.getDataMap().keySet();
			for(String s : keys)
			{
				String n = s + "=" + node.getDataMap().get(s).toString();
				list.add(n);
			}
		}
		return list;
		
	}
	
	class DataNode
	{
		
		
		private TreeMap<String,Object> dataMap;
		
		public DataNode()
		{
			dataMap = new TreeMap<String,Object>();
		}
		
		public void add(String key, Object value)
		{
			if(dataMap.get(key) == null)
				dataMap.put(key,value);
		}
		
		
		//public String getKeys(){return key;}
		public Object getValue(String s){return dataMap.get(s);}
		
		public String toString()
		{
			String s = "";
			String[] str = new String[dataMap.keySet().size()]; 
			dataMap.keySet().toArray(str);
			for(int i  = 0; i < str.length-1; i++)
			{		
				s += str[i];
				if(!s.equals("") && !s.isEmpty())
					s+= ": ";
				s+= dataMap.get(str[i]).toString() +  " , ";
			}
			return s + str[str.length-1]+ ": " + dataMap.get(str[str.length-1]).toString();
		}
		
		public TreeMap<String,Object> getDataMap()
		{
			return dataMap;
		}
	}
}
