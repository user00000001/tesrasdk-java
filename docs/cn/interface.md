<h1 align="center"> Tesra Java SDK 接口 </h1>

<p align="center" class="version">Version 1.0.0 </p>

[English](../en/interface.md) / 中文

## 介绍：

本章节列出了Java SDK中关键的，SDK接口按如下几类区分：

* 初始化接口
* 与链交互接口
* 钱包管理接口
* 数字资产接口
* 数字身份接口
* NEO智能合约部署与调用接口
* Native合约调用
* 签名验签


### 初始化接口：

包括打开钱包文件和设置与链交互方式
 ```
     |                    Function                   |     Description            
 ----|-----------------------------------------------|------------------------
   1 | sdk.setRpc(rpcUrl)                            |   设置rpc            
   2 | sdk.setRestful(restUrl)                       |   设置restful
   3 | sdk.setWesocket(wsUrl, lock)                  |   设置websocket
   4 | wm.setDefaultConnect(wm.getWebSocket());      |    设置默认与链交互方式
   5 | wm.openWalletFile("TstAssetDemo.json");       |   打开钱包
 ```

### 与链交互接口：

* 交互接口列表：
```

      |                     Main   Function                      |           Description            
 -----|----------------------------------------------------------|---------------------------------------------   
    1 | tstSdk.getConnect().getNodeCount()                       |  查询节点数量
    2 | tstSdk.getConnect().getBlock(15)                         |  查询块
    3 | tstSdk.getConnect().getBlockJson(15)                     |  查询块    
    4 | tstSdk.getConnect().getBlockJson("txhash")               |  查询块    
    5 | tstSdk.getConnect().getBlock("txhash")                   |  查询块     
    6 | tstSdk.getConnect().getBlockHeight()                     |  查询当前块高
    7 | tstSdk.getConnect().getTransaction("txhash")             |  查询交易                                     
    8 | tstSdk.getConnect().getStorage("contractaddress", key)   |  查询智能合约存储
    9 | tstSdk.getConnect().getBalance("address")                |  查询余额
   10 | tstSdk.getConnect().getContractJson("contractaddress")   |  查询智能合约          
   11 | tstSdk.getConnect().getSmartCodeEvent(59)                |  查询智能合约事件
   12 | tstSdk.getConnect().getSmartCodeEvent("txhash")          |  查询智能合约事件
   13 | tstSdk.getConnect().getBlockHeightByTxHash("txhash")     |  查询交易所在高度
   14 | tstSdk.getConnect().getMerkleProof("txhash")             |  获取merkle证明
   15 | tstSdk.getConnect().sendRawTransaction("txhexString")    |  发送交易
   16 | tstSdk.getConnect().sendRawTransaction(Transaction)      |  发送交易
   17 | tstSdk.getConnect().sendRawTransactionPreExec()          |  发送预执行交易
   18 | tstSdk.getConnect().getAllowance("tst","from","to")      |  查询允许使用值
   19 | tstSdk.getConnect().getMemPoolTxCount()                  |  查询交易池中交易总量
   20 | tstSdk.getConnect().getMemPoolTxState()                  |  查询交易池中交易状态
   21 | tstSdk.getConnect().syncSendRawTransaction("data")       |  同步发送交易
```  

### 钱包管理接口：

包括数字资产和数字身份管理
 
* 数字资产账户：

```  
     |                        Main   Function                                     |     Description            
 ----|----------------------------------------------------------------------------|------------------------ 
   1 | Account importAccount(String encryptedPrikey, String pwd,byte[] salt,String address)   |   导入资产账户
   2 | Account createAccount(String password)                                     |   创建资产账户
   3 | Account createAccountFromPriKey(String password, String prikey)            |   根据私钥创建
   4 | AccountInfo createAccountInfo(String password)                             |   根据私钥创建
   5 | AccountInfo createAccountInfoFromPriKey(String password, String prikey)    |   根据私钥创建
   6 | AccountInfo getAccountInfo(String address, String password,byte[] salt)    |   获取账号信息
   7 | List<Account> getAccounts()                                                |   查询所有账号
   8 | Account getAccount(String address)                                         |   获取账户
   9 | Account getDefaultAccount()                                                |   获取默认账户
```  

* 数字身份：
```  
     |                        Main   Function                                     |     Description            
 ----|----------------------------------------------------------------------------|------------------------ 
   1 | Identity importIdentity(String encryptedPrikey, String pwd,byte[] salt,String address) |   导入身份
   2 | Identity createIdentity(String password)                                   |   创建身份
   3 | Identity createIdentityFromPriKey(String password, String prikey)          |   根据私钥创建
   4 | IdentityInfo createIdentityInfo(String password)                           |   创建身份
   5 | IdentityInfo createIdentityInfoFromPriKey(String password, String prikey)  |   根据私钥创建
   6 | IdentityInfo getIdentityInfo(String tstid, String password)                |   查询身份信息  
   7 | List<Identity> getIdentitys()                                              |   查询所有身份 
   8 | Identity getIdentity(String tstid)                                         |   获取身份 
   9 | Identity getDefaultIdentity()                                              |   获取默认身份
  10 | Identity addTstIdController(String tstid, String key, String id)           |   添加控制人 
```        

### 数字资产：
1.原生数字资产
2.Nep-5智能合约数字资产

* 原生数字资产：

tst:
```

      |                                         Main   Function                                                     |           Description            
 -----|-------------------------------------------------------------------------------------------------------------|---------------------------------------------
    1 | String sendTransfer(Account sendAcct, String recvAddr, long amount,Account payerAcct,long gaslimit,long gasprice)   |  转账
    2 | long queryBalanceOf(String address)                                                       |  查询余额
    3 | long queryAllowance(String fromAddr,String toAddr)                                         |  查询Allowance
    4 | String sendApprove(Account sendAcct, String recvAddr, long amount,Account payerAcct,long gaslimit,long gasprice)    |  发送Approve
    5 | String sendTransferFrom(Account sendAcct, String fromAddr, String toAddr,long amount,Account payerAcct,long gaslimit,long gasprice) |  发送TransferFrom
    6 | String queryName()                                                                          |  查询资产名
    7 | String querySymbol()                                                                        |  查询资产Symbol
    8 | long queryDecimals()                                                                        |  查询精度
    9 | long queryTotalSupply()                                                                     |  查询总供应量
      
```
tsg:
```

      |                                         Main   Function                                                     |           Description            
 -----|-------------------------------------------------------------------------------------------------------------|---------------------------------------------
    1 | String sendTransfer(Account sendAcct, String recvAddr, long amount,Account payerAcct,long gaslimit,long gasprice)   |  转账
    2 | long queryBalanceOf(String address)                                                       |  查询余额
    3 | long queryAllowance(String fromAddr,String toAddr)                                         |  查询Allowance
    4 | String sendApprove(Account sendAcct, String recvAddr, long amount,Account payerAcct,long gaslimit,long gasprice)    |  发送Approve
    5 | String sendTransferFrom(Account sendAcct, String fromAddr, String toAddr,long amount,Account payerAcct,long gaslimit,long gasprice) |  发送TransferFrom
    6 | String queryName()                                                                          |  查询资产名
    7 | String querySymbol()                                                                        |  查询资产Symbol
    8 | long queryDecimals()                                                                        |  查询精度
    9 | long queryTotalSupply()                                                                     |  查询总供应量
   10 | String claimTsg(Account sendAcct, String toAddr, long amount, Account payerAcct, long gaslimit, long gasprice)             |  提取tsg
   11 | String unclaimTsg(String address)                                                                           |  查询未提取的tsg
      
```   

* Nep-5智能合约数字资产:

```  
      |                                         Main   Function                                       |           Description            
 -----|-----------------------------------------------------------------------------------------------|---------------------------------------------
    1 | void setContractAddress(String codeHash)                                                      | 设置合约地址
    2 | String sendInit(Account acct, Account payerAcct,long gaslimit,long gasprice)                   |  初始化
    3 | long sendInitGetGasLimit()                                                                     |  预执行初始化
    4 | String sendTransfer(Account acct, String recvAddr, long amount,Account payerAcct, long gaslimit,long gasprice)        |  转账
    5 | long sendTransferGetGasLimit(Account acct, String recvAddr, long amount)                      |  预执行转账                              
    6 | String queryBalanceOf(String addr)                                                            |  查询余额
    7 | String queryTotalSupply()                                                                     |  查询总供应量 
    8 | String queryName()                                                                            |  查询名字
    9 | String queryDecimals()                                                                        |  查询精度
   10 | String querySymbol()                                                                          |  查询资产Symbol

```  

### 数字身份：
1.数字身份包括注册、公钥、属性、恢复人等操作。
2.claim接口包括颁发和验证
3.claim存证接口

* tstid功能接口：
```
      |                                         Main   Function                                                     |           Description            
 -----|-------------------------------------------------------------------------------------------------------------|---------------------------------------------
    1 | String getContractAddress()                                                                                                                             |  查询合约地址
    2 | Identity sendRegister(Identity ident, String password,byte[] salt,Account payerAcct,long gaslimit,long gasprice)                                         |  注册tstid
    3 | Identity sendRegisterPreExec(Identity ident, String password,byte[] salt,Account payerAcct,long gaslimit,long gasprice)                                  |  预执行注册tstid
    4 | Identity sendRegisterWithAttrs(Identity ident, String password,byte[] salt,Attribute[] attributes,Account payerAcct,long gaslimit,long gasprice)         |  注册tstid并添加属性
    5 | String sendAddPubKey(String tstid, String password,byte[] salt, String newpubkey,Account payerAcct,long gaslimit,long gasprice)                          |  添加公钥
    6 | String sendAddPubKey(String tstid,String recoveryTstid, String password,byte[] salt, String newpubkey,Account payerAcct,long gaslimit,long gasprice)      |  添加公钥
    7 | String sendGetPublicKeys(String tstid)                                                                                                                  |  获取公钥
    8 | String sendRemovePubKey(String tstid, String password,byte[] salt, String removePubkey,Account payerAcct,long gaslimit,long gasprice)                    |  删除公钥
    9 | String sendRemovePubKey(String tstid, String recoveryTstid,String password,byte[] salt, String removePubkey,Account payerAcct,long gaslimit,long gasprice)|  删除公钥
   10 | String sendGetKeyState(String tstid,int index)                                                                                                          |  获取某公钥状态
   11 | String sendAddAttributes(String tstid, String password,byte[] salt, Attribute[] attributes,Account payerAcct,long gaslimit,long gasprice)                |  添加属性
   12 | String sendGetAttributes(String tstid)                                                                                                                  |  查询属性
   13 | String sendRemoveAttribute(String tstid,String password,byte[] salt,String path,Account payerAcct,long gaslimit,long gasprice)                           |  删除属性
   14 | String sendAddRecovery(String tstid, String password,byte[] salt, String recoveryTstid,Account payerAcct,long gaslimit,long gasprice)                     |  添加恢复人
   15 | String sendChangeRecovery(String tstid, String newRecovery, String oldRecovery, String password,byte[] salt,Account payerAcct, long gaslimit,long gasprice)                            |  修改恢复人
   16 | String sendGetDDO(String tstid)                                                                                                                         |  查询DDO
   
```

* 构造交易接口：

 ```  
     |                                           Make Transaction  Function                                                |     Description            
 ----|---------------------------------------------------------------------------------------------------------------------|------------------------ 
   1 | Transaction makeRegister(String tstid,String password,byte[] salt,String payer,long gaslimit,long gasprice)                                              | 构造注册交易
   2 | Transaction makeRegisterWithAttrs(String tstid, String password,byte[] salt, Attribute[] attributes, String payer, long gaslimit, long gasprice)         | 构造注册tstid并添加属性交易
   3 | Transaction makeAddPubKey(String tstid,String password,byte[] salt,String newpubkey,String payer,long gaslimit,long gasprice)                            | 构造添加公钥交易
   4 | Transaction makeAddPubKey(String tstid,String recoveryAddr,String password,byte[] salt,String newpubkey,String payer,long gaslimit,long gasprice)        | 构造添加公钥交易
   5 | Transaction makeRemovePubKey(String tstid, String password,byte[] salt, String removePubkey,String payer,long gaslimit,long gasprice)                    | 构造删除公钥交易
   6 | Transaction makeRemovePubKey(String tstid,String recoveryAddr, String password,byte[] salt, String removePubkey,String payer,long gaslimit,long gasprice)| 构造删除公钥交易
   7 | Transaction makeAddAttributes(String tstid, String password,byte[] salt, Attribute[] attributes,String payer,long gaslimit,long gasprice)                | 构造添加属性交易
   8 | Transaction makeRemoveAttribute(String tstid,String password,byte[] salt,String path,String payer,long gaslimit,long gasprice)                           | 构造删除属性交易
   9 | Transaction makeAddRecovery(String tstid, String password,byte[] salt, String recoveryAddr,String payer,long gaslimit,long gasprice)                     | 构造添加恢复人交易
  ```
  
* Claim相关接口：
  
 ```
     |                                           Claim Function                                                                      |     Description            
 ----|-------------------------------------------------------------------------------------------------------------------------------|------------------------
   1 | public Object getMerkleProof(String txhash)                                                                                   |   获取merkle证明
   2 | boolean verifyMerkleProof(String claim)                                                                                       |   验证merkle证明                
   3 | String createTstIdClaim(String signerTstid, String pwd, byte[] salt,String context, Map claimMap, Map metaData,Map clmRevMap,long expire) |   创建claim
   4 | boolean verifyTstIdClaim(String claim)                                                                                        |   验证claim
  
 ```
 
* Claim存证接口：
  
 ```
     |                                            Function                                                         |     Description
 ----|-------------------------------------------------------------------------------------------------------------|------------------------
   1 | String sendCommit(String issuerTstid,String password,byte[] salt,String subjectTstid,String claimId,Account payerAcct,long gaslimit,long gasprice) |   存储claim
   2 | String sendRevoke(String issuerTstid,String password,byte[] salt,String claimId,Account payerAcct,long gaslimit,long gasprice)     |   吊销
   3 | String sendGetStatus(String claimId)                                                                                               |   获取状态
  
 ```
 
 ### 智能合约部署与调用接口
 
 部署与调用
  ```
      |                                            Function                                                                                                                             |     Description            
  ----|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------
    1 | DeployCode makeDeployCodeTransaction(String code, boolean needStorage, String name, String version, String author, String email, String desp, byte vmtype,String payer,long gaslimit,long gasprice)|   部署
    2 | InvokeCode makeInvokeCodeTransaction(String codeAddr,String method,byte[] params, String payer,long gaslimit,long gasprice)                                                           |   调用
   
  ```

 ### Native合约调用

 #### 权限管理合约

* 权限管理功能接口：
 ```
       |                                            Function                                                                                                                               |     Description
   ----|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------
     1 | String sendTransfer(String adminTstId,String password,byte[] salt,String contractAddr, String newAdminTstID,int key,Account payerAcct,long gaslimit,long gasprice)                  |   合约管理员转让合约管理权限
     2 | String assignFuncsToRole(String adminTstID,String password,byte[] salt,String contractAddr,String role,String[] funcName,int key,Account payerAcct, long gaslimit,long gasprice)    |   为角色分配函数
     3 | String assignTstIDsToRole(String adminTstId,String password,byte[] salt,String contractAddr,String role,String[] tstIDs, int key,Account payerAcct, long gaslimit,long gasprice)    |   绑定角色到实体身份
     4 | String delegate(String tstid,String password,byte[] salt,String contractAddr,String toTstId,String role,int period,int level,int key,Account payerAcct, long gaslimit,long gasprice)|   将合约调用权代理给其他人
     5 | String withdraw(String initiatorTstid,String password,byte[] salt,String contractAddr,String delegate, String role,int key,Account payerAcct, long gaslimit,long gasprice)          |   收回合约调用权
 ```

 * 构造交易接口：
```
       |                                            Function                                                                                                                               |     Description
   ----|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------
     1 | Transaction makeTransfer(String adminTstID,String contractAddr, String newAdminTstID,int key,String payer,long gaslimit,long gasprice)                    |   合约管理员转让合约管理权限
     2 | Transaction makeAssignFuncsToRole(String adminTstID,String contractAddr,String role,String[] funcName,int key,String payer,long gaslimit,long gasprice)   |   为角色分配函数
     3 | Transaction makeAssignTstIDsToRole(String adminTstId,String contractAddr,String role,String[] tstIDs, int key,String payer,long gaslimit,long gasprice)   |   绑定角色到实体身份
     4 | Transaction makeDelegate(String tstid,String contractAddr,String toAddr,String role,int period,int level,int key,String payer,long gaslimit,long gasprice)|   将合约调用权代理给其他人
     5 | Transaction makeWithDraw(String tstid,String contractAddr,String delegate, String role,int key,String payer,long gaslimit,long gasprice)                  |   收回合约调用权
 ```

#### 治理合约

         |   Function                                                                                                                                                                         |   Description
    -----|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
       1 | String registerCandidate(Account account, String peerPubkey, long initPos, String tstid,String tstidpwd,byte[] salt,  long keyNo, Account payerAcct, long gaslimit, long gasprice) | 抵押一定的TSG，消耗一定的额外TSG，申请成为候选节点
       2 | String unRegisterCandidate(Account account, String peerPubkey,Account payerAcct, long gaslimit, long gasprice)                  | 取消申请成为候选节点，解冻抵押的TSG
       3 | String withdrawTsg(Account account,Account payerAcct,long gaslimit,long gasprice)                                               | 提取解绑tsg
       4 | String getPeerInfo(String peerPubkey)                                                                                           | 查询节点信息
       5 | String getPeerInfoAll()                                                                                                         | 查询所有节点
       6 | String getAuthorizeInfo(String peerPubkey,Address addr)                                                                  | 查询某个地址对某个节点的授权信息
       7 | String withdraw(Account account,String peerPubkey[],long[] withdrawList,Account payerAcct,long gaslimit,long gasprice)          | 取出处于未冻结状态的抵押TSG
       8 | String quitNode(Account account,String peerPubkey,Account payerAcct,long gaslimit,long gasprice)                                | 退出节点
       9 | String addInitPos(Account account,String peerPubkey,int pos,Account payerAcct,long gaslimit,long gasprice)                      | 节点增加initPos接口，只能由节点所有者调用
       10| String reduceInitPos(Account account,String peerPubkey,int pos,Account payerAcct,long gaslimit,long gasprice)                   | 节点减少initPos接口，只能由节点所有者调用，initPos不能低于承诺值，不能低于已接受授权数量的1/10
       11| String setPeerCost(Account account,String peerPubkey,int peerCost,Account payerAcct,long gaslimit,long gasprice)                | 节点设置自己独占激励的比例
       12| String changeMaxAuthorization(Account account,String peerPubkey,int maxAuthorize,Account payerAcct,long gaslimit,long gasprice) | 节点修改自己接受的最大授权TSG数量
       13| String getPeerAttributes(String peerPubkey)                                                                                     | 查询节点属性信息
       14| String getSplitFeeAddress(String address)                                                                                       | 查询某地址得到的激励
       
       
### 签名验签
       
```

com.github.TesraSupernet.account.Account acct = new com.github.TesraSupernet.account.Account(tstSdk.defaultSignScheme);
byte[] data = "12345".getBytes();
DataSignature sign = new DataSignature(tstSdk.defaultSignScheme, acct, data);
byte[] signature = sign.signature();


com.github.TesraSupernet.account.Account acct2 = new com.github.TesraSupernet.account.Account(false,acct.serializePublicKey());
DataSignature sign2 = new DataSignature();
System.out.println(sign2.verifySignature(acct2, data, signature));
    
```   