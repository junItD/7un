package top.i7un.springboot.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;

/**
 * @ClassName: HttpClientUtils
 * @Description: httpclient工具
 * @author gaoyuxin
 * @date 2018年4月26日 下午4:50:46
 */
@Slf4j
public class HttpClientUtils {
	
	private static final int HTTP_PROTOCOL_LENGTH = "http://".length();
	private static final String DEFAULT_CHARSET_UTF8 = "UTF-8";
	private static final String DEFAULT_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36";
	private static final int DETAULT_TRY_COUNT = 1;
	private static final String DEFAULT_SCHEMA = "http://";
	private static final String absFile = "";
	private static final String DOWNLOAD_FILE_FUFFIX = ".download";
	
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		@Override
        public String handleResponse(HttpResponse response)	throws ClientProtocolException, IOException {
			int statusCode = response.getStatusLine().getStatusCode();
			/* HttpClient 支持get请求的自动重定向 */
			if(statusCode == 301 || statusCode == 302){
				String localtionUrl = response.getFirstHeader("Location").getValue();
				if(localtionUrl == null || !localtionUrl.startsWith("http")){
					throw new RuntimeException("错误的Location地址："+String.valueOf(localtionUrl));
				}
				return go(localtionUrl, false);
			}
			if(statusCode >= 400){
				throw new RuntimeException("访问出错，HTTP响应状态码"+statusCode);
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity) == null ? DEFAULT_CHARSET_UTF8 : EntityUtils.getContentCharSet(entity);
				return new String(EntityUtils.toByteArray(entity), charset);
			} else {
				return null;
			}
		}
	};
	
	/**
	 * 对指定参数发出请求
	 * HttpClientUtils.go()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-12 </P>
	 * @param url url
	 * @param isAsync isAsync
	 * @return 返回页面源码
	 */
	public static String go(String url, boolean isAsync){
		return go(url, false, null, null, DEFAULT_CHARSET_UTF8, isAsync, DETAULT_TRY_COUNT, absFile);
	}
	
	/**
	 * 对指定参数发出请求
	 * HttpClientUtils.go()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-12 </P>
	 * @param url url
	 * @param isPost isPost
	 * @param params params
	 * @return str or absFile
	 */
	public static String go(String url, boolean isPost, Map<String, String> params){
		return go(url, isPost, params, null, DEFAULT_CHARSET_UTF8, false, DETAULT_TRY_COUNT, absFile);
	}
	
	/**
	 * 对指定参数发出请求
	 * HttpClientUtils.go()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-11 </P>
	 * @param url url
	 * @param isPost isPost
	 * @param params params
	 * @param headerMap headerMap
	 * @return str or absFile
	 */
	public static String go(String url, boolean isPost, Map<String, String> params, Map<String, String> headerMap){
		return go(url, isPost, params, headerMap, DEFAULT_CHARSET_UTF8, false, DETAULT_TRY_COUNT, absFile);
	}

	/**
	 * 对指定参数发出请求
	 * HttpClientUtils.go()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-11 </P>
	 * @param url url
	 * @param isPost isPost
	 * @param params params
	 * @param headerMap headerMap
	 * @param charset charset
	 * @param isAsync isAsync
	 * @param tryCount tryCount
	 * @param absFile 下载的文件
	 * @return str or absFile
	 */
	public static String go(String url, final boolean isPost, final Map<String, String> params, final Map<String, String> headerMap, final String charset, boolean isAsync, final int tryCount,final String absFile){
		if(url == null || url.trim().length() == 0){
			throw new RuntimeException("URL为空，请检查");
		}
		if(!url.matches("^http[s]?://.*?")){
			url = DEFAULT_SCHEMA + url;
		}
		String responseStr;
		if(isAsync){
			final ExecutorService excutors = Executors.newSingleThreadExecutor();
			final String httpUrl = url;
			excutors.execute(new Runnable() {
				@Override
				public void run() {
					executor(httpUrl, isPost, params, headerMap, charset, tryCount, absFile);
					excutors.shutdown();
				}
			});
			return null;
		} else {
			responseStr  = executor(url, isPost, params, headerMap, charset, tryCount, absFile);
		}
		
		return responseStr;
	}
	
		
	/**
	 * @Title: postWithJsonParam 
	 * @Description: 发送post参数的请求
	 * @author gaoyuxin
	 * @date 2018年4月27日 下午4:04:10
	 */
    public static String[] postWithJsonParam(String url, String param){
		
    	PrintWriter out = null;
		BufferedReader in = null;
		String result[] = { "" };
		
		try {
			
			if (url == null || url.trim().length() == 0) {
				throw new RuntimeException("URL为空，请检查");
			}
			if (!url.matches("^http[s]?://.*?")) {
				url = DEFAULT_SCHEMA + url;
			}

			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			if(StringUtils.isNotBlank(param)) {
				out.print(param);
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			int i = 0;
			while ((line = in.readLine()) != null) {
				result[i] += line;
				i++;
			}
		} catch (Exception e) {
			log.warn("调用postWithJsonParam发送Post请求失败，错误信息：" + e.getMessage() + "url=" + url + "param=" + param, e);
			throw new RuntimeException("发送Post请求失败！" + e.getMessage());
		}finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.warn("调用postWithJsonParam关流错误：" , ex);
			}
		}
		return result;
    }
	
	/**
	 * http请求
	 * HttpClientUtils.executor()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-11 </P>
	 * @param url url
	 * @param isPost ispost
	 * @param params params
	 * @param headerMap headerMap
	 * @param charset charset
	 * @param tryCount tryCount
	 * @return str or filePath
	 */
	private static String executor(String url, boolean isPost, Map<String, String> params, Map<String, String> headerMap, String charset, final int tryCount, final String absFile){
		DefaultHttpClient httpClient = getDefaultHttpClient(headerMap, charset, tryCount);
		httpClient = setHttpsConditon(url, httpClient);
		List<NameValuePair> nameValuePairs = getNameValuePairs(params);
		Header[] headers = getHeaders(headerMap);
		String responseStr;
		if(isPost){
			responseStr = post(url, nameValuePairs, httpClient, charset, headers, absFile);
		} else {
			responseStr = get(url, nameValuePairs, httpClient, charset, headers, absFile);
		}
		return responseStr;
	}
	
	/**
	 * 发送post请求
	 * HttpClientUtils.post()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-11 </P>
	 * @param url url
	 * @param nameValuePairs nameValuePairs
	 * @param httpClient httpClient
	 * @param charset charset
	 * @param headers headers
	 * @return 文本或路径
	 */
	private static String post(String url, List<NameValuePair> nameValuePairs, DefaultHttpClient httpClient, String charset, Header[] headers,final String absFile){
		HttpEntity httpEntity = null;
		if(nameValuePairs != null && nameValuePairs.size() == 1 && nameValuePairs.get(0).getName().trim().length() == 0){
			try {
				if (charset == null || charset.trim().length() == 0) {
					httpEntity = new StringEntity(nameValuePairs.get(0).getValue());
				} else {
					httpEntity = new StringEntity(nameValuePairs.get(0).getValue(), charset);
				}
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("不支持的编码集", e);
			}
		} else if(nameValuePairs != null && nameValuePairs.size() >= 1){
			try {
				if (charset == null || charset.trim().length() == 0) {
					httpEntity = new UrlEncodedFormEntity(nameValuePairs);
				} else {
					httpEntity = new UrlEncodedFormEntity(nameValuePairs, charset);
				}
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("不支持的编码集", e);
			}
		}
		HttpPost hp = new HttpPost(url); 
		hp.setHeaders(headers);
		hp.setParams(httpClient.getParams());
		hp.setEntity(httpEntity);

		String responseStr = null;
		try {

			if( "".equals(absFile) ) {
				responseStr = httpClient.execute(hp, responseHandler);
			} else {
				String tempAbsFile = absFile + DOWNLOAD_FILE_FUFFIX;
				FileOutputStream fileOut = new FileOutputStream(tempAbsFile);
				httpClient.execute(hp).getEntity().writeTo(fileOut);
	            fileOut.flush();
	            fileOut.close();
	            fileRename(tempAbsFile, absFile);
	            return absFile;
			}
		} catch (ClientProtocolException e) {
			log.error("发送post请求失败！" + e.getMessage()  + " 参数：" + JSONArray.toJSON(nameValuePairs));
			throw new RuntimeException("访问"+url+"客户端连接协议错误", e);
		} catch (IOException e) {
			log.error("发送post请求失败！" + e.getMessage()  + " 参数：" + JSONArray.toJSON(nameValuePairs));
			throw new RuntimeException("访问"+url+" IO操作异常", e);
		} catch (Exception e) {
			log.error("发送post请求失败！" + e.getMessage() + " 参数：" + JSONArray.toJSON(nameValuePairs));
			throw new RuntimeException(url, e);
		} finally {
			abortConnection(hp, httpClient);
		}		
		return responseStr;
	}
	
	/**
	 * 获取uri键值对
	 * HttpClientUtils.getURI()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-6-22 </P>
	 * @param url url
	 * @return map
	 */
	public static Map<String, String> getURI(String url){
		int index = url.indexOf("?");
		if(index < 0 || index + 1 == url.length()){
			return Collections.emptyMap();
		}
		Map<String, String> params = new HashMap<String, String>();
		String uri = url.substring(index + 1);
		String[] queries = uri.split("[&]+", -1);
		for(String query : queries){
			String[] queryArr = query.split("=", -1);
			if(queryArr.length == 1){
				params.put("", queryArr[0]);
				continue;
			}
			params.put(queryArr[0], queryArr[1]);
		}
		return params;
	}

	/**
	 * 发送get请求
	 * HttpClientUtils.get()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-11 </P>
	 * @param url url
	 * @param params params
	 * @param httpClient httpClient
	 * @param charset charset
	 * @param headers header
	 * @return str or absFile
	 */
	private static String get(String url, List<NameValuePair> params, DefaultHttpClient httpClient, String charset, Header[] headers,final String absFile){
		if (params == null) {
			params = new ArrayList<NameValuePair>();
		}
		params.addAll(getNameValuePairs(getURI(url)));
		charset = (charset == null ? DEFAULT_CHARSET_UTF8 : charset);
		String formatParams = URLEncodedUtils.format(params, charset);
		url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url.subSequence(0, url.indexOf("?") + 1) + formatParams);
		HttpGet hg = new HttpGet(url);
		hg.setHeaders(headers);
		String responseStr = null;
		FileOutputStream fileOut;
		try {
			if( "".equals(absFile) ) {
				responseStr = httpClient.execute(hg, responseHandler);
			} else {
				String tempAbsFile = absFile + DOWNLOAD_FILE_FUFFIX;
				fileOut = new FileOutputStream(tempAbsFile);
				httpClient.execute(hg).getEntity().writeTo(fileOut);
	            fileOut.flush();
	            fileOut.close();
	            fileRename(tempAbsFile, absFile);
	            return absFile;
			}
		} catch (Exception e) {
			throw new RuntimeException("访问"+url+"异常", e);
		} finally {
			abortConnection(hg, httpClient);
		}
		return responseStr;
	}
	
	private static String extractSiteUrl(String url) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		
		int siteEndIndex = url.indexOf('/', HTTP_PROTOCOL_LENGTH);
		if(siteEndIndex != -1) {
			return url.substring(HTTP_PROTOCOL_LENGTH, siteEndIndex);
		}
		return null;
	}
	
	/**
	 * 获取一个httpclient对象
	 * HttpClientUtils.getDefaultHttpClient()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-10 </P>
	 * @param headers header
	 * @param charset charset
	 * @param tryCount tryCount
	 * @return DefaultHttpClient
	 */
	private static DefaultHttpClient getDefaultHttpClient(Map<String, String> headers, String charset, final int tryCount){
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,600000);
		httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,600000);
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 600000);
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 600000);
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, (headers != null && headers.containsKey("User-Agent")) ? headers.get("User-Agent") : DEFAULT_USER_AGENT_STRING);
		httpclient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, charset == null ? DEFAULT_CHARSET_UTF8 : charset);
		httpclient.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= tryCount) {
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					return false;
				}
				HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
				return !(request instanceof HttpEntityEnclosingRequest);
			}
		});
		return httpclient;
	}
	
	/**
	 * 获取请求参数
	 * HttpClientUtils.getNameValuePairs()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-11 </P>
	 * @param params params
	 * @return list
	 */
	private static List<NameValuePair> getNameValuePairs(Map<String, String> params) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (params == null || params.size() == 0) {
			return nameValuePairs;
		}
		for (Map.Entry<String, String> map : params.entrySet()) {
			nameValuePairs.add(new BasicNameValuePair(map.getKey(), map.getValue()));
		}
		return nameValuePairs;
	}
	
	/**
	 * 获取头部信息
	 * HttpClientUtils.getHeaders()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-11 </P>
	 * @param headerMap headerMap
	 * @return header[]
	 */
	private static Header[] getHeaders(Map<String, String> headerMap) {
		if (headerMap == null || headerMap.size() == 0) {
			return null;
		}
		Header[] headers = new BasicHeader[headerMap.size()];
		Header header;
		int i = 0;
		for (Map.Entry<String, String> map : headerMap.entrySet()) {
			header = new BasicHeader(map.getKey(), map.getValue());
			headers[i] = header;
			i++;
		}
		return headers;
	}
	

	private static DefaultHttpClient setHttpsConditon(String url,
			DefaultHttpClient httpClient) {
		if(url.indexOf("https") != 0){
			return httpClient;
		}
		final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return _AcceptedIssuers;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {
				}
				
			};
			ctx.init(null, new TrustManager[]{tm}, new SecureRandom());
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
			ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
			return new DefaultHttpClient(mgr, httpClient.getParams());
		} catch (Exception e) {
			System.out.println("=====:=====");
			e.printStackTrace();
		}
		return httpClient;
	}
	
	public static void downLoadFile(String url, String absFile){
		go(url, false, null, null, DEFAULT_CHARSET_UTF8, false, DETAULT_TRY_COUNT, absFile);
	}

	public static void downLoadFileWithUnicode(String url, String absFile){
		go(url, false, null, null, "Unicode", false, DETAULT_TRY_COUNT, absFile);
	}

	
	public static void downLoadFile(String url, final String absFile, final boolean isPost, final Map<String, String> params, final Map<String, String> headerMap, final String charset, boolean isAsync, final int tryCount){
		go(url, isPost, params, headerMap, charset, isAsync, tryCount, absFile);
	}
	
	/**
	 * 关闭连接
	 * HttpClientUtils.abortConnection()<BR>
	 * <P>Author :  yubin </P>  
	 * <P>Date : 2015-3-11 </P>
	 * @param hrb
	 * @param httpclient
	 */
	private static void abortConnection(final HttpRequestBase hrb, final HttpClient httpclient){
		if (hrb != null) {
			hrb.abort();
		}
		if (httpclient != null) {
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	private static boolean fileRename(String srcFileName, String descFileName){
		return fileRename(new File(srcFileName), new File(descFileName));
	}
	private static boolean fileRename(File srcFile, File descFile){
		if(srcFile == null || descFile == null){
			return false;
		}
		if(!srcFile.exists()){
			return false;
		}
		return srcFile.renameTo(descFile);
	}

	/**
	 * 发送带json参数的post请求
	 */
    public static String[] goWithJsonParam(String url, String param){
		
    	PrintWriter out = null;
		BufferedReader in = null;
		String result[] = { "" };
		
		try {
			
			if (url == null || url.trim().length() == 0) {
				throw new RuntimeException("URL为空，请检查");
			}
			if (!url.matches("^http[s]?://.*?")) {
				url = DEFAULT_SCHEMA + url;
			}

			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			if(StringUtils.isNotBlank(param)) {
				out.print(param);
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			int i = 0;
			while ((line = in.readLine()) != null) {
				result[i] += line;
				i++;
			}
		} catch (Exception e) {
			log.warn("调用goWithJsonParam发送Post请求失败，错误信息：" + e.getMessage() + "url=" + url + "param=" + param, e);
			throw new RuntimeException("发送Post请求失败！" + e.getMessage());
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.warn("调用goWithJsonParam关流错误：" , ex);
			}
		}
		return result;
    }


	/**
	 * @param url  请求路径
	 * @param map  请求参数集合
	 * @return  请求结果的json
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @Return : 返回信息
	 * @Auther : <P> HYF <P>
	 * @Date : <P> 2018/6/5 15:23 <P>
	 */
	public static String doPost(String url, Map<String, String> map)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();//HttpClient httpClient = new DefaultHttpClient();
		// post请求返回结果
		HttpPost method = new HttpPost(url);
		// 设置参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if(map!=null){
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator
						.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
						"UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
		}
		String result = "";
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
		method.setConfig(requestConfig);
		HttpResponse response = httpClient.execute(method);
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				result = EntityUtils.toString(resEntity, "UTF-8");
			}
		}
		return result;
	}

}
