package com.Klaus.be_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request2 {

    public String model;
    public String name;
    public String phoneNumber;
    public String emailId;
    public String price;
    public String type;
    public String startDate;
    public String endDate;
    public String postalCode;

}


