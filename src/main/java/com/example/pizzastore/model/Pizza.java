package com.example.pizzastore.model;


import com.example.pizzastore.validation.LowerCase;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
@Table(name = "mypizza")
public class Pizza {
    static final String required = "'This field is required'";

    @Size(max = 40)
    @ApiModelProperty(required = true)
    @NotNull(message = "name: "+required)
    private String name;

    @Id
    @Size(max = 40)
    @ApiModelProperty(required = true,value = "lower case only")
    @NotNull(message = "slug: "+required)
    @LowerCase
    private String slug;

    @ApiModelProperty(required = true)
    @NotNull(message = "size: "+required)
    private Integer size;

    @DecimalMin(value = "0.01", message = "Minimal price is 0.01")
    @NotNull(message = "price: "+required)
    @ApiModelProperty(required = true)
    private double price;

    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'")
    @ApiModelProperty(value = "auto-generated when API is executed")
    @CreationTimestamp
    private Date date;

}
