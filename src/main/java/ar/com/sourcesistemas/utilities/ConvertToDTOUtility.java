package ar.com.sourcesistemas.utilities;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.com.sourcesistemas.dto.SendDTO;
import ar.com.sourcesistemas.model.Category;
import ar.com.sourcesistemas.model.User;

@Repository("ConvertToDTO")
public class ConvertToDTOUtility {
	
	
	public SendDTO convertToUserDTO(User user) {
		
		SendDTO sendDTO = new SendDTO();
		sendDTO.setUsername(user.getName());
		
		
		List<Category> categorys = user.getCategory();
		
		
		
		
//		sendDTO.setCategoriaDTO(categoriaDTO);
		
		
		
		return null;
	}

}
