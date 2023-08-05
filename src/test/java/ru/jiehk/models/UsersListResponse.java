package ru.jiehk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UsersListResponse {
    private int page, total;
    @JsonProperty("per_page")
    private int perPage;
    @JsonProperty("total_pages")
    private int totalPages;

    private ArrayList<UsersListData> data = new ArrayList<>();
    private UsersListSupport support = new UsersListSupport();
}
@Data
class UsersListData {
    private int id;
    private String email, avatar;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

}

@Data
class UsersListSupport {
    private String url, text;
}
