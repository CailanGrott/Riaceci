package com.cailangrott.riaceci.customer.mapper;

import com.cailangrott.riaceci.customer.dto.AddNewCustomerInput;
import com.cailangrott.riaceci.customer.dto.AddNewCustomerOutput;
import com.cailangrott.riaceci.customer.dto.FindAllCustomerDTO;
import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.customer.enums.CustomerType;

public class CustomerMapper {
    public static AddNewCustomerInput mapCustomerToNewCustomerDTO(CustomerModel customer) {
        return AddNewCustomerInput.builder()
                .id(customer.getId())
                .name(customer.getName())
                .cnpj(customer.getCnpj())
                .email(customer.getEmail())
                .customerType(customer.getCustomerType().getDescription())
                .build();
    }

    public static FindAllCustomerDTO mapCustomerToFindAllDTO(CustomerModel customer) {
        return FindAllCustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .cnpj(customer.getCnpj())
                .email(customer.getEmail())
                .customerType(customer.getCustomerType())
                .build();
    }

    public static CustomerModel mapDtoToCustomer(AddNewCustomerInput addNewCustomerInput) {
        return CustomerModel.builder()
                .id((addNewCustomerInput.id()))
                .name(addNewCustomerInput.name())
                .cnpj(addNewCustomerInput.cnpj())
                .email(addNewCustomerInput.email())
                .customerType(CustomerType.fromStringIgnoreCase(addNewCustomerInput.customerType()))
                .build();
    }

    public static CustomerModel mapDtoToCustomer(AddNewCustomerOutput addNewCustomerOutput) {
        return CustomerModel.builder()
                .name(addNewCustomerOutput.name())
                .cnpj(addNewCustomerOutput.cnpj())
                .email(addNewCustomerOutput.email())
                .password(addNewCustomerOutput.password())
                .customerType(CustomerType.fromStringIgnoreCase(addNewCustomerOutput.customerType()))
                .build();
    }
}
