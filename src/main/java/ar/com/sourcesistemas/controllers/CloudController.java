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
            log.info("esto es nuevo");
            sendDTO =mapper.readValue(jsonDTO,SendDTO.class);
        } catch (Exception e) {
            log.error("error de parseo: "+e.toString());
            e.printStackTrace();
        }
        log.info("end parsing the string to sendDTO objet");

        return sendDTO;
    }

    @RequestMapping(value ="returnCategory", method = RequestMethod.POST)
    public String returnCategory(@RequestBody String jsonDTO) throws IOException {
        SendDTO sendDTO = getSendDTO(jsonDTO);
        log.info("return category ....");
        User user = userDao.getUsernameByName(sendDTO.getUsername());
        List<CategoriaDTO> categoriaDTO = ConvertToDTOUtility.convertToUserDTO(user);

        CategoriaDTO collect = categoriaDTO.stream().filter(x -> x.getNombre().equals(sendDTO.getCategoriaDTO().getNombre())).collect(Collectors.toList()).get(0);

        log.info("nombre categoria: "+collect.getNombre());

        sendDTO.getCategoriaDTO().setSnipplets(collect.getSnipplets());
        String returnSendDTO = mapper.writeValueAsString(sendDTO);
        return returnSendDTO ;
    }

    @RequestMapping(value = "listarServer", method = RequestMethod.POST)
    public String listarServer(@RequestBody String jsonDTO) {
        String result = null;
        log.info("jsondTO: "+jsonDTO);
        log.info("listando servidor...");
        SendDTO sendDTO = getSendDTO(jsonDTO);
        User user = userDao.getUsernameByName(sendDTO.getUsername());
        List<String> collect = user.getCategory().stream().map(s -> s.getNombreCategoria()).collect(Collectors.toList());
        log.info("categorias ...");

        for (Category category : user.getCategory() ){
            log.info(category.getNombreCategoria());
        }
        log.info("fin listado categorias");

        try {
            result = mapper.writeValueAsString(collect);
        } catch (IOException e) {
            log.error("error en el writevalue as string");
            e.printStackTrace();
        }
        log.info("result: "+result);
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
