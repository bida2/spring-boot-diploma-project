package com.springboot.studentservices.userservices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageImpl implements FileStorage  {
	
	
	private final Path rootLocation = Paths.get("src/main/resources/static/filestorage/");

	@Override
	public void store(MultipartFile file) throws IOException {
		try {
			// FilenameUtils is part of Apache Commons IO library
			// Get the file extension, in order to check whether it is valid or not
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			System.out.println("Format is: " + extension);
			// Check if the extension is for a BMP,JPG or PNG image - our allowed image formats
			if (extension.compareTo("png") == 0 || extension.compareTo("jpg") == 0 || extension.compareTo("bmp") == 0) {
				Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
			} else {
				throw new IOException("Invalid file format provided! Allowed formats are .png, .jpg and .bmp!");
			}
        } catch (IOException e) {
        	throw e;
        }
	}
	
	@Override
    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
            	throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
        	throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
    }
    
	@Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
 
	@Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
       } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage with name " + "\"" + e.getMessage() + "\"");
       }
    }
 
	@Override
	public Stream<Path> loadFiles() {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
        	throw new RuntimeException("\"Failed to read stored file");
        }
	}

	
	
	

}
