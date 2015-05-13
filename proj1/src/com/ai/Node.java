package com.ai;

import java.util.ArrayList;

/**
 * Created by lucas on 16/03/15.
 */
public class Node {

    private int identifier;
    private Node parent;
    private String table;
    private ArrayList<Node> children;

    public Node(int identifier, Node parent, String table) {
        this.table = table;
        this.identifier = identifier;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public Node(int identifier, String table) {
        this.table = table;
        this.identifier = identifier;
        this.children = new ArrayList<>();
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public void addChild(Node node) {
        children.add(node);
    }
}