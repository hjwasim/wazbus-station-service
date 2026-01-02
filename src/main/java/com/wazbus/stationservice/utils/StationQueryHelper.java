package com.wazbus.stationservice.utils;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class StationQueryHelper {

   public Query isStationExistByCodeQuery(String code) {
       Query query = new Query();
       Criteria criteria = Criteria.where("code").is(code);
       query.addCriteria(criteria);
       return query;
   }

   public Query isStationExistByNameAndCityQuery(String name, String city) {
       Query query = new Query();
       Criteria nameCriteria = Criteria.where("name").is(name);
       Criteria cityCriteria = Criteria.where("city").is(city);
       Criteria combinedCriteria = new Criteria().andOperator(nameCriteria, cityCriteria);
       query.addCriteria(combinedCriteria);
       return query;
   }
}
