package br.com.nordestefomento.jrimum;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

public abstract class ACurbitaObject  implements Serializable{
	
	protected static Logger log = Logger.getLogger(ACurbitaObject.class);
	
	public static boolean isNull(Object object, String name)throws IllegalArgumentException{
		
		boolean is = true;
		
		if(object != null){
			is = false;
		}else{
			
			IllegalArgumentException e = new IllegalArgumentException((name != null ? name : "Object ")+" invalid : [" + object + "]!");
			
			log.error(StringUtils.EMPTY, e);
			
			throw e;
		}
		
		return is;
	}
	
	public static boolean isNull(Object object)throws IllegalArgumentException{
		
		return isNull(object, null);
	}
	
	public static boolean isNotNull(Object object){
		
		boolean is = true;
		
		if(object == null)
			is = false;
		
		return is;
	}

	/**
	 * Exibe os valores de instância.
	 * 
	 * @see org.apache.commons.lang.builder.ToStringBuilder#reflectionToString
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this); 
	}
}
