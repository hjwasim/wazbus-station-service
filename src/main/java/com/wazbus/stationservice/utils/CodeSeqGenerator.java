package com.wazbus.stationservice.utils;

import com.wazbus.stationservice.enums.EntityCodePrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class CodeSeqGenerator {

    private final StringRedisTemplate template;

    @Autowired
    public CodeSeqGenerator(StringRedisTemplate template) {
        this.template = template;
    }

    public String generate(EntityCodePrefix codePrefix, String name) {
        int hash = Math.abs(name.toUpperCase().hashCode() % 100);
        String yearCode = Year.now().toString();
        Long seq = template.opsForValue().increment("seq:" + codePrefix.name());
        return codePrefix.getPrefix() + hash + yearCode + seq;
    }
}
