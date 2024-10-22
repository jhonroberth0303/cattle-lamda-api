package com.unir.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "Bovine")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bovine {


    @DynamoDBHashKey(attributeName = "id")
    private int id;
    private String name;
    @DynamoDBRangeKey(attributeName = "gender")
    private String gender;
    private String bornDate;
    private String breed;
    private String color;
    private String father;
    private String mother;

}
