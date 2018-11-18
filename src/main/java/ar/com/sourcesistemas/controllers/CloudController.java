package ar.com.sourcesistemas.controllers;

import ar.com.sourcesistemas.dao.CategoryDAO;
import ar.com.sourcesistemas.dao.UserDAO;
import ar.com.sourcesistemas.dto.CategoriaDTO;
import ar.com.sourcesistemas.dto.SendDTO;
import ar.com.sourcesistemas.dto.SnippletDTO;
import ar.com.sourcesistemas.model.Category;
import ar.com.sourcesistemas.model.User;
import ar.com.sourcesistemas.utilities.ConvertToDTOUtility;
import ar.com.sourcesistemas.utilities.GsonUtility;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class CloudController {

    final static Logger log = Logger.getLogger(CloudController.class);
    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private UserDAO userDao;

    @Autowired
    private CategoryDAO categoryDAO;
    private SendDTO getSendDTO(String jsonDTO){

        SendDTO sendDTO=null;
        try {
            log.info("parsing the string to sendDTO objet");
            sendDTO =mapper.readValue(jsonDTO,SendDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sendDTO;
    }

    @RequestMapping(value ="returnCategory", method = RequestMethod.POST)
    public String returnCategory(String jsonDTO) throws IOException {
        SendDTO sendDTO = getSendDTO(jsonDTO);
        User user = userDao.getUsernameByName(sendDTO.getUsername());
        List<CategoriaDTO> categoriaDTO = ConvertToDTOUtility.convertToUserDTO(user);

        String categoriaDTOjson = mapper.writeValueAsString(categoriaDTO);
        return categoriaDTOjson;
    }

    @RequestMapping(value = "listarServer", method = RequestMethod.POST)
    public String listarServer(String jsonDTO) {
        String result = null;
        log.info("listando servidor...");
        SendDTO sendDTO = getSendDTO(jsonDTO);
        User user = userDao.getUsernameByName(sendDTO.getUsername());
        List<String> collect = user.getCategory().stream().map(s -> s.getNombreCategoria()).collect(Collectors.toList());
        log.info("categorias ...");

        for (Category category : user.getCategory() ){
            log.info(category.getNombreCategoria());
        }

        try {
            result = mapper.writeValueAsString(collect);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    @RequestMapping(value = "guardarCategoria", method = RequestMethod.POST)
    public String saveSnipplet(@RequestBody String jsonDTO ){
        SendDTO sendDTO = getSendDTO(jsonDTO);
        User user = userDao.getUsernameByName(sendDTO.getUsername());

        if (sendDTO != null) {
            String categoriaNombre = sendDTO.getCategoriaDTO().getNombre();
            List<SnippletDTO> snipplets = sendDTO.getCategoriaDTO().getSnipplets();
            List<Category> category = user.getCategory().stream().filter(x -> x.getNombreCategoria().equals(categoriaNombre)).collect(Collectors.toList());

            if(category.size() == 0){
                Category categoria= ConvertToDTOUtility.convertTOCategory(sendDTO.getCategoriaDTO());
                categoria.setUser(user);
                user.addCategory(categoria);
                categoryDAO.saveCategory(categoria);
            }

        }else {

            log.error("TheDTO was null");
        }


        return "hello ";
    }
    @RequestMapping(value = "test2",method = RequestMethod.POST)
    public String test2(@RequestBody String json){
        return "hello2 "+json;
    }



}
