package com.sipue.common.core.json;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * 金额格式转换
 * @Author wangjunyu
 * @date
 * @version 1.0
 */
public class AmountSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)){
            value = new BigDecimal(0);
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        gen.writeString(decimalFormat.format(value));
    }
}
