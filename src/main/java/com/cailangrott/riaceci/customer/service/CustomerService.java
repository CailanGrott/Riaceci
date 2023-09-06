package com.cailangrott.riaceci.customer.service;

import com.cailangrott.riaceci.customer.dto.AddNewCustomerInput;
import com.cailangrott.riaceci.customer.dto.AddNewCustomerOutput;
import com.cailangrott.riaceci.customer.dto.FindAllCustomerDTO;
import com.cailangrott.riaceci.customer.dto.UpdateCustomerDTO;
import com.cailangrott.riaceci.customer.mapper.CustomerMapper;
import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.customer.repository.CustomerRepository;
import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.cailangrott.riaceci.customer.mapper.CustomerMapper.mapCustomerToNewCustomerDTO;
import static com.cailangrott.riaceci.customer.mapper.CustomerMapper.mapDtoToCustomer;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public AddNewCustomerInput addNewCustomer(AddNewCustomerOutput addNewCustomerOutput) {
        CustomerModel customer = customerRepository.saveAndFlush(mapDtoToCustomer(addNewCustomerOutput));
        return mapCustomerToNewCustomerDTO(customer);
    }

    public CustomerModel findCustomerById(Integer id) throws ResourceNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Iterable<FindAllCustomerDTO> findAllCustomer() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::mapCustomerToFindAllDTO)
                .toList();
    }

    public void deleteCustomer(Integer id) throws ResourceNotFoundException {
        var customerReturn = customerRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        customerRepository.delete(customerReturn);
    }

    public void updateCustomer(Integer id, UpdateCustomerDTO updateCustomerDTO) throws ResourceNotFoundException {
        var customerReturn = validateCustomerAttributesUpdate(updateCustomerDTO,
                customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
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
