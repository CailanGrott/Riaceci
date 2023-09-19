package com.cailangrott.riaceci.customer.contoller;

import com.cailangrott.riaceci.customer.dto.*;
import com.cailangrott.riaceci.customer.service.CustomerService;
import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/new-customer")
    public ResponseEntity<AddNewCustomerInput> addNewCustomer(@RequestBody AddNewCustomerOutput addNewCustomerOutput) {
        AddNewCustomerInput addNewCustomerInput = customerService.addNewCustomer(addNewCustomerOutput);
        return new ResponseEntity<>(addNewCustomerInput, HttpStatus.CREATED);
    }

    @GetMapping("/find-customers")
    public ResponseEntity<Iterable<FindAllCustomerDTO>> findAllCustomer() {
        Iterable<FindAllCustomerDTO> findAllCustomerDTO = customerService.findAllCustomer();
        return ResponseEntity.ok(findAllCustomerDTO);
    }

    @GetMapping("/find-by-cnpj/{cnpj}")
    public ResponseEntity<FindCustomerByCnpj> findCustomerByCnpj(@PathVariable("cnpj") String cnpj) throws ResourceNotFoundException {
        return ResponseEntity.ok(customerService.findCustomerByCnpj(cnpj));
    }

    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<FindCustomerByName> findCustomerByName(@PathVariable("name") String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(customerService.findCustomerByName(name));
    }

    @DeleteMapping("/delete-customer/id/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-customer/id/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable("id") Integer id,
                                               @RequestBody UpdateCustomerDTO updateCustomerDTO) throws ResourceNotFoundException {
        customerService.updateCustomer(id, updateCustomerDTO);
        return ResponseEntity.ok().build();
    }
}