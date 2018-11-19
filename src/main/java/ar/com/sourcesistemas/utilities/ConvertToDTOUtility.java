package ar.com.sourcesistemas.utilities;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import ar.com.sourcesistemas.dto.SnippletDTO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import ar.com.sourcesistemas.dto.CategoriaDTO;
import ar.com.sourcesistemas.dto.CategoryViewDTO;
import ar.com.sourcesistemas.model.Category;
import ar.com.sourcesistemas.model.Snipplet;
import ar.com.sourcesistemas.model.User;

@Repository("ConvertToDTO")
public class ConvertToDTOUtility {

	@Transactional
	public static List<CategoriaDTO> convertToUserDTO(User user) {

		CategoryViewDTO viewDTO = new CategoryViewDTO();
		viewDTO.setUsername(user.getName());

		List<CategoriaDTO> catsDTO = new ArrayList<CategoriaDTO>();
		List<SnippletDTO> snipsDTO = new ArrayList<SnippletDTO>();
		for(Category category : user.getCategory()) {
			CategoriaDTO catDTO = new CategoriaDTO();
			catDTO.setNombre(category.getNombreCategoria());

			for ( Snipplet snipplet : category.getSnipplets()) {

				SnippletDTO snipDTO = new SnippletDTO();
				snipDTO.setContenido(snipplet.getContenido());
				snipDTO.setTitulo(snipplet.getTitulo());
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
		List<Snipplet> snipplets = new LinkedList<>();
		for (SnippletDTO snippletDTO : categoriaDTO.getSnipplets()){

			snipplets.add(new Snipplet(snippletDTO,category));
		}

		category.setSnipplets(snipplets);

		return category;
	}

	final static Logger log = Logger.getLogger(ConvertToDTOUtility.class);
	public static CategoriaDTO fromCategoryToCategoryDTO(Category category){
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setNombre(category.getNombreCategoria());

		log.info("Listo los snipplets ----------------------------------------");
		category.getSnipplets().stream().forEach(x -> log.info(x.getTitulo()));
		log.info("Listo los snipplets ----------------------------------------");
		category.getSnipplets().stream().forEach(x -> new SnippletDTO(x));
		List<SnippletDTO> collect = category.getSnipplets().stream().map(x -> new SnippletDTO(x)).collect(Collectors.toList());
		log.info("************************************************");
		collect.stream().forEach(x -> log.info(x.getTitulo()));
		log.info("************************************************");
		categoriaDTO.setSnipplets(collect);
		log.info("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*");
		categoriaDTO.getSnipplets().stream().forEach(x-> log.info(x.getTitulo()));
		categoriaDTO.getSnipplets().stream().forEach(x-> log.info(x.getContenido()));
		log.info("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*");

		return categoriaDTO;
	}


}
