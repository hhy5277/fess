/*
 * Copyright 2009-2015 the CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package org.codelibs.fess.app.web.admin.system;

import javax.annotation.Resource;

import org.codelibs.fess.app.web.base.FessAdminAction;
import org.codelibs.fess.helper.SystemHelper;
import org.lastaflute.web.Execute;
import org.lastaflute.web.callback.ActionRuntime;
import org.lastaflute.web.response.HtmlResponse;

/**
 * @author shinsuke
 * @author Keiichi Watanabe
 */
public class AdminSystemAction extends FessAdminAction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========

    @Resource
    private SystemHelper systemHelper;

    // ===================================================================================
    //                                                                               Hook
    //                                                                              ======
    @Override
    protected void setupHtmlData(final ActionRuntime runtime) {
        super.setupHtmlData(runtime);
        runtime.registerData("helpLink", systemHelper.getHelpLink("system"));
    }

    // ===================================================================================
    //                                                                              Index
    //                                                                      ==============
    @Execute
    public HtmlResponse index() {
        return asHtml(path_AdminSystem_IndexJsp);
    }

}
