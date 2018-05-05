package com.wesayweb.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static com.wesayweb.constants.SecurityConstants.SECRET;
public class JwtSecurityUtil {

 
	public String createJWT(String id, String subject,String issuer) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject)
				.setIssuer(issuer).signWith(signatureAlgorithm, signingKey);
		return builder.compact();
	}
	
	public String createJWTTokenForFriendRequest(String sendersemail, 
												 String recieversemail,
												 String issuer) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		JwtBuilder builder = Jwts.builder().setId(sendersemail).
				setIssuedAt(now).
				setSubject(recieversemail)
				.setIssuer(issuer).signWith(signatureAlgorithm, signingKey);
		return builder.compact();
	}
	public Map<String, String> parseInvitationJWT(String jwt) {
		Map<String, String> returnMap = new HashMap<String, String>(); 
	    Claims claims = Jwts.parser()         
	       .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
	       .parseClaimsJws(jwt).getBody();
	    returnMap.put("sendersid", claims.getId());
	    returnMap.put("recieversemail", claims.getSubject());
	    return returnMap; 
	}
	
	public Map<String, String> parseJWT(String jwt) {
		Map<String, String> returnMap = new HashMap<String, String>(); 
	    Claims claims = Jwts.parser()         
	       .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
	       .parseClaimsJws(jwt).getBody();
	    returnMap.put("userid", claims.getId());
	    returnMap.put("email", claims.getSubject());
	    returnMap.put("mobile", claims.getIssuer());
	    return returnMap; 
	}
}
