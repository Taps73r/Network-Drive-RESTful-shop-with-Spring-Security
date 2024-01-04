package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.model.NetworkDrive;
import com.example.demo.model.Order;
import com.example.demo.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderTest {

    @Mock
    private Customer mockCustomer;

    @Mock
    private NetworkDrive mockNetworkDrive;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setId(1L);
        order.setName("Test Order");
        order.setPrice(100.0);
        order.setStatus(Status.NEW);
        order.setCustomer(mockCustomer);
    }

    @Test
    void addNetworkDrive_shouldAddNetworkDriveToList() {
        when(mockNetworkDrive.getNetworkDriveId()).thenReturn(1L);
        order.addNetworkDrive(mockNetworkDrive);
        assertEquals(1, order.getNetworkDrives().size());
        assertEquals(mockNetworkDrive, order.getNetworkDrives().get(0));
    }

    @Test
    void removeNetworkDrive_shouldRemoveNetworkDriveFromList() {
        when(mockNetworkDrive.getNetworkDriveId()).thenReturn(1L);
        order.addNetworkDrive(mockNetworkDrive);
        order.removeNetworkDrive(1L);
        assertEquals(0, order.getNetworkDrives().size());
    }

    @Test
    void toString_shouldReturnFormattedString() {
        when(mockCustomer.toString()).thenReturn("MockCustomerToString");
        String result = order.toString();
        assertEquals("Order{orderId=1, orderName='Test Order', orderPrice='100.0', customer=MockCustomerToString}", result);
    }
}
