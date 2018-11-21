package ar.com.sourcesistemas.controllers;

import ar.com.commons.send.domain.Category;
import ar.com.commons.send.domain.Snipplet;
import ar.com.commons.send.domain.User;
import ar.com.commons.send.dto.CategoriaDTO;
import ar.com.commons.send.dto.SendDTO;
import ar.com.commons.send.dto.SnippletDTO;
import ar.com.commons.send.parser.ConvertToDTOUtility;
import ar.com.sourcesistemas.dao.CategoryDAO;
import ar.com.sourcesistemas.dao.SnippletDAO;
import ar.com.sourcesistemas.dao.UserDAO;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@RestController
public class CloudController {

	final static Logger log = Logger.getLogger(CloudController.class);
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private UserDAO userDao;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private SnippletDAO snippletDAO;

	private SendDTO getSendDTO(String jsonDTO) {

		SendDTO sendDTO = null;
		try {
			sendDTO = mapper.readValue(jsonDTO, SendDTO.class);
		} catch (Exception e) {
			log.error("error de parseo: " + e.toString());
			e.printStackTrace();
		}

		return sendDTO;
	}

	@RequestMapping(value = "returnCategory", method = RequestMethod.POST)
	public String returnCategory(@RequestBody String jsonDTO) throws IOException {
		log.info("Return category....");
		SendDTO sendDTO = getSendDTO(jsonDTO);
		User user = userDao.getUsernameByName(sendDTO.getUsername());
		user.getCategory().stream().forEach(x -> log.info(x.getNombreCategoria()));
		Category category = user.getCategory().stream()
				.filter(x -> x.getNombreCategoria().equals(sendDTO.getCategoriaDTO().getNombre()))
				.collect(Collectors.toList()).get(0);
		CategoriaDTO categoriaDTO = ConvertToDTOUtility.fromCategoryToCategoryDTO(category);
		log.info("Categoria a devolver: " + categoriaDTO.getNombre());
		log.info("Snipplets a devolver:");
		categoriaDTO.getSnipplets().stream().forEach(x -> log.info(x.getTitulo()));
		sendDTO.setCategoriaDTO(categoriaDTO);
		String result = mapper.writeValueAsString(sendDTO);
		return result;
	}

	@RequestMapping(value = "listarServer", method = RequestMethod.POST)
	public String listarServer(@RequestBody String jsonDTO) {
		String result = null;
		log.info("jsondTO: " + jsonDTO);
		log.info("listando servidor...");
		SendDTO sendDTO = getSendDTO(jsonDTO);
		User user = userDao.getUsernameByName(sendDTO.getUsername());
		List<String> collect = user.getCategory().stream().map(s -> s.getNombreCategoria())
				.collect(Collectors.toList());
		log.info("categorias ...");

		for (Category category : user.getCategory()) {
			log.info(category.getNombreCategoria());
		}
		log.info("fin listado categorias");

		try {
			result = mapper.writeValueAsString(collect);
		} catch (IOException e) {
			log.error("error en el writevalue as string");
			e.printStackTrace();
		}
		log.info("result: " + result);
		return result;

	}

	@RequestMapping(value = "guardarCategoria", method = RequestMethod.POST)
	@Transactional
	public String saveSnipplet(@RequestBody String jsonDTO) {
		log.info("guardarCategoria ");
		SendDTO sendDTO = null;
		List<SnippletDTO> toAdd = new LinkedList<SnippletDTO>();
		try {
			sendDTO = getSendDTO(jsonDTO);
		} catch (Exception e) {
			log.info(e.toString());
			throw e;
		}
		User user = userDao.getUsernameByName(sendDTO.getUsername());
		String categoriaDTONombre = sendDTO.getCategoriaDTO().getNombre();
		List<Category> categories = user.getCategory().stream()
				.filter(x -> x.getNombreCategoria().equals(categoriaDTONombre)).collect(Collectors.toList());
		if (categories.size() == 0) {
			 addCategory(sendDTO.getCategoriaDTO(), user);
		} else {
			mergeSnipplets(sendDTO, categories.get(0), toAdd);
			for (SnippletDTO snipDTO : toAdd) {
				Snipplet snipplet = ConvertToDTOUtility.fromSnippletDTOtoSnippletWithCategory(snipDTO,
						categories.get(0));
				snippletDAO.saveSnipplet(snipplet);
			}
		}

		return "hello";
	}

	private List<Category> addCategory(CategoriaDTO categoriaDTO, User user) {
		List<Category> categories = null;
		Category cat = new Category(categoriaDTO.getNombre(), user);
		if (user.getCategory() == null) {
			categories = new LinkedList<Category>();
		} else {
			categories = user.getCategory();
		}
		for (SnippletDTO snipDTO : categoriaDTO.getSnipplets()) {
			Snipplet snip = ConvertToDTOUtility.fromSnippletDTOtoSnippletWithCategory(snipDTO, cat);
			cat.add(snip);
			cat.setUser(user);
		}
		categoryDAO.saveCategory(cat);
		// borrar
		for (Category cato : categories) {
			System.out.println(cato.getNombreCategoria());
			for (Snipplet snop : cato.getSnipplets()) {
				System.out.println(snop.getTitulo());
				System.out.println(snop.getContenido());
			}

		}
		// borrar
		return categories;

	}

	@RequestMapping(value = "test2", method = RequestMethod.POST)
	public String test2(@RequestBody String json) {
		return "hello2 " + json;
	}

	public void mergeSnipplets(SendDTO sendDTO, Category userCategory, List<SnippletDTO> toAdd) {

		log.info("Saving object");
		for (SnippletDTO sDTO : sendDTO.getCategoriaDTO().getSnipplets()) {
		boolean modify = false;

			log.info("sDTO: " + sDTO.getTitulo());
			for (Snipplet s : userCategory.getSnipplets()) {
				log.info("snipplet: " + s.getTitulo());

				if (sDTO.getTitulo().equals(s.getTitulo())) {

						modify = true;
					if (!sDTO.getContenido().equals(s.getContenido())) {

						log.info("actualizando contenido");
						s.setContenido(sDTO.getContenido());
						snippletDAO.saveSnipplet(s);	

					}

				}
			}
			if (!modify) {
				log.info("fue modificado asi que hago el add");
				toAdd.add(sDTO);
				modify = !modify;
				log.info("modify set to: " + modify);
			}

		}

	}

}
