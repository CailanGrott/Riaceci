package com.cailangrott.riaceci.customer.service;

import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.customer.dto.AddNewCustomerOutput;
import com.cailangrott.riaceci.customer.dto.FindCustomerByCnpj;
import com.cailangrott.riaceci.customer.dto.FindCustomerByName;
import com.cailangrott.riaceci.customer.dto.UpdateCustomerDTO;
import com.cailangrott.riaceci.customer.enums.CustomerType;
import com.cailangrott.riaceci.customer.repository.CustomerRepository;
import com.cailangrott.riaceci.exception.CustomerAlreadyExistsException;
import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import com.cailangrott.riaceci.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerService customerService;

    private AddNewCustomerOutput addNewCustomerOutput;

    @BeforeEach
    public void setUp() {
        addNewCustomerOutput = new AddNewCustomerOutput("Test Name", "12345678912345", "test@example.com", "testPassword", CustomerType.SUPERMARKET.getDescription());
    }

    @Test
    public void findCustomerById_ShouldReturnCustomer_WhenCustomerExists() throws ResourceNotFoundException {
        CustomerModel mockCustomer = new CustomerModel();
        mockCustomer.setId(1);
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(mockCustomer));
        CustomerModel result = customerService.findCustomerById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void findCustomerByCnpj_ShouldReturnCustomer_WhenCustomerExists() throws ResourceNotFoundException {
        CustomerModel mockCustomer = new CustomerModel();
        mockCustomer.setCnpj("12345678901234");
        when(customerRepository.findOptionalCustomerByCnpj(anyString())).thenReturn(Optional.of(mockCustomer));
        FindCustomerByCnpj result = customerService.findCustomerByCnpj("12345678901234");
        assertNotNull(result);
        assertEquals("12345678901234", result.cnpj());
    }

    @Test
    public void findCustomerByName_ShouldReturnCustomer_WhenCustomerExists() throws ResourceNotFoundException {
        CustomerModel mockCustomer = new CustomerModel();
        mockCustomer.setName("Test Name");
        when(customerRepository.findCustomerByName(anyString())).thenReturn(Optional.of(mockCustomer));
        FindCustomerByName result = customerService.findCustomerByName("Test Name");
        assertNotNull(result);
        assertEquals("Test Name", result.name());
    }

    @Test
    public void updateCustomer_ShouldUpdateCustomerAttributes() throws ResourceNotFoundException {
        CustomerModel mockCustomer = new CustomerModel();
        mockCustomer.setId(1);
        mockCustomer.setName("Old Name");
        UpdateCustomerDTO updateCustomerDTO = new UpdateCustomerDTO("New Name", "12345678901234", "new@example.com", null);
        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(mockCustomer));
        customerService.updateCustomer(1, updateCustomerDTO);
        verify(customerRepository, times(1)).updateCustomerById(anyString(), anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    public void addNewCustomer_ShouldThrowCustomerAlreadyExistsException_WhenCustomerExists() {
        when(customerRepository.findCustomerByCnpj(anyString())).thenReturn(new CustomerModel());
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.addNewCustomer(addNewCustomerOutput));
    }

    @Test
    public void findCustomerByIdStatic_ShouldThrowResourceNotFoundException_WhenCustomerDoesNotExist() {
        when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.findCustomerById(999));
    }

    @Test
    public void findCustomerByCnpj_ShouldThrowResourceNotFoundException_WhenCustomerDoesNotExist() {
        when(customerRepository.findOptionalCustomerByCnpj(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.findCustomerByCnpj("99999999999999"));
    }

    @Test
    public void findCustomerByName_ShouldThrowResourceNotFoundException_WhenCustomerDoesNotExist() {
        when(customerRepository.findCustomerByName(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.findCustomerByName("NonExistentName"));
    }

    @Test
    public void deleteCustomer_ShouldThrowResourceNotFoundException_WhenCustomerDoesNotExist() {
        when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(999));
    }

    @Test
    public void findCustomerById_ShouldThrowResourceNotFoundException_WhenCustomerDoesNotExist() {
        when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.findCustomerById(999));
    }
}
