<h1 align="center">  Tesra Java SDK Interface</h1>

<p align="center" class="version">Version 1.0.0 </p>

English / [中文](../cn/interface.md)

## Introduction

This document provides a list of interfaces and their methods used in the Java SDK.

They are:

* Init 
* Communication with block chain interface
* Wallet manager 
* Digit asset
* Digit identity
* Neo smart contract deploy and invoke
* Native smart contract invoke
* Verify Signature

----
### Init

 |     | Main   Function | Description |           
 |:-----|:--------|:-----------------------|
|   1 | sdk.setRpc(rpcUrl)                            |   set rpc    |        
|   2 | sdk.setRestful(restUrl)                       |   set restful|
|   3 | sdk.setWesocket(wsUrl, lock)                  |   set websocket|
|   4 | wm.setDefaultConnect(wm.getWebSocket());      |    set desualt|
|   5 | wm.openWalletFile("TstAssetDemo.json");       |   open wallet|


### Communication with block chain interface

 |     | Main   Function | Description |           
 |:-----|:--------|:-----------------------|
 |    1 | getNodeCount()                       |  query  node count |
 |    2 | getBlock(15)                         |  query  block |
 |    3 | getBlockJson(15)                     |  query   block    |
 |    4 | getBlockJson("txhash")               |  query  block     |
 |    5 | getBlock("txhash")                   |  query block      |
 |    6 | getBlockHeight()                     |  query height |
 |    7 | getTransaction("txhash")             |  query transaction        |                              
 |    8 | getStorage("contractaddress", key)   |  query storage |
 |    9 | getBalance("address")                |  query balance |
 |   10 | getContractJson("contractaddress")   |  query contract           |
 |   11 | getSmartCodeEvent(59)                |  query contract event |
 |   12 | getSmartCodeEvent("txhash")          |  query contract event |
 |   13 | getBlockHeightByTxHash("txhash")     |  query transaction block height |
 |   14 | getMerkleProof("txhash")             |  get merkle proof |
 |   15 | sendRawTransaction("txhexString")    |  send transaction |
 |   16 | sendRawTransaction(Transaction)      |  send transaction |
 |   17 | sendRawTransactionPreExec()          |  send prepare execution transaction |
 |   18 | getAllowance("tst","from","to")      |  query allowance |
 |   19 | getMemPoolTxCount()                  |  query memory pool transaction count |
 |   20 | getMemPoolTxState()                  |  query memory pool transaction state |
 |   21 | syncSendRawTransaction("data")       |  sync Send RawTransaction |

----
### Wallet manager
**Digital assets**

 |     | Main   Function | Description |           
 |:-----|:--------|:-----------------------|
|   1 | Account importAccount(String encryptedPrikey, String pwd,byte[] salt,String address)   |   import account|
|   2 | Account createAccount(String password)                                     |   create account|
|   3 | Account createAccountFromPriKey(String password, String prikey)            |   create with private key|
|   4 | AccountInfo createAccountInfo(String password)                             |   create with private key|
|   5 | AccountInfo createAccountInfoFromPriKey(String password, String prikey)    |   create with private key|
|   6 | AccountInfo getAccountInfo(String address, String password,byte[] salt)    |   get account info|
|   7 | List<Account> getAccounts()                                                |   get accounts|
|   8 | Account getAccount(String address)                                         |   get account|
|   9 | Account getDefaultAccount()                                                |   get default account|

**Digital identity**

 |     | Main   Function |       
 |:-----|:--------|
|   1 | Identity importIdentity(String encryptedPrikey, String pwd,String address) |   
|   2 | Identity createIdentity(String password)                                   |   
|   3 | Identity createIdentityFromPriKey(String password, String prikey)          |   
|   4 | IdentityInfo createIdentityInfo(String password)                           |   
|   5 | IdentityInfo createIdentityInfoFromPriKey(String password, String prikey)  |   
 |  6 | IdentityInfo getIdentityInfo(String tstid, String password)                |     
 |  7 | List<Identity> getIdentitys()                                              |    
 |  8 | Identity getIdentity(String tstid)                                         |    
|   9 | Identity getDefaultIdentity()                                              |   
|  10 | Identity addTstIdController(String tstid, String key, String id)           |  


**Mnemonic and keystore**

 |     | Main   Function |           
 |:-----|:--------|
 |  1 | Map exportIdentityQRCode(Wallet walletFile, Identity identity)  |   
|   2 | Map exportAccountQRCode(Wallet walletFile,Account account)                              |   
|   3 | String getPriKeyFromQrCode(String qrcode,String password)          |   
|   4 | String generateMnemonicCodesStr()                         |   
|   5 | byte[] getSeedFromMnemonicCodesStr(String mnemonicCodesStr) |   
 |  6 | byte[] getPrikeyFromMnemonicCodesStrBip44(String mnemonicCodesStr)                |     
|   7 | String encryptMnemonicCodesStr(String mnemonicCodesStr, String password, String address)    |    
|   8 | decryptMnemonicCodesStr(String encryptedMnemonicCodesStr, String password,String address)     |    
   
----
### Digital assets：
** Native digit assets - TSG**

 |     | Main   Function | Description |           
 |:-----|:--------|:-----------------------|
|    1 | String sendTransfer(Account sendAcct, String recvAddr, long amount,Account payerAcct,long gaslimit,long gasprice)   |  transfer|
 |   2 | long queryBalanceOf(String address)                                                       |  query Balance|
 |   3 | long queryAllowance(String fromAddr,String toAddr)                                         |  query Allowance|
 |   4 | String sendApprove(Account sendAcct, String recvAddr, long amount,Account payerAcct,long gaslimit,long gasprice)    |  Approve|
 |   5 | String sendTransferFrom(Account sendAcct, String fromAddr, String toAddr,long amount,Account payerAcct,long  gaslimit,long gasprice) |  TransferFrom|
 |   6 | String queryName()                                                                          |  query Name|
 |   7 | String querySymbol()                                                                        |  query Symbol|
 |   8 | long queryDecimals()                                                                        |  query Decimals|
 |   9 | long queryTotalSupply()                                                                     |  query TotalSupply|
      
      
** Native digit assets - TSG**

 |     | Main   Function | Description |           
 |:-----|:--------|:-----------------------|
|    1 | String sendTransfer(Account sendAcct, String recvAddr, long amount,Account payerAcct,long gaslimit,long gasprice)   |  transfer|
 |   2 | long queryBalanceOf(String address)                                                       |  get balance|
 |   3 | long queryAllowance(String fromAddr,String toAddr)                                         |  get llowance|
 |   4 | String sendApprove(Account sendAcct, String recvAddr, long amount,Account payerAcct,long gaslimit,long gasprice)    |  Approve|
 |   5 | String sendTransferFrom(Account sendAcct, String fromAddr, String toAddr,long amount,Account payerAcct,long gaslimit,long gasprice) | TransferFrom|
 |   6 | String queryName()                                                                          |  query name|
 |   7 | String querySymbol()                                                                        |  query Symbol|
  |  8 | long queryDecimals()                                                                        |  query Decimals|
 |   9 | long queryTotalSupply()                                                                     |  query TotalSupply|
  | 10 | String claimTsg(Account sendAcct, String toAddr, long amount, Account payerAcct, long gaslimit, long gasprice)             |  claim tsg|
 |  11 | String unclaimTsg(String address)   |  query unclaim tsg|
      
      

**NEO Nep-5 digit assets**

 |     | Main   Function | Description |           
 |:-----|:--------|:-----------------------|
|    1 | void setContractAddress(String codeHash)                                                      | set contract address|
 |   2 | String sendInit(Account acct, Account payerAcct,long gaslimit,long gasprice)                   |  init |
 |   3 | long sendInitGetGasLimit()                                                                     |  prepare execution init|
|    4 | String sendTransfer(Account acct, String recvAddr, long amount,Account payerAcct, long gaslimit,long gasprice)        |  transfer|
|    5 | long sendTransferGetGasLimit(Account acct, String recvAddr, long amount)                      |  prepare execution transfer    |                          
|    6 | String queryBalanceOf(String addr)                                                            |  query balance|
|    7 | String queryTotalSupply()                                                                     |  query  TotalSupply|
 |   8 | String queryName()                                                                            |  query name|
|    9 | String queryDecimals()                                                                        |  query decimals|
|   10 | String querySymbol()                                                                          |  query Symbol|

----
### Digital Identity
**tstid**

 |     | Main   Function | Description |           
 |:-----|:--------|:-----------------------|
 |   1 | String getContractAddress()       |  get contract address |
|    2 | Identity sendRegister(Identity ident, String password,byte[] salt,Account payerAcct,long gaslimit,long gasprice)  |  register tstid|
|    3 | Identity sendRegisterPreExec(Identity ident, String password,byte[] salt,Account payerAcct,long gaslimit,long gasprice)                                  |  prepare execution registrytstid|
 |   4 | Identity sendRegisterWithAttrs(Identity ident, String password,byte[] salt,Attribute[] attributes,Account payerAcct,long gaslimit,long gasprice)         |  register tstid with add attribute|
 |   5 | String sendAddPubKey(String tstid, String password,byte[] salt, String newpubkey,Account payerAcct,long gaslimit,long gasprice)                          |  add pubkey|
 |   6 | String sendAddPubKey(String tstid,String recoveryTstid, String password,byte[] salt, String newpubkey,Account payerAcct,long gaslimit,long gasprice)      |  add pubkey|
 |   7 | String sendGetPublicKeys(String tstid)                                                                                                                  |  add pubkey|
 |   8 | String sendRemovePubKey(String tstid, String password,byte[] salt, String removePubkey,Account payerAcct,long gaslimit,long gasprice)                    |  remove pubkey|
 |   9 | String sendRemovePubKey(String tstid, String recoveryTstid,String password,byte[] salt, String removePubkey,Account payerAcct,long gaslimit,long gasprice)|  remove pubkey|
 |  10 | String sendGetKeyState(String tstid,int index)                                                                                                          |  get pubkey status|
|   11 | String sendAddAttributes(String tstid, String password,byte[] salt, Attribute[] attributes,Account payerAcct,long gaslimit,long gasprice)                |  add attribute|
 |  12 | String sendGetAttributes(String tstid)                                                                                                                  |  query attribute|
 |  13 | String sendRemoveAttribute(String tstid,String password,byte[] salt,String path,Account payerAcct,long gaslimit,long gasprice)                           |  remove attribute|
 |  14 | String sendAddRecovery(String tstid, String password,byte[] salt, String recoveryTstid,Account payerAcct,long gaslimit,long gasprice)                     |  add Recovery|
 |  15 | String sendChangeRecovery(String tstid, String newRecovery, String oldRecovery, String password,byte[] salt,Account payerAcct, long gaslimit,long gasprice)                            |  change Recovery|
|   16 | String sendGetDDO(String tstid)  |  get DDO|
   

   
**Make**

 |     | Main   Function |           
 |:-----|:--------|
 |  1 | Transaction makeRegister(String tstid,String password,byte[] salt,String payer,long gaslimit,long gasprice)                                              |
|   2 | Transaction makeRegisterWithAttrs(String tstid, String password,byte[] salt, Attribute[] attributes, String payer, long gaslimit, long gasprice)         |
|   3 | Transaction makeAddPubKey(String tstid,String password,byte[] salt,String newpubkey,String payer,long gaslimit,long gasprice)                            |
|   4 | Transaction makeAddPubKey(String tstid,String recoveryAddr,String password,byte[] salt,String newpubkey,String payer,long gaslimit,long gasprice)        |
|   5 | Transaction makeRemovePubKey(String tstid, String password,byte[] salt, String removePubkey,String payer,long gaslimit,long gasprice)                    |
|   6 | Transaction makeRemovePubKey(String tstid,String recoveryAddr, String password, byte[] salt,String removePubkey,String payer,long gaslimit,long gasprice)|
|   7 | Transaction makeAddAttributes(String tstid, String password,byte[] salt, Attribute[] attributes,String payer,long gaslimit,long gasprice)                |
|   8 | Transaction makeRemoveAttribute(String tstid,String password,byte[] salt,String path,String payer,long gaslimit,long gasprice)                           |
|   9 | Transaction makeAddRecovery(String tstid, String password,byte[] salt, String recoveryAddr,String payer,long gaslimit,long gasprice)                     |

  
**Claim**
  
 |     | Main   Function |           
 |:-----|:--------|
 |  1 | public Object getMerkleProof(String txhash)                                                                                  |   
 |  2 | boolean verifyMerkleProof(String claim)                                                                                       |                 
 |  3 | String createTstIdClaim(String signerTstid, String pwd,byte[] salt, String context, Map claimMap, Map metaData,Map clmRevMap,long expire) |
 |  4 | boolean verifyTstIdClaim(String claim)                                                                                        |   
  

 
**Claim record**
  
 |     | Main   Function |          
 |:-----|:--------|
  | 1 | String sendCommit(String issuerTstid,String password,byte[] salt,String subjectTstid,String claimId,Account payerAcct,long gaslimit,long gasprice)  |
|   2 | String sendRevoke(String issuerTstid,String password,byte[] salt,String claimId,Account payerAcct,long gaslimit,long gasprice)                 |
|   3 | String sendGetStatus(String claimId) |   
  
----
 ### Neo smart contract deploy and invoke
 
 **Deploy and invoke**
  
 |     | Main   Function | Description |          
 |:-----|:--------|:-----------------------|
 |   1 | DeployCode makeDeployCodeTransaction(String code, boolean needStorage, String name, String version, String author, String email, String desp, byte vmtype,String payer,long gaslimit,long gasprice)|   deploy|
 |   2 | InvokeCode makeInvokeCodeTransaction(String codeAddr,String method,byte[] params, byte vmtype, String payer,long gaslimit,long gasprice)                                                           |   invoke|
  


 ### Native smart contract invoke

 #### Authority manager contract

**Authority manager**

 |     | Main   Function |       
 |:-----|:--------|
 |    1 | String sendTransfer(String adminTstId,String password,byte[] salt,String contractAddr, String newAdminTstID,int key,Account payerAcct,long gaslimit,long gasprice)                 |
 |    2 | String assignFuncsToRole(String adminTstID,String password,byte[] salt,String contractAddr,String role,String[] funcName,int key,Account payerAcct, long gaslimit,long gasprice)    |
 |    3 | String assignTstIDsToRole(String adminTstId,String password,byte[] salt,String contractAddr,String role,String[] tstIDs, int key,Account payerAcct, long gaslimit,long gasprice)    |
  |   4 | String delegate(String tstid,String password,byte[] salt,String contractAddr,String toTstId,String role,int period,int level,int key,Account payerAcct, long gaslimit,long gasprice)|
|     5 | String withdraw(String initiatorTstid,String password,byte[] salt,String contractAddr,String delegate, String role,int key,Account payerAcct, long gaslimit,long gasprice)          |


**Make**

 |     | Main   Function |      
 |:-----|:--------|
 |    1 | Transaction makeTransfer(String adminTstID,String contractAddr, String newAdminTstID,int key,String payer,long gaslimit,long gasprice)                    |   
 |    2 | Transaction makeAssignFuncsToRole(String adminTstID,String contractAddr,String role,String[] funcName,int key,String payer,long gaslimit,long gasprice)   |   
 |    3 | Transaction makeAssignTstIDsToRole(String adminTstId,String contractAddr,String role,String[] tstIDs, int key,String payer,long gaslimit,long gasprice)   |   
 |    4 | Transaction makeDelegate(String tstid,String contractAddr,String toAddr,String role,int period,int level,int key,String payer,long gaslimit,long gasprice)|   
|     5 | Transaction makeWithDraw(String tstid,String contractAddr,String delegate, String role,int key,String payer,long gaslimit,long gasprice)                  |   

 
 #### Governance contract

 |    | Main   Function |                                                                                                                                                                             |   Description|
 -----|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 |   1 | String registerCandidate(Account account, String peerPubkey, long initPos, String tstid,String tstidpwd,byte[] salt,  long keyNo, Account payerAcct, long gaslimit, long gasprice) | Mortgage a certain TSG, consume a certain amount of additional TSG, apply to become a candidate node |
 |   2 | String unRegisterCandidate(Account account, String peerPubkey,Account payerAcct, long gaslimit, long gasprice)                  | Cancel the application to become a candidate node, unfreeze the mortgaged TSG|
 |   3 | String withdrawTsg(Account account,Account payerAcct,long gaslimit,long gasprice)                                               | Extract untied tsg|
 |   4 | String getPeerInfo(String peerPubkey)                                                                                           | Query node information|
 |   5 | String getPeerInfoAll()                                                                                                         | Query all nodes|
 |   6 | String getAuthorizeInfo(String peerPubkey,Address addr)                                                                         | Query the authorization information of a certain address to a node|
 |   7 | String withdraw(Account account,String peerPubkey[],long[] withdrawList,Account payerAcct,long gaslimit,long gasprice)          | Take out the mortgage TSG in an unfrozen state|
 |   8 | String quitNode(Account account,String peerPubkey,Account payerAcct,long gaslimit,long gasprice)                                | Exit node|
 |   9 | String addInitPos(Account account,String peerPubkey,int pos,Account payerAcct,long gaslimit,long gasprice)                      | The node adds the initPos interface, which can only be called by the node owner.|
 |   10| String reduceInitPos(Account account,String peerPubkey,int pos,Account payerAcct,long gaslimit,long gasprice)                   | The node reduces the initPos interface and can only be called by the node owner. The initPos cannot be lower than the promised value, and cannot be lower than 1/10 of the accepted number of licenses.|
 |   11| String setPeerCost(Account account,String peerPubkey,int peerCost,Account payerAcct,long gaslimit,long gasprice)                | The node sets the proportion of its own exclusive incentives|
 |   12| String changeMaxAuthorization(Account account,String peerPubkey,int maxAuthorize,Account payerAcct,long gaslimit,long gasprice) | The node modifies the maximum number of authorized TSGs it accepts.|
 |   13| String getPeerAttributes(String peerPubkey)                                                                                     | Query node attribute information|
 |   14| String getSplitFeeAddress(String address)                                                                                       | Query the incentives for an address|



### Verify Signature
       
```

com.github.TesraSupernet.account.Account acct = new com.github.TesraSupernet.account.Account(tstSdk.defaultSignScheme);
byte[] data = "12345".getBytes();
DataSignature sign = new DataSignature(tstSdk.defaultSignScheme, acct, data);
byte[] signature = sign.signature();


com.github.TesraSupernet.account.Account acct2 = new com.github.TesraSupernet.account.Account(false,acct.serializePublicKey());
DataSignature sign2 = new DataSignature();
System.out.println(sign2.verifySignature(acct2, data, signature));
    
```   