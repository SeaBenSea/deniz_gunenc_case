package config;

public final class Urls {
    // Encoded strings to prevent indexing our repo on GitHub
    public static final String HOME = decode("aHR0cHM6Ly91c2VpbnNpZGVyLmNvbS8=");
    public static final String CAREERS_QA = decode("aHR0cHM6Ly91c2VpbnNpZGVyLmNvbS9jYXJlZXJzL3F1YWxpdHktYXNzdXJhbmNlLw==");
    public static final String OPEN_POS_QA = decode("aHR0cHM6Ly91c2VpbnNpZGVyLmNvbS9jYXJlZXJzL29wZW4tcG9zaXRpb25z");
    public static final String LEVER = "jobs.lever.co";

    private Urls() {
    }

    private static String decode(String b64) {
        return new String(java.util.Base64.getDecoder().decode(b64));
    }
}
