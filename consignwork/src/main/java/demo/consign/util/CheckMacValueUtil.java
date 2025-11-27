package demo.consign.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

public class CheckMacValueUtil {

    public static String generate(Map<String, String> params, String hashKey, String hashIv) {
        try {
            Map<String, String> sorted = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            for (Map.Entry<String, String> e : params.entrySet()) {
                String key = e.getKey();
                String value = e.getValue();
                if ("CheckMacValue".equalsIgnoreCase(key)) continue;
                if (value == null || value.isEmpty()) continue;
                sorted.put(key, value);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("HashKey=").append(hashKey);
            for (Map.Entry<String, String> e : sorted.entrySet()) {
                sb.append("&").append(e.getKey()).append("=").append(e.getValue());
            }
            sb.append("&HashIV=").append(hashIv);

            String raw = sb.toString();

            String urlEncoded = URLEncoder.encode(raw, StandardCharsets.UTF_8.name())
                    .toLowerCase();

            urlEncoded = urlEncoded
                    .replace("%21", "!")
                    .replace("%28", "(")
                    .replace("%29", ")")
                    .replace("%2a", "*")
                    .replace("%2d", "-")
                    .replace("%2e", ".")
                    .replace("%5f", "_");

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(urlEncoded.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : digest) {
                hex.append(String.format("%02X", b));
            }
            return hex.toString();
        } catch (Exception ex) {
            throw new RuntimeException("產生 CheckMacValue 失敗", ex);
        }
    }

    public static boolean verify(Map<String, String> params, String hashKey, String hashIv) {
        String checkMac = params.get("CheckMacValue");
        if (checkMac == null) return false;
        String generated = generate(params, hashKey, hashIv);
        return generated.equalsIgnoreCase(checkMac);
    }
}
