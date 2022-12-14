package br.com.projetobasejsf.converters;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.projetobasejsf.pesistence.model.AbstractEntity;

@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter<Object> {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || !value.matches("\\d+")) {
			return null;
		}
		return this.getAttributesFrom(uiComponent).get(value);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value != null && !value.equals("")) {
			AbstractEntity entity = (AbstractEntity) value;
			if (entity.getId() != null) {
				this.addAttribute(uiComponent, entity);
				return entity.getId().toString();
			}
			return value.toString();
		}
		return "";
	}

	private void addAttribute(UIComponent component, AbstractEntity abstractEntity) {
		this.getAttributesFrom(component).put(abstractEntity.getId().toString(), abstractEntity);
	}

	private Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}
}
