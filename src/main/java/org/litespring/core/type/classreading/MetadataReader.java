package org.litespring.core.type.classreading;

import org.litespring.core.io.Resource;
import org.litespring.core.type.ClassMetadata;
/**
 * 	一个简单的封装接口,主要提供ClassMetadata和AnnotationMetadata。
 * 	我们不需要去关注ClassMetadata那些类的visitor方法,以及asm的调用过程
 * 	我们只需要能拿到类中包含的注解以及注解的属性即可
 */
public interface MetadataReader {
	
	Resource getResource();
	
	/**
	 * @return 返回类的信息
	 */
	ClassMetadata getClassMetada();
	
	/**
	 * @return 返回属性的信息
	 */
	AnnotationMetadata getAnnotationMetadata();
}
