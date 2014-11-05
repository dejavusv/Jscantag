/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scan.html.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Surachai
 */
public class HtmlNode {
    private List<Attibute> AttibuteList; 
    private String  data;

    public List<Attibute> getAttibuteList() {
        return AttibuteList;
    }

    public void setAttibuteList(List<Attibute> AttibuteList) {
        this.AttibuteList = AttibuteList;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public List<String> getALLAttibute(){
        List<String> result =  new LinkedList<String>();
        for(Attibute att : AttibuteList){
            result.add(att.getName());
        }
        return result;
    }
    
    public String getAttibute(String name){
        for(Attibute att : AttibuteList){
            if(name.equalsIgnoreCase(att.getName())){
                return att.getValue();
            }
        }
        return null;
    }
    
}
