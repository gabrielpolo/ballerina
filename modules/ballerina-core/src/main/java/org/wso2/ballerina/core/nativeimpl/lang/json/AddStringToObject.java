/**
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 **/

package org.wso2.ballerina.core.nativeimpl.lang.json;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.WriteContext;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ballerina.core.interpreter.Context;
import org.wso2.ballerina.core.model.types.JSONType;
import org.wso2.ballerina.core.model.types.StringType;
import org.wso2.ballerina.core.model.values.JSONValue;
import org.wso2.ballerina.core.nativeimpl.AbstractNativeFunction;
import org.wso2.ballerina.core.nativeimpl.annotations.Argument;
import org.wso2.ballerina.core.nativeimpl.annotations.BallerinaFunction;

/**
 * Insert a named element to a JSON Object. This method will add a new element with 
 * the given name and value, to the location identified by the given jsonpath. If an 
 * element with the same 'name' already exists, then it will update value of the existing 
 * element.
 */
@BallerinaFunction(
        packageName = "ballerina.lang.json",
        functionName = "add",
        args = {@Argument(name = "json", type = JSONType.class), 
                @Argument(name = "jsonPath", type = StringType.class),
                @Argument(name = "key", type = StringType.class),
                @Argument(name = "value", type = StringType.class)},
        isPublic = true
)
@Component(
        name = "func.lang.json_addStringToObject",
        immediate = true,
        service = AbstractNativeFunction.class
)

public class AddStringToObject extends AbstractJSONFunction {

    private static final Logger log = LoggerFactory.getLogger(AddStringToObject.class);

    @Override
    public void interpret(Context ctx) {
        log.info("AddStringToObject Native Function Invoked.");
        // Accessing Parameters.
        JSONValue json = (JSONValue) getArgumentValue(ctx, 0).getBValue();
        String jsonPath = getArgumentValue(ctx, 1).getString();
        String key = getArgumentValue(ctx, 2).getString();
        String value = getArgumentValue(ctx, 3).getString();
        
        // Adding the value to JSON Object
        WriteContext jsonCtx = JsonPath.parse(json.getValue());
        jsonCtx.put(jsonPath, key, value);
    }
}