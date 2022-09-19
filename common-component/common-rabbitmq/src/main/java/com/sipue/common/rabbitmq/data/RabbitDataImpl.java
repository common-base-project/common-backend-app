package com.sipue.common.rabbitmq.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * @Author mustang
 * @date 2022/2/7 15:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RabbitDataImpl implements Serializable {
    private String data;
}