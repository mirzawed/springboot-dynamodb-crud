package com.example.aws.springbootdynamodb.controller;


import com.example.aws.springbootdynamodb.entity.Employee;
import com.example.aws.springbootdynamodb.generator.PDFGenerator;
import com.example.aws.springbootdynamodb.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/{empid}")
    public Employee findById(@PathVariable(value = "empid") String empid) {
        return employeeRepository.findById(empid);
    }

    @GetMapping
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @PutMapping("/{empid}")
    public String update(@PathVariable(value = "empid") String empid,
                         @RequestBody Employee employee) {
        return employeeRepository.update(empid, employee);
    }


    @DeleteMapping("/{empid}")
    public String delete(@PathVariable(value = "empid") String empid) {
        return employeeRepository.delete(empid);
    }


    @GetMapping(value = "/employeesPdf",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> customerReport() throws IOException {
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();

        ByteArrayInputStream bis = PDFGenerator.customerPDFReport(employees);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=employee.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


  //addedcfghygjgyhjjj  demo
}
