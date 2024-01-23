package com.codegym.spb_eyesclinic_project.domain.socket;

import com.codegym.spb_eyesclinic_project.domain.Booking;

import java.util.List;

public class ListContent {
    private List<Booking> contents;

    public ListContent(List<Booking> contents) {
        this.contents = contents;
    }

    public ListContent() {
    }

    public List<Booking> getContents() {
        return contents;
    }

    public void setContents(List<Booking> contents) {
        this.contents = contents;
    }
}
