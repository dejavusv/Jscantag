/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scan.html.model;

import java.util.List;

/**
 *
 * @author Surachai
 */
public class HtmlNodeLink {
    private List<HtmlNode> RootNodeList;
    

    public List<HtmlNode> getRootNodeList() {
        return RootNodeList;
    }

    public void setRootNodeList(List<HtmlNode> RootNodeList) {
        this.RootNodeList = RootNodeList;
    }
    
    public void add(HtmlNode node){
        this.RootNodeList.add(node);
    }

}
