package ar.com.sourcesistemas.utilities;

import ar.com.commons.send.domain.Category;
import ar.com.commons.send.domain.Snipplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("progHelp")
public class ProgramHelpClass {

	@Autowired
	private GsonUtility gson;
	
	public Category getNewCategory() {
		Category cat = new Category();
		
		cat.setNombreCategoria("nombre Categoria");
		
		Snipplet snip = new Snipplet();
		Snipplet snip2 = new Snipplet();
		
		snip.setTitulo("titulo");
		snip.setContenido("contenido");
		snip.setCategoria(cat);
		
		snip2.setTitulo("titulo");
		snip2.setContenido("contenido");
		snip2.setCategoria(cat);
		
		List<Snipplet> snips = new ArrayList<Snipplet>();
		
		snips.add(snip);
		snips.add(snip2);
		
		cat.setSnipplets(snips);

		String result = gson.getGsonWithExclusion().toJson(cat);
		System.out.println(result);
		
		return cat;
	}
	
	
}
