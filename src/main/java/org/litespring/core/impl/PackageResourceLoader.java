package org.litespring.core.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;
import org.litespring.utils.Assert;
import org.litespring.utils.ClassUtils;
/**
 * 	接收一个String类型的包名,将package下面的class变成Resource
 */
public class PackageResourceLoader {
	
	private  static final Log logger = LogFactory.getLog(PackageResourceLoader.class);
	
	private final ClassLoader classLoader;
	
	public PackageResourceLoader() {
		this.classLoader = ClassUtils.getDefaultClassLoader();
	}
	public PackageResourceLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	public ClassLoader getClassLoader() {
		return this.classLoader;
	}
	
	public Resource[] getResources(String basePackage) throws IOException {
		Assert.notNull(basePackage, "basePackage must not be null");
		
		//将包名转换为路径
		String location = ClassUtils.convertClassNameToResourcePath(basePackage);
		
		ClassLoader cl = getClassLoader();
		URL url = cl.getResource(location);
		File rootDir = new File(url.getFile());
		
		Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
		Resource[] result = new Resource[matchingFiles.size()];
		int i = 0;
		for (File file : matchingFiles) {
			result[i++] = new FileSystemResource(file);
		}
		return result;
	}
	
	//检验包是否合理
	protected Set<File> retrieveMatchingFiles(File rootDir) throws IOException {
		if (!rootDir.exists()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] beacause it does not exist");
			}
			return Collections.emptySet();
		}
		if (!rootDir.isDirectory()) {
			if (logger.isWarnEnabled()) {
				logger.warn("Skipping [" + rootDir.getAbsolutePath() + "] beacause it does not a directory");
			}
			return Collections.emptySet();
		}
		if (!rootDir.canRead()) {
			if (logger.isWarnEnabled()) {
				logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath() +
						"] because the application is not allowed to read the directory");
			}
			return Collections.emptySet();
		}
		/*String fullPattern = StringUtils.replace(rootDir.gerAbsolutePath, File.separator, "/");
		 * if(!pattern.startwith("/"){
		 * 		fullPattern += "/";
		 * }
		 * fullPattern = fullPattern + StringUtils.replace(pattern, File.separator, "/"); 
		 */
		
		Set<File> result = new LinkedHashSet<>();
		doRetrieveMatchingFiles(rootDir, result);
		return result;
	}
	
	//递归找到路径下所有的class文件
	protected void doRetrieveMatchingFiles( File dir, Set<File> result) throws IOException {
		
		File[] dirContents = dir.listFiles();
		if (dirContents == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]");
			}
			return;
		}
		for (File content : dirContents) {
			
			if (content.isDirectory() ) {
				if (!content.canRead()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Skipping subdirectory [" + dir.getAbsolutePath() +
								"] because the application is not allowed to read the directory");
					}
				}
				else {
					doRetrieveMatchingFiles(content, result);
				}
			} else{
				result.add(content);
			}
			
		}
	}
}

