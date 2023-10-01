package com.cailangrott.riaceci.customer.service;

import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.customer.dto.*;
import com.cailangrott.riaceci.customer.mapper.CustomerMapper;
import com.cailangrott.riaceci.customer.repository.CustomerRepository;
import com.cailangrott.riaceci.exception.CustomerAlreadyExistsException;
import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import com.cailangrott.riaceci.user.repositories.UserRepository;
import com.cailangrott.riaceci.user.user.User;
import com.cailangrott.riaceci.user.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.cailangrott.riaceci.customer.mapper.CustomerMapper.*;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AddNewCustomerInput addNewCustomer(AddNewCustomerOutput addNewCustomerOutput) throws CustomerAlreadyExistsException {
        Optional.ofNullable(customerRepository.findCustomerByCnpj(addNewCustomerOutput.cnpj()))
                .ifPresent(existingCustomer -> {
                    throw new CustomerAlreadyExistsException(
                            HttpStatus.CONFLICT,
                            "Cliente já existe",
                            "Cliente já existe com CNPJ: " + addNewCustomerOutput.cnpj()
                    );
                });

        CustomerModel customer = CustomerMapper.mapDtoToCustomer(addNewCustomerOutput);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole(UserRole.CUSTOMER);
        customer = customerRepository.saveAndFlush(customer);

        createUserFromCustomer(customer);

        return mapCustomerToNewCustomerDTO(customer);
    }

    public CustomerModel findCustomerById(Integer id) throws ResourceNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado",
                        "Nenhum cliente encontrado com o ID: " + id));
    }

    public FindCustomerByCnpj findCustomerByCnpj(String cnpj) throws ResourceNotFoundException {
        CustomerModel customer = customerRepository.findOptionalCustomerByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado",
                        "Nenhum cliente encontrado com o CNPJ: " + cnpj));

        return mapCustomerToFindCustomerByCnpj(customer);
    }

    public FindCustomerByName findCustomerByName(String name) throws ResourceNotFoundException {
        CustomerModel customer = customerRepository.findCustomerByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado",
                        "Nenhum cliente encontrado com o nome: " + name));
        return mapCustomerToFindCustomerByName(customer);
    }

    public Iterable<FindAllCustomerDTO> findAllCustomer() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::mapCustomerToFindAllDTO)
                .toList();
    }

    public void deleteCustomer(Integer id) throws ResourceNotFoundException {
        var customerReturn = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado",
                        "Nenhum cliente encontrado com o ID: " + id));

        deleteUserByLogin(customerReturn.getCnpj());

        customerRepository.delete(customerReturn);
    }

    public void deleteCustomerByCnpj(String cnpj) throws ResourceNotFoundException {
        var customerReturn = customerRepository.findOptionalCustomerByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado",
                        "Nenhum cliente encontrado com o CNPJ: " + cnpj));

        deleteUserByLogin(customerReturn.getCnpj());
        customerRepository.delete(customerReturn);
    }

    public void updateCustomer(Integer id, UpdateCustomerDTO updateCustomerDTO) throws ResourceNotFoundException {
        var customerReturn = validateCustomerAttributesUpdate(updateCustomerDTO,
                customerRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Cliente não encontrado",
                                "Nenhum cliente encontrado com o ID: " + id)));
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

    private void createUserFromCustomer(CustomerModel customer) {
        User user = new User(customer.getCnpj(), customer.getPassword(), customer.getRole());
        userRepository.save(user);
    }

    private void deleteUserByLogin(String login) {
        User user = (User) userRepository.findByLogin(login);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}
