package com.myconnector.web;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class ChecklistCommand {

    private String id;

    private List checks;

    public void setChecks(List checks) {
        this.checks = checks;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getChecks() {
        return checks;
    }

    public String getId() {
        return id;
    }

    public ChecklistCommand() {
        checks = new ArrayList();
    }
}