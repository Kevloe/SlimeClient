package me.slime.user.group;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kevloe on 10.04.2020.
 */

public class Group {

  private int id;

  private String name;

  @SerializedName("nice_name")
  private String display_name;
  @SerializedName("color_hex")
  private String colorHex;
  @SerializedName("color_minecraft")
  private String colorMinecraft;
  @SerializedName("tag_name")
  private String tagName;

  public Group(int id, String name, String display_name, String colorHex, String colorMinecraft, String tagName){
    this.id = id;
    this.name = name;
    this.display_name = display_name;
    this.colorHex = colorHex;
    this.colorMinecraft = colorMinecraft;
    this.tagName = tagName;
  }

  public int getID() {
    return id;
  }

  public String getColorHex() {
    return "#"+colorHex;
  }

  public String getColorMinecraft() {
    return colorMinecraft;
  }

  public String getDisplayName() {
    return display_name;
  }

  public String getTagName() {
    return tagName;
  }

  public String getDisplayTag(){
    return "§a§lSlimeClient §8x §"+this.colorMinecraft+this.tagName;
  }
}
