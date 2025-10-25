package service;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FipeService {
    
    private static final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/carros";
    private Map<String, String> marcasCache;
    private Map<String, Map<String, String>> modelosCache;

    public FipeService() {
        this.marcasCache = new HashMap<>();
        this.modelosCache = new HashMap<>();
        inicializarCache();
    }

    private void inicializarCache() {
        marcasCache.put("volkswagen", "59");
        marcasCache.put("fiat", "21");
        marcasCache.put("chevrolet", "23");
        marcasCache.put("ford", "26");
        marcasCache.put("toyota", "26");
        marcasCache.put("honda", "26");
        marcasCache.put("hyundai", "26");
        marcasCache.put("renault", "28");
        marcasCache.put("nissan", "43");
        
        Map<String, String> vwModelos = new HashMap<>();
        vwModelos.put("gol", "4914");
        vwModelos.put("polo", "4915");
        vwModelos.put("jetta", "4916");
        vwModelos.put("saveiro", "4917");
        modelosCache.put("59", vwModelos);
        
        Map<String, String> fiatModelos = new HashMap<>();
        fiatModelos.put("uno", "4920");
        fiatModelos.put("palio", "4921");
        fiatModelos.put("strada", "4922");
        fiatModelos.put("torino", "4923");
        modelosCache.put("21", fiatModelos);
        
        Map<String, String> chevroletModelos = new HashMap<>();
        chevroletModelos.put("onix", "4924");
        chevroletModelos.put("prisma", "4925");
        chevroletModelos.put("s10", "4926");
        chevroletModelos.put("tracker", "4927");
        modelosCache.put("23", chevroletModelos);
    }
    
    public JSONObject consultarValorVeiculo(String marca, String modelo, int ano) {
        try {
            String codigoMarca = buscarCodigoMarca(marca);
            if (codigoMarca == null) {
                return criarMockFipe(marca, modelo, ano);
            }
            
            String codigoModelo = buscarCodigoModelo(codigoMarca, modelo);
            if (codigoModelo == null) {
                return criarMockFipe(marca, modelo, ano);
            }
            
            String codigoAno = buscarCodigoAno(codigoMarca, codigoModelo, ano);
            if (codigoAno == null) {
                return criarMockFipe(marca, modelo, ano);
            }
            
            String urlString = String.format("%s/marcas/%s/modelos/%s/anos/%s", 
                BASE_URL, codigoMarca, codigoModelo, codigoAno);
            
            return fazerRequisicaoHTTP(urlString);
            
        } catch (Exception e) {
            System.out.println("Erro na consulta FIPE: " + e.getMessage());
            return criarMockFipe(marca, modelo, ano);
        }
    }
    
    private String buscarCodigoMarca(String marca) {
        String marcaLower = marca.toLowerCase();
        if (marcasCache.containsKey(marcaLower)) {
            return marcasCache.get(marcaLower);
        }
        
        try {
            String urlString = BASE_URL + "/marcas";
            JSONObject response = fazerRequisicaoHTTP(urlString);
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    private String buscarCodigoModelo(String codigoMarca, String modelo) {
        if (modelosCache.containsKey(codigoMarca)) {
            Map<String, String> modelos = modelosCache.get(codigoMarca);
            String modeloLower = modelo.toLowerCase();
            if (modelos.containsKey(modeloLower)) {
                return modelos.get(modeloLower);
            }
        }
        return null;
    }
    
    private String buscarCodigoAno(String codigoMarca, String codigoModelo, int ano) {
        return ano + "-1";
    }
    
    private JSONObject fazerRequisicaoHTTP(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String output;
            
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            
            conn.disconnect();
            return new JSONObject(response.toString());
            
        } catch (Exception e) {
            throw new RuntimeException("Erro na requisição HTTP: " + e.getMessage());
        }
    }
    
    private JSONObject criarMockFipe(String marca, String modelo, int ano) {
        double valorBase = calcularValorBase(marca, modelo, ano);
        
        JSONObject mockResponse = new JSONObject();
        mockResponse.put("Valor", "R$ " + String.format("%,.2f", valorBase));
        mockResponse.put("Marca", marca);
        mockResponse.put("Modelo", modelo);
        mockResponse.put("AnoModelo", ano);
        mockResponse.put("Combustivel", "Gasolina");
        mockResponse.put("CodigoFipe", "XXXXXXX");
        mockResponse.put("MesReferencia", "dezembro de 2024");
        mockResponse.put("TipoVeiculo", 1);
        mockResponse.put("SiglaCombustivel", "G");
        mockResponse.put("Observacao", "Valor estimado para consulta mock");
        
        return mockResponse;
    }
    
    private double calcularValorBase(String marca, String modelo, int ano) {
        double valorBase = 20000.0; 
        
        switch (marca.toLowerCase()) {
            case "volkswagen":
                valorBase += 5000;
                break;
            case "audi":
            case "bmw":
            case "mercedes-benz":
                valorBase += 30000;
                break;
            case "fiat":
                valorBase -= 2000;
                break;
            case "chevrolet":
            case "ford":
                valorBase += 1000;
                break;
        }
        
        int anosVelhos = 2024 - ano;
        if (anosVelhos > 0) {
            double depreciacao = 0.15 * anosVelhos; 
            valorBase *= (1 - Math.min(depreciacao, 0.7)); 
        }
        
        if (modelo.toLowerCase().contains("gol") || modelo.toLowerCase().contains("uno")) {
            valorBase -= 3000;
        } else if (modelo.toLowerCase().contains("civic") || modelo.toLowerCase().contains("corolla")) {
            valorBase += 8000;
        }
        
        return Math.max(valorBase, 5000); 
    }
    
    public void exibirConsultaFipe(String marca, String modelo, int ano) {
        System.out.println("\n=== CONSULTA FIPE ===");
        System.out.println("Veículo: " + marca + " " + modelo + " " + ano);
        
        JSONObject resultado = consultarValorVeiculo(marca, modelo, ano);
        
        System.out.println("Valor FIPE: " + resultado.getString("Valor"));
        System.out.println("Combustível: " + resultado.getString("Combustivel"));
        System.out.println("Mês de Referência: " + resultado.getString("MesReferencia"));
        System.out.println("Código FIPE: " + resultado.getString("CodigoFipe"));
        
        if (resultado.has("Observacao")) {
            System.out.println("Observação: " + resultado.getString("Observacao"));
        }
        
        System.out.println("=====================");
    }
    
    public void listarMarcasPopulares() {
        System.out.println("\n=== MARCAS POPULARES DISPONÍVEIS ===");
        System.out.println("• Volkswagen");
        System.out.println("• Fiat");
        System.out.println("• Chevrolet");
        System.out.println("• Ford");
        System.out.println("• Toyota");
        System.out.println("• Honda");
        System.out.println("• Hyundai");
        System.out.println("• Renault");
        System.out.println("• Nissan");
        System.out.println("================================");
    }
    
    public void listarModelosPorMarca(String marca) {
        String codigoMarca = buscarCodigoMarca(marca);
        if (codigoMarca != null && modelosCache.containsKey(codigoMarca)) {
            System.out.println("\n=== MODELOS " + marca.toUpperCase() + " ===");
            Map<String, String> modelos = modelosCache.get(codigoMarca);
            for (String modelo : modelos.keySet()) {
                System.out.println("• " + capitalize(modelo));
            }
            System.out.println("=========================");
        } else {
            System.out.println("Marca não encontrada ou sem modelos no cache.");
        }
    }
    
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}