package com.example.aws.springbootdynamodb.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.aws.springbootdynamodb.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository  {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Employee save(Employee employee){
        dynamoDBMapper.save(employee);
        return employee;
    }

    public Employee findById(String empid){
       return dynamoDBMapper.load(Employee.class, empid);
    }

    public List<Employee> findAll(){
        return dynamoDBMapper.scan(Employee.class, new DynamoDBScanExpression());
    }

    public String update(String empid, Employee employee){
        dynamoDBMapper.save(employee,
                new DynamoDBSaveExpression()
        .withExpectedEntry("empid",
                new ExpectedAttributeValue(
                        new AttributeValue().withS(empid)
                )));
        return "employee updated successfully:: "+empid;
    }

    public String delete(String empid){
        Employee employee = dynamoDBMapper.load(Employee.class, empid);
        dynamoDBMapper.delete(employee);
        return "employee deleted successfully:: "+empid;
    }


}
