package br.com.projetobasejsf.bean;

import static br.com.projetobasejsf.utils.FacesUtils.addErrorMessage;
import static br.com.projetobasejsf.utils.FacesUtils.addSuccessMessage;
import static java.util.Arrays.asList;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.collect.ImmutableMap;

import br.com.projetobasejsf.annotations.Transactional;
import br.com.projetobasejsf.pesistence.daointerface.DAO;
import br.com.projetobasejsf.pesistence.model.Projeto;
import br.com.projetobasejsf.pesistence.model.enums.Condition;

@Named
@ViewScoped
public class TestBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private DAO<Projeto> dao;
	private Projeto projeto;
	private List<Projeto> projetos;

	@PostConstruct
	public void init() {
		projetos = dao.listAll();

		List<Projeto> projetoList = dao.findByAttributes(ImmutableMap.of("nome", "Projeto 1", "id", 1L),
				asList(Condition.LIKE, Condition.EQUAL));
		System.out.println(projetoList);
	}

	@Transactional
	public void construct() throws Exception {
		try {
//			Projeto p = new Projeto();
//			Projeto p2 = new Projeto();
//			p.setNome("Projeto 1");
//			p2.setNome("Projeto 2");
//			dao.save(p);
//			dao.save(p2);
			
			System.out.println(projeto);
			addSuccessMessage("sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("error");
			throw e;
		}
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

}
