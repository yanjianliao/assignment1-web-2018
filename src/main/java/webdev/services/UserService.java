package webdev.services;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.User;
import webdev.repositories.UserRepository;

@RestController
public class UserService {
	@Autowired
	UserRepository repository;

	//both /api/user and /api/user/?username=... will work
	@GetMapping("/api/user")
	public List<User> findAllUsers(@RequestParam(name="username", required=false) String username) {
		if(username != null) {
			return (List<User>)repository.findUserByUsername(username);
		}
		return (List<User>) repository.findAll();
	}
	
	@GetMapping("/api/user/{userId}")
	public User findUserById(@PathVariable("userId") int id) {
		Optional<User> data = repository.findById(id);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@GetMapping("/api/find")
	public List<User> findUserByUsername(@RequestParam(name="username", required=true) String username) {
		return (List<User>)repository.findUserByUsername(username);
	}
	
	@GetMapping("/api/profile")
	public User profile(HttpSession session) {
		User user = (User) session.getAttribute("user");
//		System.out.println("profile : " + (user == null));
		return user;
	}
		
	@PostMapping("/api/login")
	public User login(@RequestBody User user, HttpSession session) {
		String username = user.getUsername();
		String password = user.getPassword();
		List<User> foundUser = (List<User>) repository.findUserByUsernameAndPassword(username, password);
		if(foundUser.size() == 0) {
			user.setRole("invalid");
			return user;
		}
		User currentUser = foundUser.get(0);
		session.setAttribute("user", currentUser);
		return currentUser;
	}
	
	@PostMapping("/api/logout")
	public User logout(HttpSession session) {
		User user = (User)session.getAttribute("user");
		session.invalidate();
		return user;
	}
	
	@PostMapping("/api/register")
	public User register(@RequestBody User user, HttpSession session) {
		String username = user.getUsername();
		List<User> foundUser = (List<User>)repository.findUserByUsername(username);
		if(foundUser.size() > 0) {
			user.setRole("invalid");
			return user;
		}
		session.setAttribute("user", user);
		createUser(user);
		return user;
	}	
	
	@PutMapping("/api/profile")
	public User updateProfile(@RequestBody User user, HttpSession session) {
		User currentUser = (User)session.getAttribute("user");
		if(currentUser != null) {
			currentUser.setDateOfBirth(user.getDateOfBirth());
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setRole(user.getRole());
			repository.save(currentUser);
			return currentUser;
		}
		return null;
	}
	
	@PutMapping("/api/user/{userId}")
	public User updateUser(@PathVariable("userId") int id, @RequestBody User user) {
		Optional<User> data = repository.findById(id);
		if(data.isPresent()) {
			User newUser = data.get();
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			newUser.setPassword(user.getPassword());
			newUser.setUsername(user.getUsername());
			newUser.setRole(user.getRole());
			repository.save(newUser);
			return newUser;
		}
		return null;
	}
	
	@PostMapping("/api/user")
	public User createUser(@RequestBody User user) {
		return repository.save(user);
	}

	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		repository.deleteById(id);
	}
}
