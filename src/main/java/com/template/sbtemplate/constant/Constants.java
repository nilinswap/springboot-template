package com.template.sbtemplate.constant;

public class Constants {
    public static final String API_V1 = "/v1";
    public static final String REVIEW_GET = "/reviews/get";
    public static final String REVIEW_ADD = "/reviews/add";
    public static final String RATING_GET = "/ratings/get";

    public static final String EVENT_GET_RATINGS = "/ratings/get";
    public static final String TEMPLATE_DB_NAME = "sbtemplate";
    public static final String PAGE_SIZE_DEFAULT = "10";
    public class Database{
        public static final String REVIEW_TABLE = "review";
    }

    public class SqlQueries{
        public static final String COUNT_AVERAGE_RATING = "SELECT COUNT(*), AVG(rating) FROM review;";
    }
}


