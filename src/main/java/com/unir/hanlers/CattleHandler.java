package com.unir.hanlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unir.dtos.BovineDTO;
import com.unir.mapper.BovineMapperImpl;
import com.unir.repository.BovineRepositoryImpl;
import com.unir.services.BovineService;
import com.unir.services.impl.BovineServiceImpl;

import java.util.List;
import java.util.Map;

/**
 *  This is the entry point for the Lambda function
 */

public class CattleHandler implements RequestHandler<Map<String,Object>, List<BovineDTO>> {

    private BovineService bovineService;
    private LambdaLogger logger;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public CattleHandler() {
        bovineService = new BovineServiceImpl(new BovineRepositoryImpl(), new BovineMapperImpl());
    }

    @Override
    public List<BovineDTO> handleRequest(Map<String,Object> input, Context context) {
        logger = context.getLogger();
        logger.log("EVENT: " + gson.toJson(input));

        Map<String, Object> pathParameters = (Map<String, Object>) input.get("pathParameters");
        logger.log("PahParameters: " + gson.toJson(pathParameters));
        String gender = (String) pathParameters.get("gender");
        logger.log("gender parameter: " + gender);

        return bovineService.findByGender(gender);
    }
}