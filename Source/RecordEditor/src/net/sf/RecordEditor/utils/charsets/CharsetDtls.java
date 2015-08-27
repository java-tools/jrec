package net.sf.RecordEditor.utils.charsets;

import java.nio.charset.Charset;

/**
 * Character-set details including id and description
 * 
 * @author Bruce Martin
 *
 */
public class CharsetDtls {

	public final String id, description;
	public final Charset charset;
	
	/**
	 * Character-set details including id and description
	 * @param id charset identifier (name)
	 * @param description charset description
	 * @param charset
	 */
	CharsetDtls(String id, String description, Charset charset) {
		super();
		this.id = id;
		this.description = description;
		this.charset = charset;
	}
	
	
}
