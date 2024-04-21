package com.example.l3.dto;

public class Peliculas {

    private String Title;
    private String Director;
    private String Actors;
    private String Released;
    private String Genre;
    private String Writers;
    private String Plot;
    private Ratings Ratings;

    public String getTitle(){
        return Title;
    }public String getDirector(){
        return Director;
    }public String getActors(){
        return Actors;
    }public String getReleased(){
        return Released;
    }public String getGenre(){
        return Genre;
    }public String getWriters(){
        return Writers;
    }public String getPlot(){
        return Plot;
    }public Ratings getRatings(){
        return Ratings;
    }

    public void setTitle(String Title){
        this.Title=Title;
    }public void setDirector(String Director){
        this.Director=Director;
    }public void setActors(String Actors){
        this.Actors=Actors;
    }public void setReleased(String Released){
        this.Released=Released;
    }public void setGenre(String Genre){
        this.Genre=Genre;
    }public void setWriters(String Writers){
        this.Writers=Writers;
    }public void setPlot(String Plot){
        this.Plot=Plot;
    }public void setRatings(Ratings Ratings){
        this.Ratings=Ratings;
    }

}
