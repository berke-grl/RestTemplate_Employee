package org.example;

import org.example.Model.Employee;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

public class App {
    private static RestTemplate restTemplate;

    public static void main(String[] args) {
        restTemplate = new RestTemplate();
        ResponseErrorHandler handler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                    System.out.println("has error part");
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                System.out.println("handle error part");
            }
        };

        restTemplate.setErrorHandler(handler);
        //getAll();
        //getById(1);
        //deleteEmployee(3);
        addEmployee(new Employee("Gizem", 542));
    }

    public static void getAll() {
        String url = "http://localhost:8080/employee/getAll";
        ResponseEntity<Employee[]> response = restTemplate.getForEntity(url, Employee[].class);

        List<Employee> employees = Arrays.asList(response.getBody());
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    public static void getById(int id) {
        String url = "http://localhost:8080/employee/getById/{searchedId}";
        Map<String, Integer> param = new HashMap<>();
        param.put("searchedId", id);

        ResponseEntity<Employee> response = restTemplate.getForEntity(url, Employee.class, param);
        Employee employee = response.getBody();

        if (employee != null && response.getStatusCode().value() < 404) {
            System.out.println(employee);
        } else {
            System.out.println(id + " ID Employee not found");
        }
    }

    public static void deleteEmployee(int id) {
        String url = "http://localhost:8080/employee/deleteById/{deletedId}";
        Map<String, Integer> param = new HashMap<>();
        param.put("deletedId", id);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class, param);

        System.out.println(response.getBody());
        if (response.getStatusCode() != HttpStatus.NOT_FOUND && response.getStatusCode() != HttpStatus.INTERNAL_SERVER_ERROR) {
            System.out.println(response.getStatusCode() + " - ID Employee has been removed !");
        } else {
            System.out.println("Delete process has failed");
        }
    }

    public static void addEmployee(Employee employee) {
        String url = "http://localhost:8080/employee/save";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Employee> requestEntity = new HttpEntity<>(employee, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        System.out.println(response.getBody());
        if (response.getBody() != null && !response.getStatusCode().is4xxClientError() && !response.getStatusCode().is5xxServerError()) {
            System.out.println(employee + " -- NEW EMPLOYEE ADDED TO THE SYSTEM");
        } else {
            System.out.println("CAN NOT ADDED NEW PERSON !");
        }

    }
}
