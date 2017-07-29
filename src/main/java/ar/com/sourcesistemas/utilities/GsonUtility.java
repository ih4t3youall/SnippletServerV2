package ar.com.sourcesistemas.utilities;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;


@Repository("gsonUtility")
public class GsonUtility {

	
	private Gson gson;
	
	
	
	
	public Gson getGson(){

		if(gson == null){
			gson = new Gson();
			return gson;
			
		}
		
		return gson;
		
		
	} 

	
	
	
	
	
	
	
	
	
}