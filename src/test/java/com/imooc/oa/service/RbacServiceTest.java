package com.imooc.oa.service;

import com.imooc.oa.entity.Node;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RbacServiceTest {
    private RbacService rbacService = new RbacService();
    @Test
    public void selectNodeByUserId() {
        // 注意是3l，而不是31
        List<Node> nodes = rbacService.selectNodeByUserId(3l);
        for (Node n:nodes) {
            System.out.println(n.getNodeName());
        }
    }
}