/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.soul.admin.mapper;

import org.dromara.soul.admin.AbstractSpringIntegrationTest;
import org.dromara.soul.admin.entity.DashboardUserDO;
import org.dromara.soul.admin.page.PageParameter;
import org.dromara.soul.admin.query.DashboardUserQuery;
import org.dromara.soul.admin.utils.AesUtils;
import org.dromara.soul.common.utils.UUIDUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * test case for DashboardUserMapper.
 *
 * @author peiht
 */
public final class DashboardUserMapperTest extends AbstractSpringIntegrationTest {

    @Resource
    private DashboardUserMapper dashboardUserMapper;

    @Test
    public void testInsert() {
        DashboardUserDO record = buildDashboardUserDO();
        int count = dashboardUserMapper.insert(record);
        assertThat(count, comparesEqualTo(1));
    }

    @Test
    public void testInsertSelective() {
        DashboardUserDO record = buildDashboardUserDO();
        int count = dashboardUserMapper.insertSelective(record);
        assertThat(count, comparesEqualTo(1));
    }

    @Test
    public void testSelectById() {
        DashboardUserDO record = buildDashboardUserDO();
        int count = dashboardUserMapper.insert(record);
        assertThat(count, comparesEqualTo(1));
        DashboardUserDO result = dashboardUserMapper.selectById(record.getId());
        assertNotNull(result);
    }

    @Test
    public void testFindByQuery() {
        DashboardUserDO record = buildDashboardUserDO();
        record.setUserName("adminSoul");
        int count = dashboardUserMapper.insert(record);
        assertThat(count, comparesEqualTo(1));
        DashboardUserDO result = dashboardUserMapper.findByQuery(record.getUserName(), record.getPassword());
        assertNotNull(result);
    }

    @Test
    public void testSelectByQuery() {
        DashboardUserDO record = buildDashboardUserDO();
        int count = dashboardUserMapper.insert(record);
        assertThat(count, comparesEqualTo(1));
        DashboardUserQuery query = new DashboardUserQuery();
        PageParameter pageParameter = new PageParameter();
        query.setUserName("adminTest");
        query.setPageParameter(pageParameter);
        List<DashboardUserDO> result = dashboardUserMapper.selectByQuery(query);
        assertThat(result.size(), greaterThan(0));
    }

    @Test
    public void testCountByQuery() {
        DashboardUserDO record = buildDashboardUserDO();
        int count = dashboardUserMapper.insert(record);
        assertThat(count, comparesEqualTo(1));
        DashboardUserQuery query = new DashboardUserQuery();
        query.setUserName("adminTest");
        int result = dashboardUserMapper.countByQuery(query);
        assertThat(result, greaterThan(0));
    }

    @Test
    public void testUpdate() {
        DashboardUserDO record = buildDashboardUserDO();
        int count = dashboardUserMapper.insert(record);
        assertThat(count, comparesEqualTo(1));
        record.setUserName("adminUpdate");
        int result = dashboardUserMapper.update(record);
        assertThat(result, comparesEqualTo(1));
    }

    @Test
    public void testUpdateSelective() {
        DashboardUserDO record = buildDashboardUserDO();
        int count = dashboardUserMapper.insert(record);
        assertThat(count, comparesEqualTo(1));
        record.setUserName("adminUpdate");
        int result = dashboardUserMapper.updateSelective(record);
        assertThat(result, comparesEqualTo(1));
    }

    @Test
    public void testDelete() {
        DashboardUserDO record = buildDashboardUserDO();
        int count = dashboardUserMapper.insert(record);
        assertThat(count, comparesEqualTo(1));
        int result = dashboardUserMapper.delete(record.getId());
        assertThat(result, comparesEqualTo(1));
    }

    private DashboardUserDO buildDashboardUserDO() {
        DashboardUserDO dashboardUserDO = new DashboardUserDO();
        dashboardUserDO.setId(UUIDUtils.getInstance().generateShortUuid());
        dashboardUserDO.setUserName("adminTest");
        String aseKey = "2095132720951327";
        dashboardUserDO.setPassword(AesUtils.aesEncryption("123456", aseKey));
        dashboardUserDO.setEnabled(true);
        dashboardUserDO.setRole(1);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        dashboardUserDO.setDateCreated(now);
        dashboardUserDO.setDateUpdated(now);
        return dashboardUserDO;
    }
}
