//package com.unir.services.impl;
//
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.LambdaLogger;
//import com.unir.dtos.BovineDTO;
//import com.unir.entities.Bovine;
//import com.unir.mapper.BovineMapperImpl;
//import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
//import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
//import software.amazon.awssdk.enhanced.dynamodb.Expression;
//import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
//import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
//import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
//import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
//
//import java.util.*;
//
//public class ScanBovine {
//
//    private BovineMapperImpl bovineMapper;
//
//    public List<BovineDTO> searchBovine(Context context) {
//
//        LambdaLogger logger = context.getLogger();
//
//        logger.log("Inicio searchBovine...");
//
//        String gender = "female";
//
//        Region region = Region.US_EAST_1;
//        DynamoDbClient ddb = DynamoDbClient.builder()
//                .region(region)
//                .build();
//
//        // Create a DynamoDbEnhancedClient and use the DynamoDbClient object
//        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
//                .dynamoDbClient(ddb)
//                .build();
//
//        // Create a DynamoDbTable object based on Bovine
//        DynamoDbTable<Bovine> table = enhancedClient.table("Bovine", TableSchema.fromBean(Bovine.class));
//
//        ArrayList<BovineDTO> bovines = new ArrayList<>();
//
//        try {
//            AttributeValue attVal = AttributeValue.builder()
//                    .s(gender)
//                    .build();
//            // Get only items in the Employee table that match the date
//            Map<String, AttributeValue> myMap = new HashMap<>();
//            myMap.put(":val1", attVal);
//
//            Map<String, String> myExMap = new HashMap<>();
//            myExMap.put("#gender", "gender");
//            Expression expression = Expression.builder()
//                    .expressionValues(myMap)
//                    .expressionNames(myExMap)
//                    .expression("#gender = :val1")
//                    .build();
//
//            ScanEnhancedRequest enhancedRequest = ScanEnhancedRequest.builder()
//                    .filterExpression(expression)
//                    .limit(15)
//                    .build();
//
//            Iterator<Bovine> bovinesIterator = table.scan(enhancedRequest).items().iterator();
//
//            bovineMapper = new BovineMapperImpl();
//            while (bovinesIterator.hasNext()) {
//                Bovine bovine = bovinesIterator.next();
//                bovines.add(bovineMapper.toDTO(bovine));
//            }
//
//
//        } catch (DynamoDbException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//        return bovines;
//    }
//
//}
//
