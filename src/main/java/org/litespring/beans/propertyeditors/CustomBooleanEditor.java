package org.litespring.beans.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.litespring.utils.StringUtils;
/**
 *	将一些指定的String类型转换为boolean
 *	如果on,yes,1等都可以转化为true
 */
public class CustomBooleanEditor extends PropertyEditorSupport {
	
	public static final String VALUE_TRUE = "true";
	public static final String VALUE_FALSE = "false";
	
	public static final String VALUE_ON = "on";
	public static final String VALUE_OFF = "off";
	
	public static final String VALUE_YES = "yes";
	public static final String VALUE_NO = "no";
	
	public static final String VALUE_1 = "1";
	public static final String VALUE_0 = "0";
	
	private final boolean allowEmpty;
	
	public CustomBooleanEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String input = (text != null ? text.trim() : null);
		if (this.allowEmpty && !StringUtils.hasLength(input)) {
			setValue(null);
		} else if ((VALUE_TRUE.equalsIgnoreCase(input) || VALUE_ON.equalsIgnoreCase(input) ||
							VALUE_YES.equalsIgnoreCase(input) || VALUE_1.equalsIgnoreCase(input))) {
			setValue(Boolean.TRUE);	
		} else if ((VALUE_FALSE.equalsIgnoreCase(input) || VALUE_OFF.equalsIgnoreCase(input) ||
				VALUE_NO.equalsIgnoreCase(input) || VALUE_0.equalsIgnoreCase(input))) {
			setValue(Boolean.FALSE);
		} else {
			throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
		}
	}
}
