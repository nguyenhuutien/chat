/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl;

/**
 *
 * @author Tien
 */
public class setIcon {

    String icon;
    

    public String getIcon(String ch) {
             if(ch.indexOf(":))")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//21.gif").toString();
        ch = ch.replace(":))", "<img src=" + icon + "/>");
          }
        if(ch.indexOf(":)")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//1.gif").toString();
        ch = ch.replace(":)", "<img src=" + icon + "/>");
        }
          if(ch.indexOf(":(")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//2.gif").toString();
        ch = ch.replace(":(", "<img src=" + icon + "/>");
          }
                 if(ch.indexOf(";)")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//3.gif").toString();
        ch = ch.replace(";)", "<img src=" + icon + "/>");
          }
          if(ch.indexOf(":d")!=0 || ch.indexOf(":D")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//4.gif").toString();
        ch = ch.replace(":d", "<img src=" + icon + "/>");
        ch = ch.replace(":D", "<img src=" + icon + "/>");
          } 
             if(ch.indexOf(";;)")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//5.gif").toString();
        ch = ch.replace(";;)", "<img src=" + icon + "/>");
          }
             if(ch.indexOf(">:D<")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//6.gif").toString();
        ch = ch.replace(">:D<", "<img src=" + icon + "/>");
          }
             if(ch.indexOf(":-/")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//7.gif").toString();
        ch = ch.replace(":-/", "<img src=" + icon + "/>");
          }
             if(ch.indexOf(":x")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//8.gif").toString();
        ch = ch.replace(":x", "<img src=" + icon + "/>");
          }
             if(ch.indexOf(":\">")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//9.gif").toString();
        ch = ch.replace(":\">", "<img src=" + icon + "/>");
          }
             if(ch.indexOf(":P")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//10.gif").toString();
        ch = ch.replace(":P", "<img src=" + icon + "/>");
          }
 
          if(ch.indexOf(":-*")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//11.gif").toString();
        ch = ch.replace(":-*", "<img src=" + icon + "/>");
          }
          if(ch.indexOf("=((")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//12.gif").toString();
        ch = ch.replace("=((", "<img src=" + icon + "/>");
          }
          if(ch.indexOf(":-O")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//13.gif").toString();
        ch = ch.replace(":-O", "<img src=" + icon + "/>");
          }
          if(ch.indexOf("X(")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//14.gif").toString();
        ch = ch.replace("X(", "<img src=" + icon + "/>");
          }
          if(ch.indexOf(":>")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//15.gif").toString();
        ch = ch.replace(":>", "<img src=" + icon + "/>");
          }
          if(ch.indexOf("B-)")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//16.gif").toString();
        ch = ch.replace("B-)", "<img src=" + icon + "/>");
          }
          if(ch.indexOf(":-S")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//17.gif").toString();
        ch = ch.replace(":-S", "<img src=" + icon + "/>");
          }
          if(ch.indexOf("#:-S")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//18.gif").toString();
        ch = ch.replace("#:-S", "<img src=" + icon + "/>");
          }
          if(ch.indexOf(">:)")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//19.gif").toString();
        ch = ch.replace(">:)", "<img src=" + icon + "/>");
          }
          if(ch.indexOf(":((")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//20.gif").toString();
        ch = ch.replace(":((", "<img src=" + icon + "/>");
          }
     
          if(ch.indexOf(":|")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//22.gif").toString();
        ch = ch.replace(":|", "<img src=" + icon + "/>");
          }
          if(ch.indexOf("/:)")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//23.gif").toString();
        ch = ch.replace("/:)", "<img src=" + icon + "/>");
          }
          if(ch.indexOf("=))")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//24.gif").toString();
        ch = ch.replace("=))", "<img src=" + icon + "/>");
          }
          if(ch.indexOf("O:-)")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//25.gif").toString();
        ch = ch.replace("O:-)", "<img src=" + icon + "/>");
          }
          if(ch.indexOf(":-B")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//26.gif").toString();
        ch = ch.replace(":-B", "<img src=" + icon + "/>");
          }
          if(ch.indexOf("=;")!=0){
        icon = this.getClass().getClassLoader().getResource("yahoo//27.gif").toString();
        ch = ch.replace("=;", "<img src=" + icon + "/>");
          }
         
   
       
        return ch;
    }
}
