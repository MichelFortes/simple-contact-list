package br.com.waytech.simplecontactlist.dto;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

import org.springframework.util.Base64Utils;

@Embeddable
public class Base64File {

	@NotEmpty
	private String mediaType;
	@Lob
	@NotEmpty
	private String base64Data;

	public Base64File() {
	}

	public Base64File(@NotEmpty String mediaType, @NotEmpty String base64Data) {
		this.mediaType = mediaType;
		this.base64Data = base64Data;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getBase64Data() {
		return base64Data;
	}

	public void setBase64Data(String base64Data) {
		this.base64Data = base64Data;
	}
	
	public byte[] toBytes() {
        return Base64Utils.decodeFromString(base64Data);		
	}

}
