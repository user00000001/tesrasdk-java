<h1 align="center"> 数字身份 </h1>

<p align="center" class="version">Version 1.0.0 </p>

[English](../en/identity_claim.md) / 中文

## 介绍

数字身份相关介绍可参考[TSG ID 身份标识协议及信任框架](https://github.com/TesraSupernet/tesra-DID)。

## 钱包文件及规范

钱包文件是一个Json格式的数据存储文件，可同时存储多个数字身份和多个数字资产账户。具体参考[钱包文件规范](../en/Wallet_File_Specification.md)。

为了创建数字身份，您首先需要创建/打开一个钱包文件。


```java
//如果不存在钱包文件，会自动创建钱包文件。
TstSdk tstSdk = TstSdk.getInstance();
tstSdk.openWalletFile("Demo3.json");
```
> 注：目前仅支持文件形式钱包文件，也可以扩展支持数据库或其他存储方式。



## 数字身份数据结构

**Identity数据结构说明**

`tstid` 是代表身份的唯一的id
`label` 是用户给身份所取的名称。
`lock` 表明身份是否被用户锁定了。客户端不能更新被锁定的身份信息。默认值为false。
`controls` 是身份的所有控制对象ControlData的数组。
`extra` 是客户端开发者存储额外信息的字段。可以为null。

```
//Identity数据结构
public class Identity {
	public String label = "";
	public String tstid = "";
	public boolean isDefault = false;
	public boolean lock = false;
	public List<Control> controls = new ArrayList<Control>();
	public  Object extra = null;
}
```

`algorithm`是用来加密的算法名称。
`parameters` 是加密算法所需参数。
`curve` 是椭圆曲线的名称。
`id` 是control的唯一标识。
`key` 是NEP-2格式的私钥。

```
public class Control {
    public String algorithm = "ECDSA";
    public Map parameters = new HashMap() ;
    public String id = "";
    public String key = "";
    public String salt = "";
    public String hash = "sha256";
    @JSONField(name = "enc-alg")
    public String encAlg = "aes-256-gcm";
    public String address = "";
}
```

##  数字身份接口


### 1. 注册身份

创建数字身份指的是产生一个Identity数据结构的身份信息，并写入到到钱包文件中。

```
Identity identity = tstSdk.getConnect().createIdentity("passwordtest");
//创建的账号或身份只在内存中，如果要写入钱包文件，需调用写入接口
tstSdk.getWalletMgr().writeWallet();
```

**向链上注册身份**

只有向区块链链成功注册身份之后，该身份才可以真正使用。

有两种方法实现向链上注册身份

方法一，注册者指定支付交易费用的账户地址

```
Identity identity = tstSdk.getWalletMgr().createIdentity(password);
tstSdk.nativevm().tstId().sendRegister(identity2,password,payerAcct,gaslimit,gasprice);
```


方法二，将构造好的交易发送给服务器，让服务器进行交易费用账号的签名操作。


```
Identity identity = tstSdk.getWalletMgr().createIdentity(password);
Transaction tx = tstSdk.nativevm().tstId().makeRegister(identity.tstid,password,payerAcc.address,tstSdk.DEFAULT_GAS_LIMIT,0);
tstSdk.signTx(tx,identity.tstid,password);
tstSdk.getConnect().sendRawTransaction(tx);
```

链上注册成功后，对应此TSG ID的身份描述对象DDO将被存储在本体区块链上。

关于DDO的信息可以从[TSG ID 身份标识协议](https://github.com/TesraSupernet/tesra-DID/blob/master/README_cn.md)


### 2. 身份管理

**导入身份**

当用户已经拥有了一个数字身份或者数字账户，SDK支持将其导入到钱包文件中。

> Note: 建议导入一个数字身份之前，建议查询链上身份，如果链上身份DDO不存在，表示此数字身份未在链上注册，请使用上面注册数字身份的方法把身份注册到链上。


```java
Identity identity = tstSdk.getWalletMgr().importIdentity(encriptPrivateKey,password,salt,address);
//写入钱包      
tstSdk.getWalletMgr().writeWallet();
```


参数说明：
encriptPrivateKey: 加密后的私钥
password： 加密私钥使用的密码
salt: 解密私钥用的参数
address: 账户地址base58编码

**移除身份**

```java
tstSdk.getWalletMgr().getWallet().removeIdentity(tstid);
//写入钱包
tstSdk.getWalletMgr().writeWallet();
```

**设置默认账号或身份**

```java
//根据账户地址设置默认账户
tstSdk.getWalletMgr().getWallet().setDefaultAccount(address);
//根据identity索引设置默认identity
tstSdk.getWalletMgr().getWallet().setDefaultIdentity(index);
tstSdk.getWalletMgr().getWallet().setDefaultIdentity(tstid);
```

### 3. 查询链上身份信息

链上身份DDO信息，可以通过TSG ID进行查询。

```json
//通过TSG ID获取DDO
String ddo = tstSdk.nativevm().tstId().sendGetDDO(tstid);

//返回DDO格式
{
	"Attributes": [{
		"Type": "String",
		"Value": "value1",
		"Key": "key1"
	}],
	"TstId": "did:tst :TA5UqF8iPqecMdBzTdzzANVeY8HW1krrgy",
	"Recovery": "TA6AhqudP1dcLknEXmFinHPugDdudDnMJZ",
	"Owners": [{
		"Type": "ECDSA",
		"Curve": "P256",
		"Value": "12020346f8c238c9e4deaf6110e8f5967cf973f53b778ed183f4a6e7571acd51ddf80e",
		"PubKeyId": "did:tst :TA5UqF8iPqecMdBzTdzzANVeY8HW1krrgy#keys-1"
	}, {
		"Type": "ECDSA",
		"Curve": "P256",
		"Value": "1202022fabd733d7d7d7009125bfde3cb0afe274769c78fd653079ecd5954ae9f52644",
		"PubKeyId": "did:tst :TA5UqF8iPqecMdBzTdzzANVeY8HW1krrgy#keys-2"
	}]
}

```



### 4. 身份属性

#### 4.1 更新链上DDO属性
方法一，指定支付交易费用的账户

```java
//添加或者更新属性
String sendAddAttributes(String tstid, String password,byte[] salt， Attribute[] attributes,Account payerAcct,long gaslimit,long gasprice)
```


| 参数      | 字段   | 类型  | 描述 |             说明 |
| -----    | ------- | ------ | ------------- | ----------- |
| 输入参数   | password| String | 数字身份密码 | 必选，私钥解密的密码 |
|           |salt     | byte[] |  |    必选  |
|           | tstid    | String | 数字身份id  | 必选，身份Id |
|           | attributes | Attribute[]| 属性数组  | 必选 |
|           | payerAcct    | Account | 交易费用支付者账户       |  必选， |
|           | gaslimit      | long | gaslimit     | 必选 |
|           | gasprice      | long | gas价格     | 必选 |
| 输出参数   | txhash   | String  | 交易hash  | 交易hash是64位字符串 |



方法二，将构造好的交易发送给服务器，让服务器进行交易费用账号的签名操作。
```java
Transaction makeAddAttributes(String tstid, String password, byte[] salt,Attribute[] attributes,String payer,
                                          long gaslimit,long gasprice)
```

示例代码
```java
Transaction tx = tstSdk.nativevm().tstId().makeAddAttributes(tstid,password,salt,attributes,payer,gaslimit,0);
tstSdk.signTx(tx,identity.tstid,password);
tstSdk.getConnect().sendRawTransaction(tx);
```

#### 4.2 移除链上DDO属性

方法一

```
String sendRemoveAttribute(String tstid,String password,byte[] salt,String path,Account payerAcct,long gaslimit,long gasprice)
```


| 参数      | 字段   | 类型  | 描述 |             说明 |
| ----- | ------- | ------ | ------------- | ----------- |
| 输入参数 | password| String | 数字身份密码 | 必选 |
|        | salt| byte[] |  | required |
|        | tstid    | String | 数字身份ID   | 必选，身份Id |
|        | path    | byte[]  | path       | 必选，path |
|        | payer    | String  | payer       | 必选，payer |
|        | payerpassword | String  | 支付交易费用的账户地址  | 必选 |
|        | gas   | long | 支付的交易费用     | 必选 |
| 输出参数 | txhash   | String  | 交易hash  | 交易hash是64位字符串 |


方法二，将构造好的交易发送给服务器，让服务器进行交易费用账号的签名操作。
```
Transaction makeRemoveAttribute(String tstid,String password,salt,String path,String payer,long gaslimit,long gasprice)
```

示例代码：
```
Transaction tx = tstSdk.nativevm().tstId().makeRemoveAttribute(tstid,password,salt,path,payer,gaslimit,0);
tstSdk.signTx(tx,identity.tstid,password);
tstSdk.getConnect().sendRawTransaction(tx);
```

### 5. 身份公钥

一个身份可以有多个控制人，身份中的公钥是控制人的公钥列表。
#### 5.1 添加公钥

方法一

```java
String sendAddPubKey(String tstid, String password,byte[] salt, String newpubkey,Account payerAcct,long gaslimit,long gasprice)
```


| 参数      | 字段   | 类型  | 描述 |             说明 |
| ----- | ------- | ------ | ------------- | ----------- |
| 输入参数 | password| String | 数字身份密码 | 必选 |
|        | salt| byte[] |  | required |
|        | tstid    | String | 数字身份ID   | 必选，身份Id |
|        | newpubkey| String  |公钥       | 必选， newpubkey|
|        | payerAcct    | Account  | Payment transaction account  | 必选，payer |
|        | gaslimit   | long | gaslimit     | 必选 |
|        | gasprice   | long | gas价格     | 必选 |
| 输出参数 | txhash   | String  | 交易hash  | 交易hash是64位字符串 |


方法二，将构造好的交易发送给服务器，让服务器进行交易费用账号的签名操作。

```java
Transaction makeAddPubKey(String tstid,String password,byte[] salt,String newpubkey,String payer,long gaslimit,long gasprice)
```
参数说明请参考方法一sendAddPubKey

示例代码
```java
Transaction tx = tstSdk.nativevm().tstId().makeAddPubKey(tstid,password,byte[] salt,newpubkey,payer,gaslimit,gasprice);
tstSdk.signTx(tx,identity.tstid,password);
tstSdk.getConnect().sendRawTransaction(tx);
```

方法三，recovery机制
recovery可以为tstid添加公钥
```java
String sendAddPubKey(String tstid,String recoveryAddr, String password, byte[] salt,String newpubkey,Account payerAcct,long gaslimit,long gasprice)
```

| 参数      | 字段   | 类型  | 描述 |             说明 |
| ----- | ------- | ------ | ------------- | ----------- |
| 输入参数 | tstid    | String | 数字身份ID   | 必选，身份Id |
|        | recoveryAddr| String | recovery地址 | 必选 |
|        | password| String | recovery密码 | 必选 |
|        | salt| byte[] |  | required |
|        | newpubkey| String  |公钥       | 必选， newpubkey|
|        | payer    | String  | payer       | 必选，payer |
|        | payerpwd | String  | 支付交易费用的账户地址  | 必选 |
|        | gaslimit   | long | gaslimit     | 必选 |
|        | gasprice   | long | gas价格     | 必选 |
| 输出参数 | txhash   | String  | 交易hash  | 交易hash是64位字符串 |


方法四（recovery机制）

```java
Transaction makeAddPubKey(String tstid,String recoveryAddr,String password,byte[] salt,String newpubkey,
                                          String payer,long gaslimit,long gasprice)
```

参数说明请参考方法三


#### 5.2 删除公钥

方法一，删除公钥

```java
String sendRemovePubKey(String tstid, String password,byte[] salt, String removePubkey,Account payerAcct,long gaslimit,long gasprice)
```


| 参数      | 字段   | 类型  | 描述 |             说明 |
| ----- | ------- | ------ | ------------- | ----------- |
| 输入参数 | password| String | 数字身份密码 | 必选 |
|        | salt| byte[] |  | required |
|        | tstid    | String | 数字身份ID   | 必选，身份Id |
|        | removePubkey| String  |公钥       | 必选， removePubkey|
|        | payer    | String  | payer       | 必选，payer |
|        | payerpassword | String  | 支付交易费用的账户地址  | 必选 |
|        | gas   | long | 支付的交易费用     | 必选 |
| 输出参数 | txhash   | String  | 交易hash  | 交易hash是64位字符串 |


方法二，将构造好的交易发送给服务器，让服务器进行交易费用账号的签名操作。

```java
Transaction tx = tstSdk.nativevm().tstId().makeRemovePubKey(tstid,password,salt,removePubkey,payer,gas);
tstSdk.signTx(tx,identity.tstid.replace(Common.didtst,""),password,salt);
tstSdk.getConnect().sendRawTransaction(tx);
```

方法三，恢复人机制
```java
String sendRemovePubKey(String tstid, String recoveryAddr,String password, byte[] salt,String removePubkey,Account payerAcct,long gaslimit,long gasprice)
```

| 参数      | 字段   | 类型  | 描述 |             说明 |
| ----- | ------- | ------ | ------------- | ----------- |
| 输入参数 | tstid    | String | 数字身份ID   | 必选，身份Id |
|        | recoveryAddr| String | recovery地址 | 必选 |
|        | password| String | recovery密码 | 必选 |
|        | salt| byte[] |  | required |
|        | newpubkey| String  |公钥       | 必选， newpubkey|
|        | payerAcct    | Account  | Payment transaction account | 必选，payer |
|        | gaslimit   | long | gaslimit     | 必选 |
|        | gasprice   | long | gas价格     | 必选 |
| 输出参数 | txhash   | String  | 交易hash  | 交易hash是64位字符串 |


方法四（recovery机制）
```java
Transaction makeRemovePubKey(String tstid,String recoveryAddr, String password,byte[] salt, String removePubkey,String payer,
                                          long gaslimit,long gasprice)
```

参数说明请参考方法三

### 6. 身份恢复人

当tstid中控制人私钥丢失时，身份恢复人可以设置新的控制人。
#### 6.1 添加恢复人

方法一

```java
String sendAddRecovery(String tstid, String password,byte[] salt, String recoveryAddr,Account payerAcct,long gaslimit,long gasprice)
```

| 参数      | 字段   | 类型  | 描述 |             说明 |
| ----- | ------- | ------ | ------------- | ----------- |
| 输入参数 | password| String | 数字身份密码 | 必选 |
|        | salt| byte[] | | required |
|        | tstid    | String | 数字身份ID   | 必选，身份Id |
|        | recoveryAddr| String  |recovery账户地址 | 必选，recovery|
|        | payerAcct    | Account  | payerAcct  | 必选，payer |
|        | gaslimit   | long | gaslimit     | 必选 |
|        | gasprice   | long | gas价格     | 必选 |
| 输出参数 | txhash   | String  | 交易hash  | 交易hash是64位字符串 |


方法二，将构造好的交易发送给服务器，让服务器进行交易费用账号的签名操作。
```
Transaction makeAddRecovery(String tstid, String password,byte[] salt, String recoveryAddr,String payer,long gaslimit,long gasprice)
```

示例
```
Transaction tx = tstSdk.nativevm().tstId().makeAddRecovery(tstid,password,salt,recovery,payer,gas);
tstSdk.signTx(tx,identity.tstid.replace(Common.didtst,""),password,salt);
tstSdk.getConnect().sendRawTransaction(tx);
```

#### 6.2 修改recovery

方法一
```
String sendChangeRecovery(String tstid, String newRecovery, String oldRecovery, String password,,byte[] salt,long gaslimit,long gasprice)
```

| 参数      | 字段   | 类型  | 描述 |             说明 |
| ----- | ------- | ------ | ------------- | ----------- |
| 输入参数 |tstid    | String | 数字身份ID   | 必选，身份Id |
|        | newRecovery| String  |newRecovery账户地址 | 必选，newRecovery|
|        | oldRecovery| String  |oldRecovery账户地址 | 必选，oldRecovery|
|        | oldRecovery password | String  | oldRecovery password  | 必选 |
|        | password| String | 数字身份密码 | 必选 |
|        | salt| byte[] | | required |
|        | gaslimit   | long | gaslimit     | 必选 |
|        | gasprice   | long | gasprice     | 必选 |
| 输出参数 | txhash   | String  | 交易hash  | 交易hash是64位字符串 |


方法二
```
Transaction makeChangeRecovery(String tstid, String newRecovery, String oldRecovery, String password,byte[] salt,long gaslimit,long gasprice)
```

参数说明请参考上面的方法一




