package net.kaneka.planttech2.datagen.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class JsonFileConverter
{
	
	private static final Gson gson = new GsonBuilder().create(); 
	
	public static void act()
	{
		parseModel("planttech2:models/block/electric_fences/electric_fence_gate_opened.json");
		parseModel("planttech2:models/block/electric_fences/electric_fence_gate_top_opened.json");
		parseModel("planttech2:models/block/electric_fences/electric_fence_gate_top.json");
		parseModel("planttech2:models/block/electric_fences/electric_fence_gate.json");
		parseModel("planttech2:models/block/electric_fences/electric_fence_post.json");
		parseModel("planttech2:models/block/electric_fences/electric_fence_powered.json");
		parseModel("planttech2:models/block/electric_fences/electric_fence_side_powered.json");
		parseModel("planttech2:models/block/electric_fences/electric_fence_side.json");
		parseModel("planttech2:models/block/electric_fences/electric_fence_top_powered.json");
		parseModel("planttech2:models/block/electric_fences/electric_fence_top.json");
		parseModel("planttech2:models/block/electric_fences/electric_fence.json");
		
		
		/*
		parseModel("planttech2:models/block/basic/crops.json");
		parseModel("planttech2:models/block/basic/carver_block_base.json"); 
		parseModel("planttech2:models/block/basic/crops.json"); 
		parseModel("planttech2:models/block/basic/door_item.json"); 
		parseModel("planttech2:models/block/basic/door_top_hinged.json"); 
		parseModel("planttech2:models/block/basic/door_top.json"); 
		parseModel("planttech2:models/block/basic/door.json"); 
		parseModel("planttech2:models/block/basic/fence_core.json"); 
		parseModel("planttech2:models/block/basic/fence_west.json"); 
		parseModel("planttech2:models/block/basic/glasspane_cross.json"); 
		parseModel("planttech2:models/block/basic/glasspane_end.json"); 
		parseModel("planttech2:models/block/basic/glasspane_middle.json"); 
		parseModel("planttech2:models/block/basic/machineshell_infused.json"); 
		parseModel("planttech2:models/block/basic/machineshell.json"); 
		parseModel("planttech2:models/block/basic/straight_tube.json"); 
		parseModel("planttech2:models/block/basic/three_layered_six_sided_nubs.json"); 
		parseModel("planttech2:models/block/basic/three_layered_six_sided.json"); 
		parseModel("planttech2:models/block/basic/two_layered_six_sided.json"); 
		*/
	}
	
	public static void parseModel(String path)
	{
		List<String> output = new ArrayList<String>();  
		
		String filename = path.substring(path.lastIndexOf("/")+1).replace(".json", ""); 
		String pathShort = path.replace("planttech2:models/", "").replace(".json", "");
		JsonObject jObj = getJsonObject(path); 
		
		if(jObj != null)
		{
			output.add("BlockModelBuilder " + filename + " = models().getBuilder(\"" + pathShort + "\")");
		
		
    		if(jObj.has("parent"))
    		{
    			output.add(".parent(carver_base_block)"); 
    				
    		}
    		
    		//output.add(filename); 
    		
    		if(jObj.has("textures"))
    		{
    			
    			for(Entry<String, JsonElement> entry :jObj.get("textures").getAsJsonObject().entrySet())
    			{
    				output.add(".texture(\"" + entry.getKey() + "\", \"" + entry.getValue().getAsString() +"\")"); 
    			}
    				
    		}
    		
    		if(jObj.has("elements"))
    		{
    			JsonArray jArr = jObj.get("elements").getAsJsonArray(); 
    			for(JsonElement jEle: jArr)
    			{
    				output.add(".element()"); 
    				JsonObject ele = jEle.getAsJsonObject(); 
    				JsonArray jArrFrom = ele.get("from").getAsJsonArray(); 
    				String from = ""; 
    				for(JsonElement jEleFrom: jArrFrom)
    				{
    					from += ", " +jEleFrom.getAsFloat() + "f"; 
    				}
    				append(output, ".from(" + from.replaceFirst(", ", "") + ")"); 
    				
    				JsonArray jArrTo = ele.get("to").getAsJsonArray(); 
    				String to = ""; 
    				for(JsonElement jEleTo: jArrTo)
    				{
    					to += ", " +jEleTo.getAsFloat() + "f"; 
    				}
    				append(output, ".to(" + to.replaceFirst(", ", "") + ")"); 
    				
    				
    				if(ele.has("rotation"))
    				{
    					output.add("	.rotation()"); 
    					JsonObject rotation = ele.get("rotation").getAsJsonObject();
    					if(rotation.has("angle"))
    					{
    						append(output, ".angle(" + rotation.get("angle").getAsFloat() + "f" + ")");
    					}
    					
    					if(rotation.has("axis"))
    					{
    						append(output, ".axis(Axis.byName(\"" + rotation.get("axis").getAsString() + "\"))");
    					}
    					
    					if(rotation.has("origin"))
    					{
    						JsonArray originArr = rotation.get("origin").getAsJsonArray(); 
    	    				String originStr = ""; 
    	    				for(JsonElement origin: originArr)
    	    				{
    	    					originStr += ", " + origin.getAsFloat() + "f"; 
    	    				}
    	    				append(output, ".origin(" + originStr.replaceFirst(", ", "") + ")"); 
    					}
    					
    					if(rotation.has("rescale"))
    					{
    						append(output, ".rescale(" + rotation.get("rescale").getAsString() + ")");
    					}
    					
    					append(output, ".end()"); 
    				}
    				
    				if(ele.has("faces"))
    				{
    					for(Entry<String, JsonElement> entry :ele.get("faces").getAsJsonObject().entrySet())
    	    			{
    						output.add("	.face(Direction.byName(\"" + entry.getKey() + "\"))");
    						JsonObject value = entry.getValue().getAsJsonObject(); 
    						JsonArray uvArr = value.get("uv").getAsJsonArray(); 
    	    				String uvs = ""; 
    	    				for(JsonElement uv: uvArr)
    	    				{
    	    					uvs += ", " + uv.getAsFloat() + "f"; 
    	    				}
    	    				append(output, ".uvs(" + uvs.replaceFirst(", ", "") + ")"); 
    	    				
    	    				if(value.has("rotation"))
    	    				{
    	    					int rot = value.get("rotation").getAsInt(); 
    	    					switch(rot)
    	    					{
        	    					case 90: output.add(".rotation(FaceRotation.CLOCKWISE_90)");
        	    					case 180: output.add(".rotation(FaceRotation.UPSIDE_DOWN)");
        	    					case 270: output.add(".rotation(FaceRotation.COUNTERCLOCKWISE_90)"); 
    	    					}
    	    				}
    	    				
    	    				if(value.has("texture"))
    	    				{
    	    					append(output, ".texture(\"" + value.get("texture").getAsString() + "\")"); 
    	    				}
    	    				
    	    				if(value.has("tintindex"))
    	    				{
    	    					append(output, ".tintindex(" + value.get("tintindex").getAsInt() + ")"); 
    	    				}
    	    				
    	    				
    	    				append(output, ".end()"); 
    	    			}
    				}
    				
    				
    				append(output, ".end()"); 
    			}
    			
    			
    			
    		}
		}
		else 
		{
			output.add("Missing file: " + path); 
		}
		
		int i = 1; 
		for(String s: output)
		{
			if(i == output.size())
			{
				s += ";"; 
			}
			
			if(s.startsWith(".") || s.startsWith("	."))
			{
				s = "	" + s; 
			}
			System.out.println(s); 
			i++; 
		}
	}
	
	private static void append(List<String> list, String end)
	{
		int last = list.size() - 1; 
		String s = list.get(last); 
		list.set(last, s + end); 
	}
	
	private static JsonObject getJsonObject(String path)
	{
		try{
    		ResourceLocation loc = new ResourceLocation(path);
    		InputStream in = Minecraft.getInstance().getResourceManager().getResource(loc).getInputStream();
    		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    		JsonElement je = gson.fromJson(reader, JsonElement.class);
    		return je.getAsJsonObject();
		} catch (IOException ioe) 
		{
			return null; 
		}
	}
	
	
}