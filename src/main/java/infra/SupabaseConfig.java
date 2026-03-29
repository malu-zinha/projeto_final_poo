package infra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

// Carrega credenciais do Supabase a partir do arquivo .env
public class SupabaseConfig {
    private static final Properties props = new Properties();

    static {
        try (BufferedReader br = new BufferedReader(new FileReader(".env"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty() || linha.startsWith("#")) continue;
                int sep = linha.indexOf('=');
                if (sep > 0) {
                    props.setProperty(
                        linha.substring(0, sep).trim(),
                        linha.substring(sep + 1).trim()
                    );
                }
            }
        } catch (IOException e) {
            // Sem .env = modo offline (dados ficam só em memória)
        }
    }

    public static String getUrl() {
        return props.getProperty("SUPABASE_URL", "");
    }

    public static String getAnonKey() {
        return props.getProperty("SUPABASE_ANON_KEY", "");
    }

    public static boolean isConfigurado() {
        return !getUrl().isEmpty() && !getAnonKey().isEmpty();
    }
}
