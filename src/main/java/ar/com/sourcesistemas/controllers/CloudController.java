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

    @RequestMapping(value = "guardarCategoria", method = RequestMethod.POST)
    public String saveSnipplet(@RequestBody String jsonDTO ){
        SendDTO sendDTO=null;
        try {
             sendDTO =mapper.readValue(jsonDTO,SendDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

         User user = userDao.getUsernameByName(sendDTO.getUsername());
        log.info("user id: "+user.getId());
        log.info("user id: "+user.getName());
        log.info("user id: "+user.getPassword());
        log.info("user id: "+user.getActive());

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
