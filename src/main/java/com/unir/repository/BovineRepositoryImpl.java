package com.unir.repository;

import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.unir.dtos.util.MessageDTO;
import com.unir.entities.Bovine;
import com.unir.mapper.BovineMapperImpl;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public class BovineRepositoryImpl extends ConfigureRepository implements BovineRepository {

    private static final String TABLE_BOVINE = System.getenv("TABLE_BOVINE");

    private BovineMapperImpl bovineMapper;

    public BovineRepositoryImpl(){
        super(TABLE_BOVINE);
        this.bovineMapper = new BovineMapperImpl();
    }

    @Override
    public List<Bovine> findById(Long id) {
        List<Bovine> result = new ArrayList<>();

        try {
            AttributeValue attVal = AttributeValue.builder().s(String.valueOf(id)).build();
            // Get only items in the Employee table that match the date
            Map<String, AttributeValue> myMap = new HashMap<>();
            myMap.put(":val1", attVal);

            Map<String, String> myExMap = new HashMap<>();
            myExMap.put("#id", "id");
            Expression expression = Expression.builder()
                    .expressionValues(myMap)
                    .expressionNames(myExMap)
                    .expression("#id = :val1")
                    .build();

            ScanEnhancedRequest enhancedRequest = ScanEnhancedRequest.builder()
                    .filterExpression(expression)
                    .limit(15)
                    .build();

            Iterator<Bovine> bovinesIterator = table.scan(enhancedRequest).items().iterator();

            bovineMapper = new BovineMapperImpl();
            while (bovinesIterator.hasNext()) {
                Bovine bovine = bovinesIterator.next();
                result.add(bovine);
            }

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return result;
    }

    @Override
    public List<Bovine> findByGender(String gender) {

        List<Bovine> result = new ArrayList<>();

        try {
            AttributeValue attVal = AttributeValue.builder().s(gender).build();
            // Get only items in the Employee table that match the date
            Map<String, AttributeValue> myMap = new HashMap<>();
            myMap.put(":val1", attVal);

            Map<String, String> myExMap = new HashMap<>();
            myExMap.put("#gender", "gender");
            Expression expression = Expression.builder()
                    .expressionValues(myMap)
                    .expressionNames(myExMap)
                    .expression("#gender = :val1")
                    .build();

            ScanEnhancedRequest enhancedRequest = ScanEnhancedRequest.builder()
                    .filterExpression(expression)
                    .limit(15)
                    .build();

            Iterator<Bovine> bovinesIterator = table.scan(enhancedRequest).items().iterator();

            bovineMapper = new BovineMapperImpl();
            while (bovinesIterator.hasNext()) {
                Bovine bovine = bovinesIterator.next();
                result.add(bovine);
            }

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return result;
    }

    @Override
    public MessageDTO insert(Bovine bovine) {
        MessageDTO result;
        try{
            this.mapper.save(bovine);
            result = new MessageDTO("Bovine has saved successful", "200");

        }catch(Exception ex){
            lambdaContext.logException("ErrorSaving: ", ex.getMessage());
            return new MessageDTO("Bovine has not saved successful", "401");
        }

        return result;
    }

    @Override
    public MessageDTO update(Bovine bovine) {
        MessageDTO result;

        int valueId = Optional.of(bovine.getId()).orElse(0);

        if( valueId == 0 || mapper.load(Bovine.class, bovine.getId()) != null){
            return new MessageDTO("Bovine has not saved successful", "404");
        }

        try{
            this.mapper.save(bovine);
            result = new MessageDTO("Bovine has saved successful", "200");

        }catch(Exception ex){
            lambdaContext.logException("ErrorSaving: ", ex.getMessage());
            return new MessageDTO("Bovine has not saved successful", "500");
        }

        return result;
    }


    @Override
    public MessageDTO deleteById(Long id) {
        MessageDTO messageDTO;
        try{
            TableWriteItems tableWriteItems = new TableWriteItems(TABLE_BOVINE);
            Bovine bovine  = mapper.load(Bovine.class, id);

            if(Objects.isNull(bovine)){
                return  new MessageDTO("Bovine Bovine not exist in DynamoDB","200");
            }
            mapper.delete(bovine);
            messageDTO = new MessageDTO("Bovine was delete successfully: " + id,"200");
        }catch (Exception ex){
            lambdaContext.logException("ErrorDeleting: ",  ex.getMessage());
            messageDTO = new MessageDTO("Bovine was not deleting successfully: " + id,"401");
        }

        return messageDTO;
    }

    @Override
    public List<Bovine> findAll() {
        List<Bovine> bovines = new ArrayList<>();
        try{
            ScanEnhancedRequest enhancedRequest = ScanEnhancedRequest.builder()
                    .limit(15).build();

            Iterator<Bovine> bovinePageIterable = table.scan(enhancedRequest).items().iterator();

            bovineMapper = new BovineMapperImpl();
            while (bovinePageIterable.hasNext()) {
                Bovine bovine = bovinePageIterable.next();
                bovines.add(bovine);
            }

        }catch(Exception ex){
            lambdaContext.logException("ErrorGetting: ",  ex.getMessage());
        }
        return bovines;
    }
}
