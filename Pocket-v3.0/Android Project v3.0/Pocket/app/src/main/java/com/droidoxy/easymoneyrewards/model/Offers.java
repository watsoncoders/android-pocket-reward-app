package com.droidoxy.easymoneyrewards.model;

/**
 * Created by DroidOXY
 */
 
public class Offers {

    private String uniq_id,offerid,title,url,subtitle,amount, image, bg_image,partner,OriginalAmount,inst_title,inst1,inst2,inst3,inst4;
    private Boolean InappViewable;

    public Offers(String image, String title, String amount, String OriginalAmount, String url, String subtitle, String partner, String uniq_id, String offerid, String bg_image, String inst_title, String inst1, String inst2, String inst3, String inst4, Boolean InappViewable) {
        this.image = image;
        this.title = title;
        this.url = url;
        this.amount = amount;
        this.OriginalAmount = OriginalAmount;
        this.partner = partner;
        this.subtitle = subtitle;
        this.uniq_id = uniq_id;
        this.offerid = offerid;
        this.bg_image = bg_image;
        this.inst_title = inst_title;
        this.inst1 = inst1;
        this.inst2 = inst2;
        this.inst3 = inst3;
        this.inst4 = inst4;
        this.InappViewable = InappViewable;
    }

    public Boolean getInappViewable() {
        return InappViewable;
    }

    public void setInappViewable(Boolean InappViewable) {
        this.InappViewable = InappViewable;
    }

    public String getUniq_id() {
        return uniq_id;
    }

    public void setUniq_id(String uniq_id) {
        this.uniq_id = uniq_id;
    }

    public String getOfferid() {
        return offerid;
    }

    public void setOfferid(String offerid) {
        this.offerid = offerid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBg_image() {
        return bg_image;
    }

    public void setBg_image(String bg_image) {
        this.bg_image = bg_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOriginalAmount() {
        return OriginalAmount;
    }

    public void setOriginalAmount(String Originalamount) {
        this.OriginalAmount = Originalamount;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getInst_title() {
        return inst_title;
    }

    public void setInst_title(String inst_title) {
        this.inst_title = inst_title;
    }

    public String getInst1() {
        return inst1;
    }

    public void setInst1(String inst1) {
        this.inst1 = inst1;
    }

    public String getInst2() {
        return inst2;
    }

    public void setInst2(String inst2) {
        this.inst2 = inst2;
    }

    public String getInst3() {
        return inst3;
    }

    public void setInst3(String inst3) {
        this.inst3 = inst3;
    }

    public String getInst4() {
        return inst4;
    }

    public void setInst4(String inst4) {
        this.inst4 = inst4;
    }


}
