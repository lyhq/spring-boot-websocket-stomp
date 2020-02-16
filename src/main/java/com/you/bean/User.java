package com.you.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * User 类
 *
 * @author: YangRun
 * @date: 2020/2/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String userName;
    private int age;
}
