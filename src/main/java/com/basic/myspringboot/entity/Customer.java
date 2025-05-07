package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="customers")
@Getter @Setter
@DynamicUpdate
public class Customer {
    //Pk 값을 persistence provider가 결정해라.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //중복을 허용하지 않고, Null도 허용하지 않는다.
    @Column(unique = true, nullable = false)
    private String customerId;

    //Null을 허용하지 않는다.
    @Column(nullable = false)
    private String customerName;

}
