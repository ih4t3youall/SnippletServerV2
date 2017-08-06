package ar.com.sourcesistemas.utilities;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.com.sourcesistemas.dto.CategoriaDTO;
import ar.com.sourcesistemas.dto.CategoryViewDTO;
import ar.com.sourcesistemas.dto.SnippletDTO;
import ar.com.sourcesistemas.model.Category;
import ar.com.sourcesistemas.model.Snipplet;
import ar.com.sourcesistemas.model.User;

@Repository("ConvertToDTO")
public class ConvertToDTOUtility {
	
	
	public static List<CategoriaDTO> convertToUserDTO(User user) {
		
		CategoryViewDTO viewDTO = new CategoryViewDTO();
		viewDTO.setUsername(user.getName());
		
		List<CategoriaDTO> catsDTO = new ArrayList<CategoriaDTO>();
		List<SnippletDTO> snipsDTO = new ArrayList<SnippletDTO>();
		for(Category category : user.getCategory()) {
			CategoriaDTO catDTO = new CategoriaDTO();
			catDTO.setCategoriaID(category.getId());
			catDTO.setNombre(category.getNombreCategoria());
			
			for ( Snipplet snipplet : category.getSnipplets()) {
				
				SnippletDTO snipDTO = new SnippletDTO();
				snipDTO.setContenido(snipplet.getContenido());
				snipDTO.setTitulo(snipplet.getTitulo());
				snipDTO.setId(snipplet.getId());
				snipsDTO.add(snipDTO);
				
			}
			catDTO.setSnipplets(snipsDTO);
			
			catsDTO.add(catDTO);
			
			
		}
		return catsDTO;
	}
	
	public static Category convertTOCategory(CategoriaDTO categoriaDTO ){
		
		Category category = new Category();
		
		category.setNombreCategoria(categoriaDTO.getNombre());
		List<SnippletDTO> snippletDTOs = categoriaDTO.getSnipplets();
		
		List<Snipplet> snipplets = new ArrayList<Snipplet>();
		for (SnippletDTO snippletDTO : snippletDTOs) {
			
			Snipplet snipplet = new Snipplet();
			snipplet.setContenido(snippletDTO.getContenido());
			snipplet.setTitulo(snippletDTO.getTitulo());
			snipplet.setCategoria(category);
			snipplets.add(snipplet);
		}
		
		
		category.setSnipplets(snipplets);
		
		return category;
	}

}
