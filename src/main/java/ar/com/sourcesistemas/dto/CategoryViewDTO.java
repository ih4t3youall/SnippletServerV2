package ar.com.sourcesistemas.dto;

import java.util.List;

public class CategoryViewDTO {

	private long userId;
	private String username;
	private List<CategoriaDTO> categoriaDTO;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<CategoriaDTO> getCategoriaDTO() {
		return categoriaDTO;
	}

	public void setCategoriaDTO(List<CategoriaDTO> categoriaDTO) {
		this.categoriaDTO = categoriaDTO;
	}


}
