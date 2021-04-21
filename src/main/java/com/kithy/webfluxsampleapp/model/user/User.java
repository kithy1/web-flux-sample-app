package com.kithy.webfluxsampleapp.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
public class User {

    private String id;

    private String username;

    private String password;
}
