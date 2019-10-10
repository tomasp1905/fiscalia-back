package com.fiscalia.springboot.backend.apirest.auth;

public class JwtConfig {
	public static final  String LLAVE_FINAL = "alguna.clave.secreta.12345687";

	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIBPAIBAAJBAM7/cDvoRHlCpWLpXcl+kWpDHEOe15RtuXeoOMPjsQ/H6qOiBh/B\r\n" + 
			"0/gYs3c8HK+J8yCEytbhipShbAHp+9FDUh8CAwEAAQJAA5NN8Pr2E9Ie4TJ+uDhD\r\n" + 
			"cr5uAoz/1ESA65wwPtH0AP2/DkqKrZWL63ZZFBC0jq7viIUdcOnxCim41NtjFTou\r\n" + 
			"gQIhAO/EA1kVAcBn5Dszr4ZKuPdpylm5NmBOz2IvWM7pApovAiEA3QNxOiFQeKZO\r\n" + 
			"lKpE+9i4DtVWfjnZwyAbibEMNrgF+xECIQCsKSEGTBEv6Ol2oQw971WVQf8sIdOr\r\n" + 
			"n6tFox2vpJxbvQIhAMLtqE3G2DyxIZmJW3JswFmE29ZUm7W3edJFbypLwCqhAiEA\r\n" + 
			"kwUwWFhDNnCIURFA0gBtz0Ze983yTig49nYv8gsr3uE=\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA="-----BEGIN PUBLIC KEY-----\r\n" + 
			"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAM7/cDvoRHlCpWLpXcl+kWpDHEOe15Rt\r\n" + 
			"uXeoOMPjsQ/H6qOiBh/B0/gYs3c8HK+J8yCEytbhipShbAHp+9FDUh8CAwEAAQ==\r\n" + 
			"-----END PUBLIC KEY-----";
}
