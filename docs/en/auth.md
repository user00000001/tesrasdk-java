<h1 align="center"> Authority management </h1>

<p align="center" class="version">Version 1.0.0 </p>

English / [中文](../cn/auth.md)


At present, smart contracts can be called on by anyone, but this does not conform to real requirements. The basic idea of the role-based authority management is that each role can call on certain functions and each entity can be given multiple roles (entities are identified by TSG ID).
If a smart contract needs to add an authority management function, then information such as roles allocated in the contract, callable functions and which entity does the role belong to etc. must be recorded. This can be a very elaborate process and can be managed by a system contract. <br>


## Authority Contract Management

An authority contract is responsible for managing authority of an application contract. The contract administrator can transfer the authority, allocate functions for roles, revoke contract calling privileges and can verify the validity of the contract call token through entity validation.

Tesra's authority contract implements a role-based authority management solution. Each role corresponds to some callable function. Administrator assigns a role to an TSG ID to make it possible to call functions under that role. At the same time, roles can be passed, which means A can pass a certain role delegation to B and appoint the delegation time, so that B can also call the corresponding functions in a certain period.

### Usage Examples

Tesra smart contract do not support initialization execution during deployment, therefore, the contract administrator needs to hardcode it into the contract code, where the contract administrator’s TSGID is defined as a constant in the contract. 

For more details please refer to the following contract example: Tesra smart contract can call VerifyToken function for authority verification. At the same time, for developers’ convenience to check if an TSGID has a role, Java-SDK also provides VerifyToken interface where one can check role allocations.

Usage flow:

1. Deploy smart contract on-chain.
2. Call init methods in the smart contract. Use initContractAdmin method in the contract code to set up a pre-defined administrator TSG ID as the contract administration (please note it is a requirement to register the administrator’s TSG ID on-chain).
3. Contract administrator designs the role required and binds it with the function in the smart contract. This step can call assignFuncsToRole interface in Java-SDK for settings.
4. Contract administrator allocates role to TSG ID, TSG ID that owns the role will have authority to call the corresponding function of the role. This step can call assignFuncsToRole interface in Java-SDK for settings.
5. The appropriate TSG ID with the role, before calling its corresponding function, can use VerifyToken Interface in Java-SDK to verify if TSGID has the right to call the corresponding function.


##### Example

A.	Deploy smart contract on-chain
B.	Call init method in this contract

```
AbiInfo abiInfo = JSON.parseObject(abi,AbiInfo.class);
String name = "init";
AbiFunction function = abiInfo.getFunction(name);
function.setParamsValue();
String txhash = (String) sdk.neovm().sendTransaction(Helper.reverse(codeAddress),account,account,sdk.DEFAULT_GAS_LIMIT,0,function,false);
```

C.	Contract administrator designs role 1 and role 2, and binds role 1 and function foo1, as well as role2 and function foo2 and foo3.

```
String txhash = sdk.nativevm().auth().assignFuncsToRole(adminIdentity.tstid, password, adminIdentity.controls.get(0).getSalt(),
1, Helper.reverse(codeAddress), "role1", new String[]{"foo1"}, account, sdk.DEFAULT_GAS_LIMIT, 0);

String txhash = sdk.nativevm().auth().assignFuncsToRole(adminIdentity.tstid, password, adminIdentity.controls.get(0).getSalt(), 1,
Helper.reverse(codeAddress), "role2", new String[]{"foo2","foo3"}, account, sdk.DEFAULT_GAS_LIMIT, 0);

```

D.	Contract administrator will allocate role 1 to TstID1 and role 2 to TstID2, then TstID1 has the authority to call function foo1 and TstID2 has the authority to call function foo2 and foo3.
```
String txhash = sdk.nativevm().auth().assignTstIdsToRole(adminIdentity.tstid, password, adminIdentity.controls.get(0).getSalt(), 1,
Helper.reverse(codeAddress), "role1", new String[]{identity1.tstid}, account, sdk.DEFAULT_GAS_LIMIT, 0);

String txhash = sdk.nativevm().auth().assignTstIdsToRole(adminIdentity.tstid, password, adminIdentity.controls.get(0).getSalt(), 1,
Helper.reverse(codeAddress), "role2", new String[]{identity2.tstid}, account, sdk.DEFAULT_GAS_LIMIT, 0);

```

E.	As TSGID1’s role is allocated by contract administrator, its authority level default is 2, which means TSGID1 can transfer its authority delegation to another TSGIDX, the delegation interface Java-SDK is delegate, more details can be referred from the following interface information. When delegating authority, it is required to have the appointed principal’s authority level and delegation time. If the level of the delegate is 2, then the principal’s level can only be 1.
```
sdk.nativevm().auth().delegate(identity1.tstid,password,identity1.controls.get(0).getSalt(),1,Helper.reverse(codeAddress),
identityX.tstid,"role1",60*5,1,account,sdk.DEFAULT_GAS_LIMIT,0);
```
F.	Verify if an TSGID has the authority to call a certain function via VerifyToken interface.
```
String result = sdk.nativevm().auth().verifyToken(identityX.tstid, password, identityX.controls.get(0).getSalt(), 1, Helper.reverse(codeAddress), "foo1");

Returned Value: “01” means has authority, “00” means no authority.
```

G.	If the principal’s authority time has not finished, the delegate can revoke the authority that is delegated to others.
```
sdk.nativevm().auth().withdraw(identity1.tstid,password,identity1.controls.get(0).getSalt(),1,Helper.reverse(codeAddress),identityX.tstid,"role1",account,sdk.DEFAULT_GAS_LIMIT,0);
```

H.	Contract administrator can pass his or her administration authority to other TSGID.
```
String txhash = sdk.nativevm().auth().sendTransfer(adminIdentity.tstid,password,adminIdentity.controls.get(0).getSalt(),1,Helper.reverse(codeAddress),adminIdentity.tstid,
account,sdk.DEFAULT_GAS_LIMIT,0);
```

Tesra contract example:
```
using Neo.SmartContract.Framework;
using Neo.SmartContract.Framework.Services.Neo;
using Neo.SmartContract.Framework.Services.System;
using System;
using System.ComponentModel;
using System.Numerics;

namespace Example
{
    public class AppContract : SmartContract
    {
        public struct initContractAdminParam
        {
            public byte[] adminTstID;
        }

        public struct verifyTokenParam
        {
            public byte[] contractAddr; // Contract Address
            public byte[] calllerTstID; // tstId Caller’s TSGID
            public string funcName;     // Function Name
            public int keyNo;           // Which number of public key of Caller’s TSGID is used?
        }

        //the admin TSG ID of this contract must be hardcoded.
        public static readonly byte[] adminTstID = "did:tst :AazEvfQPcQ2GEFFPLF1ZLwQ7K5jDn81hve".AsByteArray();

        public static Object Main(string operation,object[] args)
        {
            if (operation == "init") return init();

            if (operation == "foo1")
            {
                //we need to check if the caller is authorized to invoke foo
                if (!verifyToken(operation, args)) return "no auth";

                return foo();
            }
            if (operation == "foo2")
            {
                //we need to check if the caller is authorized to invoke foo
                if (!verifyToken(operation, args)) return "no auth";

                return foo2();
            }
            if (operation == "foo3")
            {
                //we need to check if the caller is authorized to invoke foo
                if (!verifyToken(operation, args)) return "no auth";

                return foo3();
            }

            return "over";
        }

        public static string foo1()
        {
            return "A";
        }
        public static string foo2()
        {
            return "B";
        }
        public static string foo3()
        {
            return "C";
        }

        //this method is a must-defined method if you want to use native auth contract.
        public static bool init()
        {
            byte[] authContractAddr = {
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x06 };
            byte[] ret = Native.Invoke(0, authContractAddr, "initContractAdmin", adminTstID);
            return ret[0] == 1;
        }

        internal static bool verifyToken(string operation, object[] args)
        {
            verifyTokenParam param = new verifyTokenParam{};
            param.contractAddr = ExecutionEngine.ExecutingScriptHash;
            param.funcName = operation;
            param.calllerTstID = (byte[])args[0];
            param.keyNo = (int)args[1];

            byte[] authContractAddr = {
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x06 };
            byte[] ret = Native.Invoke(0, authContractAddr, "verifyToken", param);
            return ret[0] == 1;
        }
    }
}
```
----

## Interface list

The Tesra Java SDK provides methods for authority management.

|Instruction||Description|
|:--|:--|:--|
|Function instruction|Contract administrator transfers contract management authorization|This function must be called by the contract administrator and we use the public key with the number keyNo in the adminTstID to verify the validity of transaction signature. |
|Parameter instruction|Field|Description|
||adminTstId|Contract administrator's tstid|
||password|Contract administrator's password|
||salt|Private key decryption parameters|
||contractAddr|Contract address|
||newAdminTstID|New administrator|
||keyNo|Contract administrator's public key KeyNo|
||payerAcct|Paid account|
||gaslimit|Gaslimit|
||gasprice|Gas price|
|Return value instruction|Transaction hash||

##### Example
```
String sendTransfer(String adminTstId, String password, byte[] salt, String contractAddr, String newAdminTstID, long keyNo, Account payerAcct, long gaslimit, long gasprice)
```

<br>

|Instruction||Description|
|:--|:--|:--|
|Function instruction|Assign a function to a role|This function must be called by the contract administrator, and it will automatically bind all functions to the role. if it is already binded, the binding procedure will skip automatically, and return true in the end.|
|Parameter instruction|Field|Description|
||adminTstId|Contract administrator's tstid|
||password|Contract administrator's password|
||salt|Private key decryption parameters|
||contractAddr|Contract address|
||role|Role|
||funcName|Function name|
||keyNo|Contract administrator's KeyNo|
||payerAcct|Paid account|
||gaslimit|Gaslimit|
||gasprice|Gas price|
|Return value instruction|Transaction hash||

##### Example
```
String assignFuncsToRole(String adminTstID,String password,byte[] salt,String contractAddr,String role,String[] funcName,long keyNo,Account payerAcct,long gaslimit,long gasprice)
```

<br>

|Instruction||Description|
|:--|:--|:--|
|Function instruction|Bind a role to an entity|This function must be called by the contract administrator. The TSG ID in the tstIDs array is assigned the role and finally returns true. In the current implementation, the level of the permission token is equal to 2 by default.|
|Parameter instruction|Field|Description|
||adminTstId|Contract administrator's tstid|
||password|Contract administrator password|
||salt|Private key decryption parameters|
||contractAddr|Contract address|
||role|Role|
||tstIDs|tstid array|
||keyNo|Contract administrator's public key KeyNo|
||payerAcct|Paid account|
||gaslimit|Gaslimit|
||gasprice|Gas price|
|Return value instruction|Transaction hash||

##### Example
```
String assignTstIDsToRole(String adminTstId,String password,salt,String contractAddr,String role,String[] tstIDs,long keyNo,Account payerAcct,long gaslimit,long gasprice)
```

A Role owner can assign a role to other people, from is the TSG ID for assigner, to is the TSG ID for the assignee/delegate, role is the assigned role, period parameter specifies the term of delegation in seconds.

Delegate can assign its role to more people, level parameter specifies the depth of the delegate level, for instance,

Level = 1：Delegate cannot assign its role. The current realization only supports this case.

|Instruction||Description|
|:--|:--|:--|
|Function instruction|Delegate contract call authorization to others||
|Parameter instruction|Field|Description|
||tstid|The tstid of a function call authorization in the contract|
||password|tstid password|
||salt|Private key decryption parameters|
||contractAddr|Contract address|
||toTstId|The tstid that receives contract call authorization|
||role|Role|
||period|Use second as the unit|
||keyNo|The KeyNo of tstid public key|
||payerAcct|Paid account|
||gaslimit|Gaslimit|
||gasprice|Gas price|
|Return value instruction|Transaction hash||

##### Example
```
String delegate(String tstid,String password,salt,String contractAddr,String toTstId,String role,long period,long level,long keyNo,Account payerAcct,long gaslimit,long gasprice)
```

Role owners can revoke the role delegation in advance. InitiatorTstid is the initiator, delegate is role delegate, initiator revokes the role delegated to the delegate in advance.

|Instruction||Description|
|:--|:--|:--|
|Function instruction|Revocate contract call authorization（use with delegate function）||
|Parameter instruction|Field|Description|
||initiatorTstid|Transfer contract call authorization to other's tstid|
||password|tstid password|
||salt|Private key decryption parameters|
||contractAddr|Contract address|
||delegate|Delegator's tstid|
||role|Role|
||keyNo|tstid public key KeyNo|
||payerAcct|Paid account|
||gaslimit|Gaslimit|
||gasprice|Gas price|
|Return value instruction|Transaction hash||

##### Example
```
String withdraw(String initiatorTstid,String password,byte[] salt,String contractAddr,String delegate, String role,long keyNo,Account payerAcct,long gaslimit,long gasprice)
```

|Instruction||Description|
|:--|:--|:--|
|Function instruction|Verify the validity of contract call token||
|Parameter instruction|Field|Description|
||tstid|tstid that should be verified|
||password|tstid password|
||salt|Private key decryption parameters|
||contractAddr|Contract address|
||funcName|Function name|
||keyNo|tstid public key KeyNo|
||payerAcct|Paid account|
||gaslimit|Gaslimit|
||gasprice|Gas price|
|Return value instruction|Transaction hash||

##### Example
```
String verifyToken(String tstid,String password,byte[] salt,String contractAddr,String funcName,long keyNo,Account payerAcct,long gaslimit,long gasprice)
```
