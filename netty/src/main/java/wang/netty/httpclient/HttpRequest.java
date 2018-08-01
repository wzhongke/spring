package wang.netty.httpclient;


import org.apache.commons.lang.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wang.utils.MD5Utils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpRequest {

	private static Logger log = LoggerFactory.getLogger(HttpRequest.class);

	private static ExecutorService service = Executors.newFixedThreadPool(2);

	private static CloseableHttpClient httpClient;

	private final static String PFX_PATH = "client.p12";   //客户端证书路径
	private final static String PFX_PWD = "ssyqClient20170605"; //客户端证书密码
	private final static String CERT = "ca.crt";

	/**
	 * httpclient 线程池 长连接保持策略
	 */
	static ConnectionKeepAliveStrategy keepAliveStrategy = (httpResponse, httpContext) -> {
		HeaderElementIterator it = new BasicHeaderElementIterator
			(httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE));
		while (it.hasNext()) {
			HeaderElement he = it.nextElement();
			String param = he.getName();
			String value = he.getValue();
			if (value != null && param.equalsIgnoreCase
				("timeout")) {
				return Long.parseLong(value) * 1000;
			}
		}
		//如果没有约定，则默认定义时长为60s
		return 60 * 1000;
	};

	public static void threadPool () {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		// 最大路由数
		connectionManager.setMaxTotal(5);
		//例如默认每路由最高50并发，具体依据业务来定
		connectionManager.setDefaultMaxPerRoute(50);
		CloseableHttpClient httpClient = HttpClients.custom()
			.setConnectionManager(connectionManager)
			.setKeepAliveStrategy(keepAliveStrategy)
			.setDefaultRequestConfig(RequestConfig.custom().build())
			.build();

		ExecutorService threadPool = Executors.newFixedThreadPool(20);
		for (int i=0; i< 10; i++) {
			threadPool.submit(()->{
				for (int j=0; j< 10; j++) {
					HttpPost httpPost = new HttpPost("http://www.baidu.com");
					StringEntity entity = new StringEntity("{\"flag\":0,\"from\":\"wap\",\"ip\":\"\",\"ipLoc\":\"\"}", Charset.forName("UTF8"));
					httpPost.setEntity(entity);
					httpPost.setHeader("Content-Type", "application/json");
					try (CloseableHttpResponse response = httpClient.execute(httpPost)){
						HttpEntity httpEntity = response.getEntity();
						if (httpEntity != null) {
							System.out.println(EntityUtils.toString(httpEntity, "UTF-8"));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static String get(String requestUrl) throws Exception {
		return get(requestUrl, true);
	}

	public static void getAsy (String requestUrl, boolean isHttps) {
		service.submit(() -> {
			try {
				log.info("SYNC status: " + get(requestUrl, isHttps));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public static String get(String requestUrl, boolean isHttps) throws Exception {
		CloseableHttpClient httpClient;
		if (isHttps) {
			httpClient = createSSLClientDefault();
		} else {
			httpClient = HttpClients.createDefault();
		}
		HttpGet request = new HttpGet(requestUrl);
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		try (CloseableHttpResponse response =  httpClient.execute(request)) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String responseBody = EntityUtils.toString(entity);
				log.info(responseBody);
				return responseBody;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getFile (String fileUrl) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(fileUrl);
		try (CloseableHttpResponse response =  httpClient.execute(request)) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				byte[] content = EntityUtils.toByteArray(entity);
				return MD5Utils.getByteMd5(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getWithSSL(String requestUrl)  throws Exception{

		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(HttpRequest.class.getClassLoader().getResourceAsStream(PFX_PATH), PFX_PWD.toCharArray());

		System.out.println(KeyStore.getDefaultType());
		System.out.println(TrustManagerFactory.getDefaultAlgorithm());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(ks);

		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(null, tmf.getTrustManagers(), null);

		LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);
		CloseableHttpClient httpclient = HttpClients.custom()
			.setSSLSocketFactory(sslSocketFactory)
			.build();
		try {
			HttpGet httpget = new HttpGet(requestUrl);
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");//返回结果
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	public static String post(String postUrl, String params, boolean isHttps) throws Exception {
		CloseableHttpClient httpClient ;
		RequestConfig defaultRequestConfig = RequestConfig.custom()
			.setSocketTimeout(5000)
			.setConnectTimeout(5000)
			.setConnectionRequestTimeout(5000)
			.build();
		if (isHttps) {
			httpClient = createSSLClientDefault();
		} else {
			httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
		}
		HttpPost httpPost = new HttpPost(postUrl);
		httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
		httpPost.setEntity(new StringEntity(params, "utf-8"));
		try ( CloseableHttpResponse response = httpClient.execute(httpPost)) {
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				return EntityUtils.toString(httpEntity, "UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String post(String postUrl, String params) throws Exception {
		CloseableHttpClient httpClient  = createSSLClientWithoutVer();

		HttpPost httpPost = new HttpPost(postUrl);
		httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
		httpPost.setEntity(new StringEntity(params, "utf-8"));
		try ( CloseableHttpResponse response = httpClient.execute(httpPost)) {
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				return EntityUtils.toString(httpEntity, "UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param fileInfo <md5, filePath>
	 * @return <md5, url>
	 */

	public static Map<String, String> postYutu(Map<String, String> fileInfo) {
		Map<String, String> resultMap = new HashMap<>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault()){
			// 把一个普通参数和文件上传给下面这个地址 是一个servlet
			HttpPost httpPost = new HttpPost("http://innerupload.sogou/http_upload?appid=200746");
			// 把文件转换成流对象FileBody

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			int i = 1;
			for (Map.Entry<String, String> entry : fileInfo.entrySet()) {
				File file = new File(entry.getValue());
				if (file.exists()) {
					System.out.println(file.getName() + "," + i);
					builder.addPart("file" + i, new FileBody(file));
					builder.addPart("sign_file" + i,  new StringBody(entry.getKey(), ContentType.TEXT_PLAIN));
					i++;
				}
			}
			httpPost.setEntity(builder.build());
			// 发起请求 并返回请求的响应
			CloseableHttpResponse response = httpClient.execute(httpPost);

			System.out.println("The response value of token:" + response.getFirstHeader("token"));

			// 获取响应对象
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				// 打印响应内容
				String res = EntityUtils.toString(resEntity, "utf8");
				System.out.println(res);
				JSONArray jsonArr = new JSONArray(res);
				int length = jsonArr.length();
				for (int p = 0; p < length; p++){
					JSONObject obj = (JSONObject) jsonArr.get(p);
					String name = obj.optString("name"), url = obj.optString("url");
					if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(url)) {
						resultMap.put(name, url);
					}
				}
			}
			response.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return resultMap;
	}


	public static Map<String, String> postYutuFile(Map<String, File> fileInfo) {
		Map<String, String> resultMap = new HashMap<>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault()){
			// 把一个普通参数和文件上传给下面这个地址 是一个servlet
			HttpPost httpPost = new HttpPost("http://innerupload.sogou/http_upload?appid=200746");
			// 把文件转换成流对象FileBody

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			int i = 1;
			for (Map.Entry<String, File> entry : fileInfo.entrySet()) {
				File file = entry.getValue();
				if (file.exists()) {
					System.out.println(file.getName() + "," + i);
					builder.addPart("file" + i, new FileBody(file));
					builder.addPart("sign_file" + i,  new StringBody(entry.getKey(), ContentType.TEXT_PLAIN));
					i++;
				}
			}
			httpPost.setEntity(builder.build());
			// 发起请求 并返回请求的响应
			CloseableHttpResponse response = httpClient.execute(httpPost);

			System.out.println("The response value of token:" + response.getFirstHeader("token"));

			// 获取响应对象
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				// 打印响应内容
				String res = EntityUtils.toString(resEntity, "utf8");
				System.out.println(res);
				JSONArray jsonArr = new JSONArray(res);
				int length = jsonArr.length();
				for (int p = 0; p < length; p++){
					JSONObject obj = (JSONObject) jsonArr.get(p);
					String name = obj.optString("name"), url = obj.optString("url");
					if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(url)) {
						resultMap.put(name, url);
					}
				}
			}
			response.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return resultMap;
	}

	private static CloseableHttpClient createSSLClientWithoutVer() {
		SSLContext sslContext;
		try {
			// 创建一个信任所有的https链接，不验证证书
			sslContext = new SSLContextBuilder()
				.loadTrustMaterial(null, (certificate, authType) -> true).build();
			return HttpClients.custom()
				.setSSLContext(sslContext)
				.setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		} catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.custom().build();
	}

	private static CloseableHttpClient createSSLClientDefault() throws Exception {
		if (httpClient == null) {
			SSLContext ctx = getSSLContext(PFX_PWD, PFX_PATH, CERT);
			LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);
			httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslSocketFactory)
				.build();
		}
		return httpClient;
	}

	private static SSLContext getSSLContext(String password, String keyStorePath, String trustStorePath) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, IOException, CertificateException, KeyManagementException {
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		InputStream in = HttpRequest.class.getClassLoader().getResourceAsStream(keyStorePath);
		keyStore.load(in, PFX_PWD.toCharArray());
		in.close();
		keyManagerFactory.init(keyStore, password.toCharArray());
		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(keyManagerFactory.getKeyManagers(), getTrustManagers(trustStorePath) , null);
		return ctx;
	}

	private static TrustManager[] getTrustManagers (String ... crtPath) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
		if (crtPath == null || crtPath.length < 1) {
			return null;
		}
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(null);
		for (int i=0, j=crtPath.length; i<j; i++) {
			String path = crtPath[i];
			InputStream is = HttpRequest.class.getClassLoader().getResourceAsStream(path);
			System.out.println(path);
			keyStore.setCertificateEntry(Integer.toString(i), certificateFactory.generateCertificate(is));
			is.close();
		}
		TrustManagerFactory trustManagerFactory =TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(keyStore);
		return trustManagerFactory.getTrustManagers();
	}


}
