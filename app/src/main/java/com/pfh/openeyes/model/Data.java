package com.pfh.openeyes.model;

import java.io.Serializable;
import java.util.List;

/**
 * 可能为null的字段：author、sharing、campaign、waterMarks、adTrack、shareAdTrack、favoriteAdTrack
 *                  webAdTrack、promotion、label
 * y一共有三种dataType
 */
public class Data implements Serializable{
    private static final long serialVersionUID = 1L;

    private String dataType;

    private int id;

    private String title;

    private String description;

    private Provider provider;

    private String category;

//    private String author;

    private Cover cover;

    private String playUrl;

    private int duration;

    private WebUrl webUrl;

    private long releaseTime;

    private List<PlayInfo> playInfo ;

    private Consumption consumption;

//    private String campaign;

//    private String waterMarks;

//    private String adTrack;

    private List<Tags> tags ;

    private String type;

    private int idx;

//    private String shareAdTrack;

//    private String favoriteAdTrack;

//    private String webAdTrack;

    private long date;

//    private String promotion;

//    private String label;

    //下面三个是banner里特有的
    private String image;

    private String actionUrl;

    private boolean shade;

    //TextHeader里的：

    private String text;// - Sep. 03 -

    private String font;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public boolean isShade() {
        return shade;
    }

    public void setShade(boolean shade) {
        this.shade = shade;
    }

    public void setDataType(String dataType){
        this.dataType = dataType;
    }
    public String getDataType(){
        return this.dataType;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setProvider(Provider provider){
        this.provider = provider;
    }
    public Provider getProvider(){
        return this.provider;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getCategory(){
        return this.category;
    }
//    public void setAuthor(String author){
//        this.author = author;
//    }
//    public String getAuthor(){
//        return this.author;
//    }
    public void setCover(Cover cover){
        this.cover = cover;
    }
    public Cover getCover(){
        return this.cover;
    }
    public void setPlayUrl(String playUrl){
        this.playUrl = playUrl;
    }
    public String getPlayUrl(){
        return this.playUrl;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    public int getDuration(){
        return this.duration;
    }
    public void setWebUrl(WebUrl webUrl){
        this.webUrl = webUrl;
    }
    public WebUrl getWebUrl(){
        return this.webUrl;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void setPlayInfo(List<PlayInfo> playInfo){
        this.playInfo = playInfo;
    }
    public List<PlayInfo> getPlayInfo(){
        return this.playInfo;
    }
    public void setConsumption(Consumption consumption){
        this.consumption = consumption;
    }
    public Consumption getConsumption(){
        return this.consumption;
    }
//    public void setCampaign(String campaign){
//        this.campaign = campaign;
//    }
//    public String getCampaign(){
//        return this.campaign;
//    }
//    public void setWaterMarks(String waterMarks){
//        this.waterMarks = waterMarks;
//    }
//    public String getWaterMarks(){
//        return this.waterMarks;
//    }
//    public void setAdTrack(String adTrack){
//        this.adTrack = adTrack;
//    }
//    public String getAdTrack(){
//        return this.adTrack;
//    }
    public void setTags(List<Tags> tags){
        this.tags = tags;
    }
    public List<Tags> getTags(){
        return this.tags;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setIdx(int idx){
        this.idx = idx;
    }
    public int getIdx(){
        return this.idx;
    }
//    public void setShareAdTrack(String shareAdTrack){
//        this.shareAdTrack = shareAdTrack;
//    }
//    public String getShareAdTrack(){
//        return this.shareAdTrack;
//    }
//    public void setFavoriteAdTrack(String favoriteAdTrack){
//        this.favoriteAdTrack = favoriteAdTrack;
//    }
//    public String getFavoriteAdTrack(){
//        return this.favoriteAdTrack;
//    }
//    public void setWebAdTrack(String webAdTrack){
//        this.webAdTrack = webAdTrack;
//    }
//    public String getWebAdTrack(){
//        return this.webAdTrack;
//    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

//    public void setPromotion(String promotion){
//        this.promotion = promotion;
//    }
//    public String getPromotion(){
//        return this.promotion;
//    }
//    public void setLabel(String label){
//        this.label = label;
//    }
//    public String getLabel(){
//        return this.label;
//    }

}
