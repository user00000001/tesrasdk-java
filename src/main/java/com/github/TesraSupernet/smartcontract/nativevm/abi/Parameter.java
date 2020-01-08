/*
 * Copyright (C) 2019-2020 The TesraSupernet Authors
 * This file is part of The TesraSupernet library.
 *
 *  The TesraSupernet is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  The TesraSupernet is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with The TesraSupernet.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.TesraSupernet.smartcontract.nativevm.abi;

import com.alibaba.fastjson.JSON;
import com.github.TesraSupernet.common.ErrorCode;
import com.github.TesraSupernet.sdk.exception.SDKException;

import java.util.List;

/**
 *
 */
public class Parameter {
    public String name;
    public String type;
    public SubType[] subType;
    public String value;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
    public SubType[] getSubType() {
        return subType;
    }

    public boolean setValue(Object value) {
        try {
            if(value == null) {
                this.value = null;
            }else if ("Byte".equals(type)) {
                byte tmp = (byte) value;
                this.value = JSON.toJSONString(tmp);
            } else if ("ByteArray".equals(type)) {
                byte[] tmp = (byte[]) value;
                this.value = JSON.toJSONString(tmp);
            } else if ("String".equals(type)) {
                this.value = (String) value;
            } else if ("Bool".equals(type)) {
                boolean tmp = (boolean) value;
                this.value = JSON.toJSONString(tmp);
            } else if ("Int".equals(type)) {
                long tmp = (long)value;
                this.value = JSON.toJSONString(tmp);
            } else if ("Array".equals(type)) {
                List tmp = (List) value;
                this.value = JSON.toJSONString(tmp);
            } else if ("Uint256".equals(type)) {
            } else if ("Address".equals(type)) {
            } else if ("Struct".equals(type)) {
                Struct tmp = (Struct) value;
                for(int i=0;i<tmp.list.size();i++){
                    subType[i] = new SubType();
                    subType[i].setParamsValue(tmp.list);
                }
            } else {
                throw new SDKException(ErrorCode.TypeError);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
