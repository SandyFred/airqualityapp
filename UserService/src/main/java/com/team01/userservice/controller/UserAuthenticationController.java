package com.team01.userservice.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team01.userservice.exception.UserAlreadyExistsException;
import com.team01.userservice.exception.UserNotFoundException;
import com.team01.userservice.model.User;
import com.team01.userservice.service.UserAuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthenticationController {

	@Autowired
	UserAuthenticationService authService;

	public UserAuthenticationController(UserAuthenticationService authService) {
		this.authService = authService;
	}

//	@PostMapping("/register")
//	public ResponseEntity<String> register(@RequestBody User user){
//		try {
//			authService.saveUser(user);
//			return new ResponseEntity<String>("Created", HttpStatus.CREATED);
//		} catch (UserAlreadyExistsException e) {
//			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
//		}
//	}
	
	@PostMapping("/register")
    public ResponseEntity<String> register(User user,@RequestParam("image") MultipartFile multipartFile) throws IOException {
        try {
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        user.setprofilePictureUrl(fileName);
	        authService.saveUser(user);
	        String uploadDir = "user-photos/" + user.getUserId();
	        saveFile(uploadDir, fileName, multipartFile);
	        return new ResponseEntity<String>("Created", HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
    }
	

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		try {
			authService.findByUserIdAndPassword(user.getUserId(), user.getUserPassword());
			return new ResponseEntity<String>(getToken(user.getUserId(), user.getUserPassword()), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	//save image to directory
	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {        
            throw new IOException("Could not save image file: " + fileName, e);
        }
	}

	// Generate JWT token
	public String getToken(String userId, String password) throws Exception {
		return Jwts.builder().setId(userId).setSubject(password).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").compact();

	}
	

}