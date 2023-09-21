package com.infy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.entity.User;
import com.infy.exceptions.ResourceNotFoundException;
import com.infy.repo.UserRepo;

@RestController 
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/getall")
	public List<User> getAllUsers(){
		return this.userRepo.findAll();
	}
	
	@GetMapping("/getuser/{id}")
	public User getUserById(@PathVariable long id) {
		return this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(" User not found with id: " +id));
	}
	
	@PostMapping("/createuser")
	public User createUser(@RequestBody User user) {
		return this.userRepo.save(user);
	}
	
	@PutMapping("/updateuser/{id}")
	public User updateUser(@PathVariable long id, @RequestBody User user) {
		
		User existing = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(" User not found with id: " +id));
	
		existing.setFname(user.getFname());
		existing.setLname(user.getLname());
		existing.setEmail(user.getEmail());
		
		return this.userRepo.save(existing);
		
	}
	
	
	@DeleteMapping("/deleteuser/{id}")
	public String  deleteUser(@PathVariable long id) {
		User user = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(" User not found with id: " +id));

		this.userRepo.delete(user);
		return "User deleted successfully";
	}
	
}
