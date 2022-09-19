package top.mybi.common.rabbitmq.data;

import lombok.*;

import java.io.Serializable;
/**
 * @Author mustang
 * @date 2022/2/7 15:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RabbitData<T> implements Serializable {
    private String uuid;
    private T message;
}