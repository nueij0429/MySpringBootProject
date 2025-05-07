package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Rollback(value = false)
    void testDeleteCustomer() {
        Customer customer = customerRepository.findById(10L) //Optional<Customer>
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        customerRepository.delete(customer);
    }

    @Test
    @Rollback(value = false)
    void testUpdateCustomer() {
        Customer customer = customerRepository.findById(1L) //Optional<Customer>
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        //수정하려면 Entity의 setter method를 호출한다.
        //update customers set customer_id=?,customer_name=? where id=? (@DynamicUpdate 적용 전)
        customer.setCustomerName("홍길동");
        //customerRepository.save(customer);
        assertThat(customer.getCustomerName()).isEqualTo("홍길동");
    }

    @Test
    void testByNotFoundException() {
        //<X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)
        //Supplier 의 추상메서드 T get()
        Customer customer = customerRepository.findByCustomerId("A004")
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        //assertThat(customer.getCustomerId()).isEqualTo("A001");
    }

    @Test
    //@Disabled
    void testFindBy() {
        Optional<Customer> optionalCustomer = customerRepository.findById(1L);
        //assertThat(optionalCustomer).isNotEmpty();
        if(optionalCustomer.isPresent()) {
            Customer existCustomer = optionalCustomer.get();
            assertThat(existCustomer.getId()).isEqualTo(1L);
        }
        //Optional의 ElseGet(Supplier<? extends T> supplier)
        //Supplier의 추상메서드 T get()
        Optional<Customer> optionalCustomer2 = customerRepository.findByCustomerId("A001");
        Customer a001customer = optionalCustomer2.orElseGet(() -> new Customer()) ;
        assertThat(a001customer.getCustomerName()).isEqualTo("스프링");

        Customer notFoundCustomer = customerRepository.findByCustomerId("A004")
                .orElseGet(() -> new Customer());
        assertThat(notFoundCustomer.getCustomerName()).isNull();
    }

    @Test
    @Rollback(value = false)
    @Disabled
    void testCreateCustomer() {
        //Given
        Customer customer = new Customer();
        customer.setCustomerId("A002");
        customer.setCustomerName("스프링2");
        //When
        Customer addCustomer = customerRepository.save(customer);
        //Then
        assertThat(addCustomer).isNotNull();
        assertThat(addCustomer.getCustomerName()).isEqualTo("스프링2");
    }

}