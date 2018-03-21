package com._51job.tool;

import com._51job.web.SearchResults;

import java.util.List;

public class KeywordFilter implements Runnable {
    private String keyword;

    public void setSources(List<SearchResults> sources) {
        this.sources = sources;
    }

    private List<SearchResults> sources;
    private List<SearchResults> results;
    private boolean finished=false;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setResults(List<SearchResults> results) {
        this.results = results;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    KeywordFilter() {

    }

    @Override
    public void run() {
        for (SearchResults searchResults: sources) {
            if (searchResults.getRecruitment().getPost().toUpperCase().contains(keyword.toUpperCase()) || searchResults.getEnterprise().getName().toUpperCase().contains(keyword.toUpperCase()))
                if(searchResults.getRecruitment().getState()!=(byte)2)results.add(searchResults);
        }
        finished=true;
    }

    public boolean isFinished() {
        return finished;
    }
}
