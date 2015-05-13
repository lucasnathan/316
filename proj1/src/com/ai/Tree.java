package com.ai;

import java.util.HashMap;

/**
 * Created by lucas on 16/03/15.
 */
public class Tree {
    private final static int ROOT = 1;
    private HashMap<Integer, Node> nodes;

    public Tree() {
        this.nodes = new HashMap<Integer, Node>();
    }

    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public void setNodes(HashMap<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    public Node addNode(int identifier, int parent, String table) {
        Node node = new Node(identifier, table);
        nodes.put(identifier, node);

        if (parent != 0) {
            nodes.get(parent).addChild(node);
            node.setParent(nodes.get(parent));
        }

        return node;
    }
}
