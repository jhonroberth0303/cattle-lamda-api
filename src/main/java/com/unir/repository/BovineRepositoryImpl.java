package com.unir.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.unir.dtos.util.MessageDTO;
import com.unir.entities.Bovine;
import com.unir.mapper.BovineMapperImpl;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class BovineRepositoryImpl extends ConfigureRepository implements BovineRepository {

    private static final String TABLE_BOVINE = System.getenv("Bovine");

    private BovineMapperImpl bovineMapper;

    public BovineRepositoryImpl(){
        super(TABLE_BOVINE);
        bovineMapper = new BovineMapperImpl();
    }

    @Override
    public List<Bovine> findById(Long id) {
        List<Bovine> result = null;
        try{
            HashMap<String, AttributeValue> av = new HashMap<>();
            av.put(":id",new AttributeValue().withS(String.valueOf(id)));

            DynamoDBQueryExpression<Bovine> queryExp = new DynamoDBQueryExpression<Bovine>()
                    .withKeyConditionExpression("id = :id")
                    .withExpressionAttributeValues(av);
            result = this.mapper.query(Bovine.class, queryExp);

        }catch(Exception ex){
            lambdaContext.logException("ErrorGetting: ",  ex.getMessage());
        }
        return new LinkedList<>(result);
    }

    @Override
    public List<Bovine> findByGender(String gender) {

        List<Bovine> result = null;

        try {
            HashMap<String, AttributeValue> av = new HashMap<>();
            av.put(":gender",new AttributeValue().withS(String.valueOf(gender)));

            DynamoDBQueryExpression<Bovine> queryExp = new DynamoDBQueryExpression<Bovine>()
                    .withKeyConditionExpression("gender = :gender")
                    .withExpressionAttributeValues(av);
            result = this.mapper.query(Bovine.class, queryExp);


        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return new LinkedList<>(result);
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
        List<Bovine> result = null;
        try{
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            result = mapper.scan(Bovine.class, scanExpression);

        }catch(Exception ex){
            lambdaContext.logException("ErrorGetting: ",  ex.getMessage());
        }
        return new LinkedList<>(result);
    }
}
