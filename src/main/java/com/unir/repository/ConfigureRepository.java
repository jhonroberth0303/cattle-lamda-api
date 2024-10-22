package com.unir.repository;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.unir.util.LambdaContext;

public class ConfigureRepository {

    protected AmazonDynamoDB client;
    protected DynamoDBMapper mapper;
    protected DynamoDB dynamoDB;
    protected LambdaContext lambdaContext;

    public ConfigureRepository(String tableName) {

        lambdaContext = new LambdaContext();

        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tableName)).build();

        this.client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_1).build();
        this.mapper = new DynamoDBMapper(client, mapperConfig);
        this.dynamoDB = new DynamoDB(client);

    }

    protected void setDbAdapter(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    protected void setClient(AmazonDynamoDB client) {
        this.client = client;
    }

    protected void setMapper(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }


}
