package com.huseyn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static List<Book>books;
    private static List<Member> members;
    private static List<Loan> loans;
    private static final Type booksListType = new TypeToken<List<Book>>(){}.getType();
    private static final Type membersListType = new TypeToken<List<Member>>(){}.getType();
    private static final Type loansListType = new TypeToken<List<Loan>>(){}.getType();

    FileManager(){
        books=new ArrayList<>();
        members= new ArrayList<>();
        loans=new ArrayList<>();
    }
    public void save(){
        saveBooks();
        saveMembers();
        saveLoans();
    }
    private void saveBooks() {
        books= new ArrayList<>(db.getBooks());
        try(FileWriter fr= new FileWriter("books.json")){
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(books,booksListType,fr);
        }catch (  IOException e){
            throw new RuntimeException(e);
        }
    }
    private void saveMembers() {
        members= new ArrayList<>(db.getMembers());
        try(FileWriter fr= new FileWriter("members.json")){
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(members,membersListType,fr);
        }catch (  IOException e){
            throw new RuntimeException(e);
        }
    }
    private void saveLoans() {
        loans= new ArrayList<>(db.getLoans());
        try(FileWriter fr= new FileWriter("loans.json")){
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(loans,loansListType,fr);
        }catch (  IOException e){
            throw new RuntimeException(e);
        }
    }

}
