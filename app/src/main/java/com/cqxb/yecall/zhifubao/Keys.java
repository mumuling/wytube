/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.cqxb.yecall.zhifubao;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {	
	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088011271959663";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "admin@cqsic.com";

	//商户公钥
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	
	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOJtlnppeIy1gULmr0RdDNjIyPAdjIG4+QWi1EBbx9Q39y8JVfhTtkSQPQuJZg4vxJ6NfxgXKwxS335hvMVpxd58Xy1Kxt+UUSHBaEF0v6Ext3yaajuV2JGUzMIzOOTv1cJ/Ua+I8mN3ReshSUtlvz554deMc6I3k3anvhkO/ZolAgMBAAECgYEAiwnGFEb9qlGuHRmwWCdXQysQEDnk1Kdz6p0Q/rAdJdhz5aMy8jjdPH7hrVrimyWD8+RpPa7EVV3yNRXpJ8QKpqr0lg4uuC2HjxPNqvf30Fs9oBBMm/CdqCS36JsR7TsQWbzxMcquowZKzqv5iKIefMFUkLD4/owQeRNzH+fWUMUCQQD8Byr7i7u/xKXmVdgcJB/P0p1Cnf/u/5esNs6pgXt8PWCntfpER4/T1QRDH5rEitYY3o6dz8PF5P7dc9awNUSTAkEA5f8ibE9pS3ATlaBq4ArFER4faJzozM7u42aiLSYkYUiXzcFCxwCa5ud9jPV3Oh/jBUrzp6Fb90hV4nmD+AHRZwJABgO1xuMCzATJYMHTsng6Oh9wmVJj9TQsTnPQYsMwSzq7v8TcAB0lFY0T2PY8H0yg518IUEPRDDv2yRommXXr+QJBAN6zVr+NfSVAlpYRSKs7gmn6wurm1DxMOuAR5wLUpfFU+ziN430RxuvCRr2QiSvM6GOdmaQ9B/G/JvouM2yXRg0CQDcOE5FOhYqOEKUjPuvG2LdnNzzWyy2YZPd26uGuj0DUHj9suSeOWgtmlG4vm7KhIpyynXIMqDxW8/eGCPVHy2s=";
}
