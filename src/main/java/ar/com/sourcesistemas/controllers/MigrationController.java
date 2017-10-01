package ar.com.sourcesistemas.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.com.sourcesistemas.dao.UserDAO;
import ar.com.sourcesistemas.dto.CategoriaDTO;
import ar.com.sourcesistemas.migracion.Persistencia;
import ar.com.sourcesistemas.model.Category;
import ar.com.sourcesistemas.model.User;
import ar.com.sourcesistemas.utilities.ConvertToDTOUtility;
import ar.com.sourcesistemas.utilities.GsonUtility;

@Controller
public class MigrationController {
	
	@Autowired
	private GsonUtility gsonUtility;
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping("/admin/migrar")
	public void migrar() {
		
		
		String output = restCall("iniciarMigracion");
		
		List<CategoriaDTO> catsDTO = new ArrayList<CategoriaDTO>();
		
		int maxPositions = Integer.parseInt(output);
		
		for(int i = 0; i< maxPositions; i++) {
			
			String categoriaDTOString = otherRestCall("getNumber",i);
			CategoriaDTO categoriaDTO = gsonUtility.getGson().fromJson(categoriaDTOString, CategoriaDTO.class);
			catsDTO.add(categoriaDTO);
			
		}
		
		User user =  new User();
		
		user.setName("martin");
		user.setPassword("martin");
		
		
		
		for (CategoriaDTO categoriaDTO : catsDTO) {
			Category category = ConvertToDTOUtility.convertTOCategory(categoriaDTO);
			category.setUser(user);
			user.addCategory(category);
		}
	
		userDAO.saveUser(user);
		
		
	}
	
	
	private  String otherRestCall(String endpoint,int position) {
		String output="";
		  try {

			URL url = new URL("http://ih4t3youall.com:8082/"+endpoint+"?number="+position);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			 output = br.readLine();
			System.out.println(output);
			

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }

		  
		  return output;
		  
		}
	
	
	private  String restCall(String endpoint) {
		String output="";
		  try {

			URL url = new URL("http://ih4t3youall.com:8082/"+endpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			 output = br.readLine();
			System.out.println(output);
			

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }

		  
		  return output;
		  
		}

}
