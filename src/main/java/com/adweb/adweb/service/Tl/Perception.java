package com.adweb.adweb.service.Tl;

public class Perception {
    private InputText inputText;
    public Perception(String inputText){
        this.inputText=new InputText(inputText);
    }

    public InputText getInputText() {
        return inputText;
    }
}
