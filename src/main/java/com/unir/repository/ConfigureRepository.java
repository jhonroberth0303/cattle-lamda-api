package com.unir.repository;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.unir.entities.Bovine;
import com.unir.util.LambdaContext;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class ConfigureRepository {

    protected AmazonDynamoDB client;
    protected DynamoDBMapper mapper;
    protected DynamoDB dynamoDB;
    protected LambdaContext lambdaContext;
    protected DynamoDbEnhancedClient enhancedClient;
    protected DynamoDbTable<Bovine> table;

    public ConfigureRepository(String tableName) {

        lambdaContext = new LambdaContext();

        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tableName)).build();

        this.client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_1).build();
        this.mapper = new DynamoDBMapper(client, mapperConfig);
        this.dynamoDB = new DynamoDB(client);

        // Create a DynamoDbEnhancedClient and use the DynamoDbClient object
        Region region = Region.US_EAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder().region(region).build();
        enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(ddb).build();
        table = enhancedClient.table(tableName, TableSchema.fromBean(Bovine.class));

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
