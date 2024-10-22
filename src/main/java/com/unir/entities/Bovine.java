package com.unir.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bovine {

    private int id;
    private String name;
    private String gender;
    private String bornDate;
    private String breed;
    private String color;
    private String father;
    private String mother;

    @DynamoDbPartitionKey
    public int getId() {
        return this.id;
    }

    @DynamoDbSortKey
    public String getGender() {
        return this.gender;
    }

}
