package com.you.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * ChatRoomRequest 类
 *
 * @author: YangRun
 * @date: 2020/2/15
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatRoomRequest {

    private String userId;
    private String userName;
    private String chatMsg;
}
