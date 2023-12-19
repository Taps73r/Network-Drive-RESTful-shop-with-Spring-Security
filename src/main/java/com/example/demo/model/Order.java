package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private Long Id;

    private String name;

    private double price;
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + Id +
                ", orderName='" + name + '\'' +
                ", orderPrice='" + price + '\'' +
                ", customer=" + customer +
                '}';
    }
}