/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Hachiko
 */
public class Picture implements Content{
    
    private String name;
    private String pictureType;
    private String base64Content;

    public Picture() {
    }

    public Picture(String name, String pictureType, String base64Content) {
        this.name = name;
        this.pictureType = pictureType;
        this.base64Content = base64Content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    @Override
    public String toString() {
        return base64Content;
    }
    
    
}
