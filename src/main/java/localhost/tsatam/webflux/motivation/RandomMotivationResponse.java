package localhost.tsatam.webflux.motivation;

import java.util.Objects;

public class RandomMotivationResponse {
    private String url;

    public RandomMotivationResponse() {}

    public RandomMotivationResponse(String url) {this.url = url;}

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomMotivationResponse that = (RandomMotivationResponse) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() { return Objects.hash(url); }
}
