package com.example.demo;

import com.example.demo.model.NetworkDrive;
import com.example.demo.model.NetworkDriveCategory;
import com.example.demo.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class NetworkDriveTest {

    @InjectMocks
    private NetworkDrive networkDrive;

    @Mock
    private NetworkDriveCategory networkDriveCategory;

    @Mock
    private Order order;

    @BeforeEach
    public void setUp() {
        networkDrive = new NetworkDrive();
        networkDrive.setNetworkDriveName("TestDrive");
        networkDrive.setPrice(100.0);
        networkDrive.setNetworkDriveInfo("Test Info");
        networkDrive.setOrders(Collections.singletonList(order));
        networkDrive.setNetworkDriveCategory(networkDriveCategory);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals("TestDrive", networkDrive.getNetworkDriveName());
        assertEquals(100.0, networkDrive.getPrice());
        assertEquals("Test Info", networkDrive.getNetworkDriveInfo());
        assertEquals(Collections.singletonList(order), networkDrive.getOrders());
        assertEquals(networkDriveCategory, networkDrive.getNetworkDriveCategory());
    }

    @Test
    public void testToString() {
        String expectedToString = "networkDrive{" +
                "networkDriveId=null" +
                ", productName='TestDrive'" +
                ", price=100.0" +
                ", order=" + Collections.singletonList(order) +
                ", networkDriveCategory=" + networkDriveCategory +
                '}';
        assertEquals(expectedToString, networkDrive.toString());
    }
}
