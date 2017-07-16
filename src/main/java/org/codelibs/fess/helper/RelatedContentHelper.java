/*
 * Copyright 2012-2017 CodeLibs Project and the Others.
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
package org.codelibs.fess.helper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.codelibs.core.lang.StringUtil;
import org.codelibs.fess.es.config.exbhv.RelatedContentBhv;
import org.codelibs.fess.es.config.exentity.RelatedContent;
import org.codelibs.fess.mylasta.direction.FessConfig;
import org.codelibs.fess.util.ComponentUtil;

public class RelatedContentHelper {

    protected volatile Map<String, Map<String, String>> relatedContentMap = Collections.emptyMap();

    @PostConstruct
    public void init() {
        reload();
    }

    public void update() {
        reload();
    }

    public List<RelatedContent> getAvailableRelatedContentList() {

        return ComponentUtil.getComponent(RelatedContentBhv.class).selectList(cb -> {
            cb.query().matchAll();
            cb.query().addOrderBy_Term_Asc();
            cb.fetchFirst(ComponentUtil.getFessConfig().getPageRelatedqueryMaxFetchSizeAsInteger());
        });
    }

    protected void reload() {
        final Map<String, Map<String, String>> relatedContentMap = new HashMap<>();
        getAvailableRelatedContentList().stream().forEach(entity -> {
            final String key = getHostKey(entity);
            Map<String, String> map = relatedContentMap.get(key);
            if (map == null) {
                map = new HashMap<>();
                relatedContentMap.put(key, map);
            }
            map.put(toLowerCase(entity.getTerm()), entity.getContent());
        });
        this.relatedContentMap = relatedContentMap;
    }

    protected String getHostKey(RelatedContent entity) {
        final String key = entity.getVirtualHost();
        return StringUtil.isBlank(key) ? StringUtil.EMPTY : key;
    }

    public String getRelatedContent(final String query) {
        final FessConfig fessConfig = ComponentUtil.getFessConfig();
        final String key = fessConfig.getVirtualHostKey();
        Map<String, String> map = relatedContentMap.get(key);
        if (map != null) {
            final String content = map.get(toLowerCase(query));
            if (StringUtil.isNotBlank(content)) {
                return content;
            }
        }
        return StringUtil.EMPTY;
    }

    private String toLowerCase(final String term) {
        return term != null ? term.toLowerCase(Locale.ROOT) : term;
    }

}