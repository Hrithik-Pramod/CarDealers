package com.Klaus.be_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    public String model;
    public String name;
    public String phoneNumber;
    public String emailId;
}


