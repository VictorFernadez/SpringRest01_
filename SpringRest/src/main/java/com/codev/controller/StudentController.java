package com.codev.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.codev.model.Student;
import com.codev.repository.StudentNotFoundException;
import com.codev.repository.StudentRepository;

@RestController
public class StudentController {
	@Autowired
	private StudentRepository studentRepository;

//	@GetMapping("/students")
//	public List<Student> retrieveAllStudent(){
//		return studentRepository.findAll();
//	}
	@GetMapping("/students")
	public ResponseEntity<List<Student>> retrieveAllStudent() {
		return new ResponseEntity<List<Student>>(studentRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Student> retrieveStudent(@PathVariable long id) {
		Optional<Student> student = studentRepository.findById(id);
		if (!student.isPresent()) {
			throw new StudentNotFoundException("id-" + id);
		}
		return new ResponseEntity<Student>(student.get(), HttpStatus.OK);

	}

	@DeleteMapping("/students/{id}")
	public void deleteStudent(@PathVariable long id) {
		studentRepository.deleteById(id);
	}

	@PostMapping("/students")
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		// Create the student and look for it
		Student savedStudent = studentRepository.save(student);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();
		return ResponseEntity.created(location).build();

	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable long id) {
		Optional<Student> studentOptional = studentRepository.findById(id);
		if (!studentOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		student.setId(id);
		studentRepository.save(student);
		return ResponseEntity.noContent().build();
	}
}
