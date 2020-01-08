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

package com.github.TesraSupernet.sdk.wallet;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Identity {
    public String label = "";
    public String tstid = "";
    public boolean isDefault = false;
    public boolean lock = false;
    public List<Control> controls = new ArrayList<Control>();
    public  Object extra = null;

    public Object getExtra(){
        return extra;
    }
    public void setExtra(Object extra){
        this.extra = extra;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

