<h1 align="center">Claim and Attestation </h1>

<p align="center" class="version">Version 1.0.0 </p>

English / [中文](../cn/attest.md)




## Verifiable claim

### Data structure specification

```
class Claim{
  header : Header
  payload : Payload
  signature : byte[]
}
```

```
class Header {
    public String Alg = "TSG-ES256";
    public String Typ = "JWT-X";
    public String Kid;
    }
```

`alg` attribute specifies the signature scheme to use
`typ` attribute can take one of the two values:

    * JWT: This corresponds to the case that blockchain proof is not contained in the claim
    * JWT-X: This corresponds to the case that blockchain proof is a part of the claim

`kid`  attribute refers to the public key used for signature verification. It has the form <ontID>#keys-<id> as defined in TSG ID specification.


```
class Payload {
    public String Ver;
    public String Iss;
    public String Sub;
    public long Iat;
    public long Exp;
    public String Jti;
    @JSONField(name = "@context")
    public String Context;
    public Map<String, Object> ClmMap = new HashMap<String, Object>();
    public Map<String, Object> ClmRevMap = new HashMap<String, Object>();
    }
```

`ver` attribute specifies the version of the claim specification it follows

`iss` attribute refers to the TSG ID of the issuer

`sub` attribute refers to the TSG ID of the recipient

`iat` attribute marks the time the claim was created and has the unix timestamp format

`exp` attribute marks the expiration time of the claim and has the the unix timestamp format

`jti` attribute specifies the unique identifier of the verifiable claim

`@context` attribute specifies the uri of the claim content definition document which defines the meaning of each field and the type of the value

`clm` attribute is an object which contains the claim content

`clm-rev` attribute is an object which defines the revocation mechanism the claim uses <p><br>



### Sign and issue verifiable claim
A verifiable claim is constructed based on user input, which contains signed data.

**createTstIdClaim**

| Parameter      | Field   | Type  | Description |            Remarks |
| ----- | ------- | ------ | ------------- | ----------- |
| Input parameter | signerTstid| String | TSG ID | Required |
|        | password    | String | TSG ID password   | Required |
|        | salt        | byte[] | Private key decryption parameters |Required|
|        | context| String  |Attribute specifies the URI of claim content definition document which defines the meaning of each field and the type of the value | Required|
|        | claimMap| Map  |Content of claim | Required|
|        | metaData   | Map | Claim issuer and subject's TSG ID | Required |
|        | clmRevMap   | Map | Attribute is an object which defines the revocation mechanism the claim use | Required |
|        | expire   | long | Attribute marks the expiration time of the claim and has the format of unix timestamp     | required |
| Output parameter| claim   | String  |   |  |

##### Example of creating an onit claim    
```
String createTstIdClaim (String signerTstid, String password,byte[] salt, String context, Map<String, Object> claimMap, Map metaData,Map clmRevMap,long expire)
```

```
Map<String, Object> map = new HashMap<String, Object>();
map.put("Issuer", dids.get(0).tstid);
map.put("Subject", dids.get(1).tstid);
Map clmRevMap = new HashMap();
clmRevMap.put("typ","AttestContract");
clmRevMap.put("addr",dids.get(1).tstid.replace(Common.didont,""));
String claim = tstSdk.nativevm().tstId().createTstIdClaim(dids.get(0).tstid,password,salt, "claim:context", map, map,clmRevMap,System.currentTimeMillis()/1000 +100000);
```
Note: The issuer may have multiple public keys. The parameter tstid of createTstIdClaim specifies which public key to use. <p> <br>


### Verify verifiable claim

**verifyTstIdClaim**

| Parameter      | Field   | Type  | Description |            Remarks |
| ----- | ------- | ------ | ------------- | ----------- |
| Input parameter | claim| String | Trusted claim | Required |
| Output parameter | true or false   | boolean  |   |  |
  
 
```
boolean verifyTstIdClaim (string claim)
```

##### Example of verifying an tstid claim
```
boolean b = tstSdk.nativevm().tstId().verifyTstIdClaim(claim);
```

##### Example of claim issuance and verification:

```
Map<String, Object> map = new HashMap<String, Object>();
map.put("Issuer", dids.get(0).tstid);
map.put("Subject", dids.get(1).tstid);

Map clmRevMap = new HashMap();
clmRevMap.put("typ","AttestContract");
clmRevMap.put("addr",dids.get(1).tstid.replace(Common.didont,""));

String claim = tstSdk.nativevm().tstId().createTstIdClaim(dids.get(0).tstid,password,salt, "claim:context", map, map,clmRevMap,System.currentTimeMillis()/1000 +100000);
boolean b = tstSdk.nativevm().tstId().verifyTstIdClaim(claim);
```



## Attestation Example

The attest contract of a verifiable claim provides the attest service and records availability information, that is, whether it has been revoked.


#### SDK init


```
String ip = "http://127.0.0.1";
String restUrl = ip + ":" + "20334";
String rpcUrl = ip + ":" + "20336";
String wsUrl = ip + ":" + "20335";
TstSdk wm = TstSdk.getInstance();
wm.setRpc(rpcUrl);
wm.setRestful(restUrl);
wm.setDefaultConnect(wm.getRestful());
wm.openWalletFile("RecordTxDemo.json");
```

Note: codeAddress is the address of the record contract.

#### Create claim

```
Map<String, Object> map = new HashMap<String, Object>();
map.put("Issuer", dids.get(0).tstid);
map.put("Subject", dids.get(1).tstid);

Map clmRevMap = new HashMap();
clmRevMap.put("typ","AttestContract");
clmRevMap.put("addr",dids.get(1).tstid.replace(Common.didont,""));

String claim = tstSdk.nativevm().tstId().createTstIdClaim(dids.get(0).tstid,password,dids.get(0).controls.get(0).getSalt(), "claim:context", map, map,
clmRevMap,System.currentTimeMillis()/1000 +100000);
```

Note: For createTstIdClaim interface details please see the digital identity tstid document https://github.com/TesraSupernet/tesra-java-sdk/blob/master/docs/cn/identity_claim.md


<br>

#### Storage Claim

**sendCommit**
```
String sendCommit (String issuerTstid, String password,byte[] salt, String subjectTstid, String claimId, Account payerAcct, long gaslimit, long gasprice)
```
Function description: Save data to the chain

Parameters:
```issuerTstid```:  Issuer TSG ID
```subjectTstid```:  Subject TSG ID
```password```: Identity password
```claimId```: Trusted claims claim uniqueness mark, i.e. Jti field in Claim
```payerAcct```: Payment transaction account
```gaslimit```: Gas limit
```gasprice```: Gas price
return value: Transaction hash


##### Example

```
String[] claims = claim.split("\\.");
JSONObject payload = JSONObject.parseObject(new String(Base64.getDecoder().decode(claims[1].getBytes())));
tstSdk.neovm().claimRecord().sendCommit(tstid,password,payload.getString("jti"),0)
```


### Get Status

**sendGetStatus**
```
 String sendGetStatus(String claimId)
```
Function description: Query status of trusted claim

Parameters:
        ```claimId```: Trusted claims claim uniqueness mark, i.e. Jti field in Claim

return value： 
        Part 1: Status of the claim: "Not attested", "Attested", "Attest has been revoked"
        Part 2: The certificate's TSG ID.

##### Example
```
String getstatusRes2 = tstSdk.neovm().claimRecord().sendGetStatus(payload.getString("jti"));
```


### Revoke

**sendRevoke**
```
String sendRevoke(String issuerTstid,String password,byte[] salt,String claimId,Account payerAcct,long gaslimit,long gas)
```
Function description:Repeal of a trust claim

Parameters:
```issuerTstid```: Issuer TSG ID
```password```: Attester's TSG ID password
```claimId```: Trusted claims claim uniqueness mark, i.e. Jti field in Claim
```payerAcct```: Payment transaction account
```gaslimit```: Gas limit
```gasprice```: Gas price

return value： This function will return true if and only if the claim is attested, and the revokerTstId is equal to the attester's TSG identity; Otherwise, it will return false.
