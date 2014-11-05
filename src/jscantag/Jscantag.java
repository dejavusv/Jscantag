/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscantag;

import com.des.generate.GenerateFile;
import com.scan.html.model.Attibute;
import com.scan.html.model.HtmlNode;
import com.scan.html.model.HtmlNodeLink;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Surachai
 */
public class Jscantag {

    GenerateFile g = new GenerateFile();
    private static final String BEAN = "bean";
    private static final String[] ACCESS_MODIFIERS = {"public", "private", "protected"};
    private static final String[] JAVA_KEYWORD = {"abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while"
    };

    private static final String pathFile = "C:\\Users\\Surachai\\Documents\\NetBeansProjects\\Jscantag\\example\\tiles-definitions.xml";
    private static final String pathHtml = "C:\\Users\\Surachai\\Documents\\NetBeansProjects\\Jscantag\\example\\empList.jsp";

    private static final String START_PREFIXTAG = "<";
    private static final String SUPFIXTAG1 = ">";
    private static final String STOP_PREFIXTAG = "</";
    private static final String SUPFIXTAG2 = "/>";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Jscantag h = new Jscantag();
        //h.getListNameTemplateFromTile(pathFile);
        HtmlNodeLink list = h.getHTMLTagElement(pathHtml, "form");
        List<HtmlNode> data = list.getRootNodeList();
       // System.out.println(data.get(0).getData());
       // System.out.println(data.get(1).getData());
        HtmlNodeLink fieldsetlist = h.getHTMLTagElement(data.get(0), "fieldset");
        List<HtmlNode> fieldsetlistdata =  fieldsetlist.getRootNodeList();
        System.out.println(fieldsetlistdata.get(0).getData());
    }

    public List<String> getListNameTemplateFromTile(String pathfile) {
        List<String> ListTemplate = new LinkedList<String>();
        NodeList node = getTagElement(pathfile, "definition");
        List<String> TemplateList = getAttributeFromTagElement(node, "template");
        List<String> nameTemplateList = getAttributeFromTagElement(node, "name");
        for (int i = 0; i < TemplateList.size(); i++) {
            if (!"".equalsIgnoreCase(TemplateList.get(i))) {
                ListTemplate.add(nameTemplateList.get(i));
            }
        }
        return ListTemplate;
    }

    public List<String> getListIDFromSpringBeans(String pathfile) {
        List<String> ListID = new LinkedList<String>();
        NodeList node = getTagElement(pathfile, "bean");
        ListID = getAttributeFromTagElement(node, "id");
        return ListID;
    }

    public List<String> getAttributeFromTagElement(NodeList nList, String tagname) {
        List<String> Listdata = new LinkedList<String>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Listdata.add(eElement.getAttribute(tagname));
            }
        }
        return Listdata;
    }

    public NodeList getTagElement(String pathfile, String tagname) {
        NodeList nList = null;
        try {
            File fXmlFile = new File(pathfile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            nList = doc.getElementsByTagName(tagname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nList;
    }

    public HtmlNodeLink getHTMLTagElement(String pathfile, String tagname) {
        String content = "";
        List<String> datafile = g.readFile(pathfile);
        for (String data : datafile) {
            content += data;
        }
        return AnalysisHTML(content, tagname);
    }

    public HtmlNodeLink AnalysisHTML(String content, String tagname) {
        String dataInTag = content;
        HtmlNodeLink hList = new HtmlNodeLink();
        try {
            while (dataInTag.indexOf(tagname) != -1) {
                int prefix = checkPrefixTagIndex(dataInTag, tagname);
                if (prefix == -1) {
                    dataInTag = dataInTag.substring(dataInTag.indexOf(tagname) + tagname.length());

                } else {
                    HtmlNode node = new HtmlNode();
                    String supfix = setupSupfix(tagname);
                    String headertag = "";
                    int checkTag = 0;
                    //get header tag
                    if ((supfix.equalsIgnoreCase(SUPFIXTAG2) && (dataInTag.indexOf(SUPFIXTAG1, prefix) + SUPFIXTAG1.length() < dataInTag.indexOf(SUPFIXTAG2, prefix) + SUPFIXTAG2.length()))) {
                        checkTag = 1;
                        supfix = SUPFIXTAG1;
                    }

                    headertag = dataInTag.substring(prefix, dataInTag.indexOf(supfix, prefix) + supfix.length());
                    System.out.println("headertag :" + headertag);
                    node.setAttibuteList(readAttbuteList(headertag, tagname, supfix));
                    if (checkTag == 1) {
                        checkTag = 0;
                        supfix = SUPFIXTAG2;
                    }

                    //get data in tag
                    if (!supfix.equalsIgnoreCase(SUPFIXTAG2)) {
                        
                        String datatag = dataInTag.substring(dataInTag.indexOf(supfix, prefix) + supfix.length(), dataInTag.indexOf(STOP_PREFIXTAG + tagname + supfix));
                        //System.out.println("datatag :"+datatag);
                        node.setData(datatag);
                        dataInTag = dataInTag.substring(dataInTag.indexOf(STOP_PREFIXTAG + tagname + supfix));
                    } else {
                        dataInTag = dataInTag.substring(dataInTag.indexOf(supfix) + supfix.length());
                    }

                    hList.add(node);

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hList;
    }

    public HtmlNodeLink getHTMLTagElement(HtmlNode nodelist,String tagname) {
        return AnalysisHTML(nodelist.getData(), tagname);
    }

    private String setupSupfix(String tagname) {
        if (tagname.equalsIgnoreCase("input")) {
            return SUPFIXTAG2;
        }
        return SUPFIXTAG1;
    }

    private List<Attibute> readAttbuteList(String headertag, String tagname, String supfixtag) {
        List<Attibute> AttList = new LinkedList<Attibute>();
        //split header
        String data = headertag.substring(headertag.indexOf(tagname) + tagname.length());
        //split tailer
        data = data.substring(0, data.indexOf(supfixtag));
        //replace " '
        data = data.replaceAll("\"", "");
        data = data.replaceAll("'", "");
        String[] pathAtt = data.split(" ");
        for (String att : pathAtt) {
            Attibute attibute = new Attibute();
            if (att.indexOf("=") != -1) {
                attibute.setName(att.split("=")[0]);
                attibute.setValue(att.split("=")[1]);
                AttList.add(attibute);
            }

        }

        return AttList;
    }

    private int checkPrefixTagIndex(String data, String tagname) {
        String temp = data;
        // System.out.println("temp : "+temp);
        // System.out.println("temp  index: "+temp.indexOf(tagname));
        int g = data.lastIndexOf(START_PREFIXTAG, temp.indexOf(tagname));
        //System.out.println("g : "+g);
        if (g == -1) {
            return -1;
        }

        if (temp.substring(g, temp.indexOf(tagname)).trim().equalsIgnoreCase(START_PREFIXTAG)) {

            return g;
        }
        return -1;
    }

    public List<String> ScanMethodFromFile(String pathFile) {
        List<String> data = g.readFile(pathFile);
        List<String> MethodList = new LinkedList<String>();
        for (int i = 0; i < data.size(); i++) {
            String temp = data.get(i);
            if (temp.trim().length() != 0) {
                String[] path = temp.trim().split(" ");
                if ((path[0].equalsIgnoreCase(ACCESS_MODIFIERS[0])) || (path[0].equalsIgnoreCase(ACCESS_MODIFIERS[1])) || (path[0].equalsIgnoreCase(ACCESS_MODIFIERS[2]))) {
                    for (int j = 0; j < path.length; j++) {
                        //check class
                        if (path[j].indexOf("class") != -1) {
                            break;
                        } else if (path[j].indexOf(";") != -1) { //check valiable
                            break;
                        } else {
                            if (path[j].indexOf("(") != -1) {
                                if (path[j].indexOf("(") != 0) {
                                    System.out.println("methodname :" + path[j].substring(0, path[j].indexOf("(")));
                                    MethodList.add(path[j].substring(0, path[j].indexOf("(")));
                                } else {
                                    System.out.println("methodname :" + path[j - 1]);
                                    MethodList.add(path[j - 1]);
                                }
                            }
                        }

                    }
                }
            }
        }
        return null;
    }

    public List<String> ScanValiableFromFile(String pathFile) {
        List<String> data = g.readFile(pathFile);
        for (int i = 0; i < data.size(); i++) {

        }
        return null;
    }

}
