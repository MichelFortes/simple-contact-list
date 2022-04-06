package br.com.waytech.simplecontactlist.config.error;

public class ErrorResponseDto {

	private ErrorTemplateType template;
	private Object content;

	public ErrorResponseDto(ErrorTemplateType template) {
		super();
		this.template = template;
	}

	public ErrorTemplateType getTemplate() {
		return template;
	}

	public void setTemplate(ErrorTemplateType template) {
		this.template = template;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

}
