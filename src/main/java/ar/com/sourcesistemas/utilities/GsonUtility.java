package ar.com.sourcesistemas.utilities;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Repository("gsonUtility")
public class GsonUtility {

	
	private Gson gson;
	private Gson gsonExclusion;
	
	
	
	
	public Gson getGson(){

		if(gson == null){
			gson = new Gson();
			return gson;
			
		}
		
		return gson;
		
		
	} 
	
	public Gson getGsonWithExclusion() {
		
		if(gsonExclusion == null){
			gsonExclusion = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			return gsonExclusion;
			
		}
		
		return gsonExclusion;
		
		
	}

	
	
	
	
	
	
	
	
	
}