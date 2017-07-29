package ar.com.sourcesistemas.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.com.sourcesistemas.model.Category;
import ar.com.sourcesistemas.model.Snipplet;

@Repository("progHelp")
public class ProgramHelpClass {

	
	
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

		return cat;
	}
	
	
}
