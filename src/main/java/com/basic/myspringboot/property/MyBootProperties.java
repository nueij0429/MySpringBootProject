package com.basic.myspringboot.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("myboot")
@Getter @Setter
public class MyBootProperties {
    private String name;
    private int age;
    private String fullName;
}
