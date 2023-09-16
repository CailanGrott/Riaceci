package com.cailangrott.riaceci.customer.service;

import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.customer.dto.*;
import com.cailangrott.riaceci.customer.mapper.CustomerMapper;
import com.cailangrott.riaceci.customer.repository.CustomerRepository;
import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public AddNewCustomerInput addNewCustomer(AddNewCustomerOutput addNewCustomerOutput) {
        CustomerModel customer = CustomerMapper.mapDtoToCustomer(addNewCustomerOutput);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer = customerRepository.saveAndFlush(customer);
        return CustomerMapper.mapCustomerToNewCustomerDTO(customer);
    }

    public CustomerModel findCustomerById(Integer id) throws ResourceNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow();
    }

    public FindCustomerByCnpj findCustomerByCnpj(String cnpj) throws ResourceNotFoundException {
        CustomerModel customer = customerRepository.findCustomerByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("No customer found with the CNPJ: " + cnpj));

        return CustomerMapper.mapCustomerToFindCustomerByCnpj(customer);
    }

    public Iterable<FindAllCustomerDTO> findAllCustomer() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::mapCustomerToFindAllDTO)
                .toList();
    }

    public void deleteCustomer(Integer id) throws ResourceNotFoundException {
        var customerReturn = customerRepository.findById(id)
                .orElseThrow();
        customerRepository.delete(customerReturn);
    }

    public void updateCustomer(Integer id, UpdateCustomerDTO updateCustomerDTO) throws ResourceNotFoundException {
        var customerReturn = validateCustomerAttributesUpdate(updateCustomerDTO,
                customerRepository.findById(id).orElseThrow());
        customerRepository.updateCustomerById(customerReturn.getName(),
                customerReturn.getCnpj(),
                customerReturn.getEmail(),
                String.valueOf(customerReturn.getCustomerType()),
                id);
    }

    private CustomerModel validateCustomerAttributesUpdate(UpdateCustomerDTO updateCustomerDTO, CustomerModel customer) {
        return CustomerModel.builder()
                .name(updateCustomerDTO.name().isBlank() ? customer.getName() : updateCustomerDTO.name())
                .cnpj(updateCustomerDTO.cnpj().isBlank() ? customer.getCnpj() : updateCustomerDTO.cnpj())
                .email(updateCustomerDTO.email().isBlank() ? customer.getEmail() : updateCustomerDTO.email())
                .customerType(updateCustomerDTO.customerType() == null ? customer.getCustomerType() : updateCustomerDTO.customerType())
                .build();
    }
}
