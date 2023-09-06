package com.cailangrott.riaceci.customer.contoller;

import com.cailangrott.riaceci.customer.dto.AddNewCustomerInput;
import com.cailangrott.riaceci.customer.dto.AddNewCustomerOutput;
import com.cailangrott.riaceci.customer.dto.FindAllCustomerDTO;
import com.cailangrott.riaceci.customer.dto.UpdateCustomerDTO;
import com.cailangrott.riaceci.customer.service.CustomerService;
import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<AddNewCustomerInput> addNewCustomer(@RequestBody AddNewCustomerOutput addNewCustomerOutput) {
        AddNewCustomerInput addNewCustomerInput = customerService.addNewCustomer(addNewCustomerOutput);
        return new ResponseEntity<>(addNewCustomerInput, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<FindAllCustomerDTO>> findAllCustomer() {
        Iterable<FindAllCustomerDTO> findAllCustomerDTO = customerService.findAllCustomer();
        return new ResponseEntity<>(findAllCustomerDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable("id") Integer id,
                                               @RequestBody UpdateCustomerDTO updateCustomerDTO) throws ResourceNotFoundException {
        customerService.updateCustomer(id, updateCustomerDTO);
        return ResponseEntity.ok().build();
    }
}